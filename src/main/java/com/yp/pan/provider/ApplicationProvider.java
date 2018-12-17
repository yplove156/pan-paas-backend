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
                VALUES("description", "#{description}");
                VALUES("image", "#{image}");
                VALUES("photo", "#{photo}");
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

    public String openAppList(Map<String,Object> params) {
        return new SQL() {
            {
                SELECT("id", "name", "type", "description", "image", "photo", "open", "user_id", "create_time");
                FROM("app_info");
                WHERE("open=1", "delete_flag=0");
            }
        }.toString() + "limit #{start},#{limit}";
    }

    public String userAppList(Map<String,Object> params) {
        return new SQL() {
            {
                SELECT("id", "name", "type", "description", "image", "photo", "open", "user_id", "create_time");
                FROM("app_info");
                WHERE("user_id=#{userId}", "delete_flag=0");
            }
        }.toString() + "limit #{start},#{limit}";
    }
}
