package com.yp.pan.provider;

import com.yp.pan.model.ApplicationInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * ApplicationProvider class
 *
 * @author Administrator
 * @date 2018/12/13
 */
public class ApplicationProvider {

    public String addApplication(ApplicationInfo applicationInfo) {
        return new SQL() {
            {
                INSERT_INTO("app_info");
                VALUES("id", "#{id}");
                VALUES("name", "#{name}");
                VALUES("type", "#{type}");
                VALUES("version", "#{version}");
                VALUES("description", "#{description}");
                VALUES("image", "#{image}");
                VALUES("photo", "#{photo}");
                VALUES("level", "#{level}");
                VALUES("open", "#{open}");
                VALUES("user_id", "#{userId}");
                VALUES("create_time", "#{createTime}");
                VALUES("update_time", "#{updateTime}");
                VALUES("delete_flag", "#{deleteFlag}");
            }
        }.toString();
    }

    public String openAppNo() {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("app_info");
                WHERE("open=1", "delete_flag=0");
            }
        }.toString();
    }

    public String userAppNo(String userId) {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("app_info");
                WHERE("user_id=#{userId}", "delete_flag=0");
            }
        }.toString();
    }

    public String openAppList(Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("id", "name", "type", "version", "description", "image", "photo", "open", "level", "user_id", "create_time");
                FROM("app_info");
                WHERE("open=1", "delete_flag=0");
                ORDER_BY("create_time desc");
            }
        }.toString() + "limit #{start},#{limit}";
    }

    public String userAppList(Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("id", "name", "type", "version", "description", "image", "photo", "open", "level", "user_id", "create_time");
                FROM("app_info");
                WHERE("user_id=#{userId}", "delete_flag=0");
                ORDER_BY("create_time desc");
            }
        }.toString() + "limit #{start},#{limit}";
    }

    public String getById(String id){
        return new SQL(){
            {
                SELECT("id", "name", "type", "version", "description", "image", "photo", "open", "level", "user_id", "create_time");
                FROM("app_info");
                WHERE("id=#{id}", "delete_flag=0");
            }
        }.toString();
    }

    public String deleteApp(String id) {
        return new SQL() {
            {
                UPDATE("app_info");
                SET("delete_flag=1");
                WHERE("id=#{id}", "delete_flag=0");
            }
        }.toString();
    }

    public String update(ApplicationInfo applicationInfo) {
        return new SQL() {
            {
                UPDATE("app_info");
                if (null != applicationInfo.getName()) {
                    SET("name=#{name}");
                }
                if (null != applicationInfo.getType()) {
                    SET("type=#{type}");
                }
                if (null != applicationInfo.getVersion()) {
                    SET("version=#{version}");
                }
                if (null != applicationInfo.getDescription()) {
                    SET("description=#{description}");
                }
                if (null != applicationInfo.getImage()) {
                    SET("image=#{image}");
                }
                if (null != applicationInfo.getPhoto()) {
                    SET("photo=#{photo}");
                }
                if (null != applicationInfo.getOpen()) {
                    SET("open=#{open}");
                }
                if (null != applicationInfo.getLevel()) {
                    SET("level=#{level}");
                }
                if (null != applicationInfo.getUserId()) {
                    SET("user_id=#{userId}");
                }
                if (null != applicationInfo.getCreateTime()) {
                    SET("create_time=#{createTime}");
                }
                if (null != applicationInfo.getUpdateTime()) {
                    SET("update_time=#{updateTime}");
                }
                if (null != applicationInfo.getDeleteFlag()) {
                    SET("delete_flag=#{deleteFlag}");
                }
                WHERE("id=#{id}", "delete_flag=0");
            }
        }.toString();
    }
}
