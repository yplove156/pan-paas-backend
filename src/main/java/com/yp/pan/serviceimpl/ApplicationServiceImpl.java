package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomAnno;
import com.yp.pan.common.CustomEnum;
import com.yp.pan.common.RoleEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.AppReplicasDto;
import com.yp.pan.dto.DeleteAppDto;
import com.yp.pan.dto.DeployDto;
import com.yp.pan.dto.StartAppDto;
import com.yp.pan.dto.StopAppDto;
import com.yp.pan.model.ImageInfo;
import com.yp.pan.model.UserInfo;
import com.yp.pan.service.ApplicationService;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.ImageService;
import com.yp.pan.util.RoleUtil;
import com.yp.pan.util.ServerException;
import com.yp.pan.util.ThreadLocalUtil;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerPort;
import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpec;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Service
public class ApplicationServiceImpl implements ApplicationService {


    private final ClusterService clusterService;

    private final ImageService imageService;

    @Autowired
    public ApplicationServiceImpl(ClusterService clusterService, ImageService imageService) {
        this.clusterService = clusterService;
        this.imageService = imageService;
    }

    @Override
    public Object apps(String namespace) {
        SimpleDateFormat sdf = new SimpleDateFormat(CustomAnno.K8S_TIME_FORMAT);
        KubernetesClient client = K8sClient.init(clusterService);
        List<Deployment> items = client.apps().deployments().inNamespace(namespace).list().getItems();
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        return items.stream()
                .filter(deployment -> {
                    if (RoleUtil.isAdmin(userInfo.getRole())) {
                        return true;
                    }
                    return RoleUtil.isOwner(deployment.getMetadata(), userInfo.getId());
                })
                .sorted((x, y) -> {
                    try {
                        Long dateX = sdf.parse(x.getMetadata().getCreationTimestamp()).getTime();
                        Long dateY = sdf.parse(y.getMetadata().getCreationTimestamp()).getTime();
                        return dateX.compareTo(dateY);
                    } catch (ParseException e) {
                        return 1;
                    }
                });
    }

    @Override
    public String deploy(DeployDto deployDto) {
        KubernetesClient client = K8sClient.init(clusterService);
        ImageInfo imageInfo = imageService.findById(deployDto.getImageId());
        if (imageInfo == null) {
            throw new ServerException(CustomEnum.GET_IMAGE_ERROR);
        }
        Deployment deployment = new Deployment();

        ObjectMeta meta = new ObjectMeta();
        meta.setName(deployDto.getName());

        Map<String, String> labels = new HashMap<>();
        labels.put(CustomAnno.PAN_APP, imageInfo.getName());
        meta.setLabels(labels);

        Map<String, String> annotations = new HashMap<>();
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        annotations.put(CustomAnno.PAN_USER, userInfo.getId());
        annotations.put(CustomAnno.PAN_DESC, imageInfo.getDescription());
        meta.setAnnotations(annotations);

        deployment.setMetadata(meta);

        DeploymentSpec spec = new DeploymentSpec();

        spec.setReplicas(deployDto.getReplicas());

        LabelSelector labelSelector = new LabelSelector();
        labelSelector.setMatchLabels(labels);
        spec.setSelector(labelSelector);

        PodTemplateSpec templateSpec = new PodTemplateSpec();
        ObjectMeta templateMeta = new ObjectMeta();
        templateMeta.setLabels(labels);
        templateMeta.setAnnotations(annotations);
        templateSpec.setMetadata(templateMeta);

        PodSpec podSpec = new PodSpec();
        List<Container> containers = new ArrayList<>();
        Container container = new Container();
        container.setImage(imageInfo.getImage());
        container.setName(imageInfo.getName());
        container.setImagePullPolicy(deployDto.getImagePullPolicy());
//        List<ContainerPort> ports = new ArrayList<>();
//        ContainerPort port = new ContainerPort();
//        port.setContainerPort(deployDto.getPort());
//        ports.add(port);
//        container.setPorts(ports);
        containers.add(container);
        podSpec.setContainers(containers);

        templateSpec.setSpec(podSpec);
        spec.setTemplate(templateSpec);

        deployment.setSpec(spec);

        Deployment orReplace = client.apps().deployments()
                .inNamespace(deployDto.getNamespace())
                .createOrReplace(deployment);
        if (orReplace != null) {
            return deployDto.getImageId();
        }
        throw new ServerException(CustomEnum.DEPLOY_APPLICATION_ERROR);
    }

