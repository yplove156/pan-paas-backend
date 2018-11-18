package com.yp.pan.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * UserProvider class
 *
 * @author Administrator
 * @date 2018/11/17
 */
public class UserProvider {

    public String findByUsername(String username){
        String preSql =  new SQL(){
            {
                SELECT("id","username","nick","photo","role","create_time","update_time","delete_flag");
                FROM("user_info");
                WHERE("username=#{username}");
                WHERE("delete_flag=0");
            }
        }.toString();
        return preSql + " limit 1";
    }
}
