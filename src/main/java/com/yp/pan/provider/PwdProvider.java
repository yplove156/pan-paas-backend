package com.yp.pan.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * PwdProvider class
 *
 * @author Administrator
 * @date 2018/11/17
 */
@SuppressWarnings("Duplicates")
public class PwdProvider {

    public String findByUsername(String username) {
        String preSql = new SQL() {
            {
                SELECT("id", "username", "password", "salt", "state", "create_time", "update_time", "delete_flag");
                FROM("pwd_info");
                WHERE("username=#{username}");
                WHERE("delete_flag=0");
            }
        }.toString();
        return preSql + " limit 1";
    }
}