    @Override
    public String stopApp(StopAppDto appDto) {
        KubernetesClient client = K8sClient.init(clusterService);
        Deployment deployment = client.apps().deployments()
                .inNamespace(appDto.getNamespace())
                .withName(appDto.getName()).get();
        if (null == deployment) {
            throw new ServerException(CustomEnum.STOP_APPLICATION_ERROR);
        }
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        boolean res;
        if (RoleUtil.isAdmin(userInfo.getRole())) {
            res = stopDeployment(client, appDto);
        } else if (RoleUtil.isOwner(deployment.getMetadata(), userInfo.getId())) {
            res = stopDeployment(client, appDto);
        } else {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        if (res) {
            return appDto.getName();
        }
        throw new ServerException(CustomEnum.STOP_APPLICATION_ERROR);
    }

    @Override
    public String startApp(StartAppDto appDto) {
        KubernetesClient client = K8sClient.init(clusterService);
        Deployment deployment = client.apps().deployments()
                .inNamespace(appDto.getNamespace())
                .withName(appDto.getName()).get();
        if (null == deployment) {
            throw new ServerException(CustomEnum.START_APPLICATION_ERROR);
        }
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        boolean res;
        if (RoleUtil.isAdmin(userInfo.getRole())) {
            res = startDeployment(client, appDto);
        } else if (RoleUtil.isOwner(deployment.getMetadata(), userInfo.getId())) {
            res = startDeployment(client, appDto);
        } else {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        if (res) {
            return appDto.getName();
        }
        throw new ServerException(CustomEnum.START_APPLICATION_ERROR);
    }

    @Override
    public String deleteApp(DeleteAppDto appDto) {
        KubernetesClient client = K8sClient.init(clusterService);
        Deployment deployment = client.apps().deployments()
                .inNamespace(appDto.getNamespace())
                .withName(appDto.getName()).get();
        if (null == deployment) {
            throw new ServerException(CustomEnum.START_APPLICATION_ERROR);
        }
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        boolean res;
        if (RoleUtil.isAdmin(userInfo.getRole())) {
            res = deleteDeployment(client, appDto);
        } else if (RoleUtil.isOwner(deployment.getMetadata(), userInfo.getId())) {
            res = deleteDeployment(client, appDto);
        } else {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        if (res) {
            return appDto.getName();
        }
        throw new ServerException(CustomEnum.START_APPLICATION_ERROR);
    }

    private boolean stopDeployment(KubernetesClient client, StopAppDto appDto) {
        Deployment stop = client.apps().deployments()
                .inNamespace(appDto.getNamespace())
                .withName(appDto.getName())
                .edit()
                .withNewSpec()
                .withReplicas(0)
                .endSpec()
                .done();
        return stop != null;
    }

    private boolean startDeployment(KubernetesClient client, StartAppDto appDto) {
        Deployment start = client.apps().deployments()
                .inNamespace(appDto.getNamespace())
                .withName(appDto.getName())
                .edit()
                .withNewSpec()
                .withReplicas(appDto.getReplicas())
                .endSpec()
                .done();
        return start != null;
    }

    private boolean deleteDeployment(KubernetesClient client, DeleteAppDto appDto) {
        return client.apps().deployments()
                .inNamespace(appDto.getNamespace())
                .withName(appDto.getName())
                .delete();
    }

    @Override
    public Object updateReplicas(AppReplicasDto appReplicasDto) {
        KubernetesClient client = K8sClient.init(clusterService);
        UserInfo userInfo = ThreadLocalUtil.getInstance().getUserInfo();
        Deployment deployment = client.apps().deployments()
                .inNamespace(appReplicasDto.getNamespace())
                .withName(appReplicasDto.getName()).get();
        boolean res;
        if (RoleUtil.isAdmin(userInfo.getRole())) {
            res = resetReplicas(client, appReplicasDto);
        } else if (RoleUtil.isOwner(deployment.getMetadata(), userInfo.getId())) {
            res = resetReplicas(client, appReplicasDto);

        } else {
            throw new ServerException(CustomEnum.NO_PERMISSION);
        }
        if (res) {
            return appReplicasDto.getName();
        }
        throw new ServerException(CustomEnum.RESET_APPLICATION_REPLICAS_ERROR);

    }

    private boolean resetReplicas(KubernetesClient client, AppReplicasDto appReplicasDto) {
        Deployment start = client.apps().deployments()
                .inNamespace(appReplicasDto.getNamespace())
                .withName(appReplicasDto.getName())
                .edit()
                .withNewSpec()
                .withReplicas(appReplicasDto.getReplicas())
                .endSpec()
                .done();
        return start != null;
    }
}
