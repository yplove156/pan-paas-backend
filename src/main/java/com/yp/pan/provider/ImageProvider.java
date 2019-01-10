package com.yp.pan.provider;

import com.yp.pan.model.ImageInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * ApplicationProvider class
 *
 * @author Administrator
 * @date 2018/12/13
 */
public class ImageProvider {

    public String addApplication(ImageInfo imageInfo) {
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
                VALUES("username", "#{username}");
                VALUES("size", "#{size}");
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
                SELECT("id", "name", "type", "version", "description", "image", "photo", "open", "level", "user_id", "username", "size", "create_time");
                FROM("app_info");
                WHERE("open=1", "delete_flag=0");
                ORDER_BY("create_time desc");
            }
        }.toString() + " limit #{start},#{limit}";
    }

    public String userAppList(Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("id", "name", "type", "version", "description", "image", "photo", "open", "level", "user_id", "username", "size", "create_time");
                FROM("app_info");
                WHERE("user_id=#{userId}", "delete_flag=0");
                ORDER_BY("create_time desc");
            }
        }.toString() + "limit #{start},#{limit}";
    }

    public String getById(String id) {
        return new SQL() {
            {
                SELECT("id", "name", "type", "version", "description", "image", "photo", "open", "level", "user_id", "username", "size", "create_time");
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

    public String update(ImageInfo imageInfo) {
        return new SQL() {
            {
                UPDATE("app_info");
                if (null != imageInfo.getName()) {
                    SET("name=#{name}");
                }
                if (null != imageInfo.getType()) {
                    SET("type=#{type}");
                }
                if (null != imageInfo.getVersion()) {
                    SET("version=#{version}");
                }
                if (null != imageInfo.getDescription()) {
                    SET("description=#{description}");
                }
                if (null != imageInfo.getImage()) {
                    SET("image=#{image}");
                }
                if (null != imageInfo.getPhoto()) {
                    SET("photo=#{photo}");
                }
                if (null != imageInfo.getOpen()) {
                    SET("open=#{open}");
                }
                if (null != imageInfo.getLevel()) {
                    SET("level=#{level}");
                }
                if (null != imageInfo.getUserId()) {
                    SET("user_id=#{userId}");
                }
                if (null != imageInfo.getCreateTime()) {
                    SET("create_time=#{createTime}");
                }
                if (null != imageInfo.getUpdateTime()) {
                    SET("update_time=#{updateTime}");
                }
                if (null != imageInfo.getDeleteFlag()) {
                    SET("delete_flag=#{deleteFlag}");
                }
                WHERE("id=#{id}", "delete_flag=0");
            }
        }.toString();
    }
}
