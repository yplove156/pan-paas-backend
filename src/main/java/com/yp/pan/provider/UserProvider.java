package com.yp.pan.provider;

import com.yp.pan.model.UserInfo;
import org.apache.ibatis.jdbc.SQL;

/**
 * UserProvider class
 *
 * @author Administrator
 * @date 2018/11/17
 */
public class UserProvider {

    public String findByUsername(String username) {
        String preSql = new SQL() {
            {
                SELECT("id", "username", "nick", "photo", "role", "name", "phone", "position", "create_time", "update_time", "delete_flag");
                FROM("user_info");
                WHERE("username=#{username}");
                WHERE("delete_flag=0");
            }
        }.toString();
        return preSql + " limit 1";
    }

    public String userList() {
        return new SQL() {
            {
                SELECT("id", "username", "nick", "photo", "role", "name", "phone", "position", "create_time", "update_time", "delete_flag");
                FROM("user_info");
                WHERE("delete_flag=0");
                ORDER_BY("create_time");
            }
        }.toString();
    }

    public String addUser(UserInfo userInfo) {
        return new SQL() {
            {
                INSERT_INTO("user_info");
                VALUES("id", "#{id}");
                VALUES("username", "#{username}");
                VALUES("nick", "#{nick}");
                VALUES("photo", "#{photo}");
                VALUES("role", "#{role}");
                VALUES("name", "#{name}");
                VALUES("phone", "#{phone}");
                VALUES("position", "#{position}");
                VALUES("create_time", "#{createTime}");
                VALUES("update_time", "#{updateTime}");
                VALUES("delete_flag", "#{deleteFlag}");
            }
        }.toString();
    }
}
