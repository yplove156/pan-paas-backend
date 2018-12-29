package com.yp.pan.serviceimpl;

import com.yp.pan.common.CustomEnum;
import com.yp.pan.config.K8sClient;
import com.yp.pan.dto.DeployDto;
import com.yp.pan.model.ImageInfo;
import com.yp.pan.service.ApplicationService;
import com.yp.pan.service.ClusterService;
import com.yp.pan.service.ImageService;
import com.yp.pan.util.ServerException;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerPort;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpec;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String deploy(DeployDto deployDto) {
        KubernetesClient client = new K8sClient(clusterService).get();
        ImageInfo imageInfo = imageService.getById(deployDto.getImageId());
        if (imageInfo == null) {
            throw new ServerException(CustomEnum.GET_IMAGE_ERROR);
        }
        Deployment deployment = new Deployment();

        ObjectMeta meta = new ObjectMeta();
        meta.setName(imageInfo.getName());

        Map<String, String> labels = new HashMap<>();
        labels.put("app", imageInfo.getName());
        meta.setLabels(labels);

        Map<String, String> annotations = new HashMap<>();
        annotations.put("pan-user", imageInfo.getUserId());
        annotations.put("pan-desc", imageInfo.getDescription());
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
        templateSpec.setMetadata(templateMeta);

        PodSpec podSpec = new PodSpec();
        List<Container> containers = new ArrayList<>();
        Container container = new Container();
        container.setImage(imageInfo.getImage());
        container.setName(imageInfo.getName());
        container.setImagePullPolicy(deployDto.getImagePullPolicy());
        List<ContainerPort> ports = new ArrayList<>();
        ContainerPort port = new ContainerPort();
        port.setContainerPort(80);
        ports.add(port);
        container.setPorts(ports);
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
}
