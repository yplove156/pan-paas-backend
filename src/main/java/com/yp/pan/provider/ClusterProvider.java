package com.yp.pan.provider;

import com.yp.pan.dto.ClusterInfoDto;
import com.yp.pan.model.ClusterInfo;
import org.apache.ibatis.jdbc.SQL;

/**
 * ClusterProvider class
 *
 * @author Administrator
 * @date 2018/12/04
 */
public class ClusterProvider {

    public String getCluster(String userId) {
        return new SQL() {
            {
                SELECT("id", "url", "user_id", "ca_cert_data", "client_cert_data", "client_key_data", "open", "create_time", "update_time", "delete_flag");
                FROM("cluster_info");
                WHERE("user_id=#{userId}");
                WHERE("open=1");
                WHERE("delete_flag=0");
            }
        }.toString() + "limit 1";
    }

    public String addCluster(ClusterInfo clusterInfo) {
        return new SQL() {
            {
                INSERT_INTO("cluster_info");
                VALUES("id", "#{id}");
                VALUES("url", "#{url}");
                VALUES("user_id", "#{userId}");
                VALUES("ca_cert_data", "#{caCertData}");
                VALUES("client_cert_data", "#{clientCertData}");
                VALUES("client_key_data", "#{clientKeyData}");
                VALUES("open", "#{open}");
                VALUES("create_time", "#{createTime}");
                VALUES("update_time", "#{updateTime}");
                VALUES("delete_flag", "#{deleteFlag}");
            }
        }.toString();
    }

    public String deleteClusterById(String id) {
        return new SQL() {
            {
                UPDATE("cluster_info");
                SET("delete_flag=1");
                WHERE("id=#{id}", "delete_flag=0");
            }
        }.toString();
    }

    public String closeOpen() {
        return new SQL() {
            {
                UPDATE("cluster_info");
                SET("open=0");
            }
        }.toString();
    }

    public String clusterList(String userId) {
        return new SQL() {
            {
                SELECT("id", "url", "ca_cert_data", "client_cert_data", "client_key_data", "open", "create_time", "update_time", "delete_flag");
                FROM("cluster_info");
                WHERE("user_id=#{userId}");
                WHERE("delete_flag=0");
            }
        }.toString();
    }

    public String updateCluster(ClusterInfoDto clusterInfoDto) {
        return new SQL() {
            {
                UPDATE("cluster_info");
                SET("url=#{url}");
                SET("ca_cert_data=#{caCertData}");
                SET("client_cert_data=#{clientCertData}");
                SET("client_key_data=#{clientKeyData}");
                SET("open=#{open}");
                SET("update_time=#{createTime}");
                WHERE("id=#{id}", "delete_flag=0");
            }
        }.toString();
    }
}
