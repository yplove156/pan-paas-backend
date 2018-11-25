package com.yp.pan.provider;

import com.yp.pan.model.PwdInfo;
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

    public String addPwd(PwdInfo pwdInfo) {
        return new SQL() {
            {
                INSERT_INTO("pwd_info");
                VALUES("id", "#{id}");
                VALUES("username", "#{username}");
                VALUES("password", "#{password}");
                VALUES("salt", "#{salt}");
                VALUES("state", "#{state}");
                VALUES("create_time", "#{createTime}");
                VALUES("update_time", "#{updateTime}");
                VALUES("delete_flag", "#{deleteFlag}");
            }
        }.toString();
    }
}
