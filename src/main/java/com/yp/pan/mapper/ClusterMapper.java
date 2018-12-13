package com.yp.pan.mapper;

import com.yp.pan.dto.ClusterInfoDto;
import com.yp.pan.model.ClusterInfo;
import com.yp.pan.provider.ClusterProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * ClusterMapper class
 *
 * @author Administrator
 * @date 2018/12/04
 */
public interface ClusterMapper {

    @SelectProvider(type = ClusterProvider.class, method = "getCluster")
    @Results({
            @Result(column = "ca_cert_data", property = "caCertData"),
            @Result(column = "client_cert_data", property = "clientCertData"),
            @Result(column = "client_key_data", property = "clientKeyData"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "delete_flag", property = "deleteFlag")
    })
    ClusterInfo getCluster();

    @InsertProvider(type = ClusterProvider.class, method = "addCluster")
    int addCluster(ClusterInfo clusterInfo);

    @UpdateProvider(type = ClusterProvider.class, method = "deleteClusterById")
    int deleteClusterById(String id);

    @UpdateProvider(type = ClusterProvider.class, method = "closeOpen")
    int closeOpen();

    @SelectProvider(type = ClusterProvider.class, method = "clusterList")
    @Results({
            @Result(column = "ca_cert_data", property = "caCertData"),
            @Result(column = "client_cert_data", property = "clientCertData"),
            @Result(column = "client_key_data", property = "clientKeyData"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "delete_flag", property = "deleteFlag")
    })
    List<ClusterInfo> clusterList();

    @UpdateProvider(type = ClusterProvider.class, method = "updateCluster")
    int updateCluster(ClusterInfoDto clusterInfoDto);
}
