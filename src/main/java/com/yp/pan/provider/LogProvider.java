package com.yp.pan.provider;

import com.yp.pan.model.LogInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class LogProvider {

    public String addLog(LogInfo logInfo) {
        return new SQL() {
            {
                INSERT_INTO("log_info");
                VALUES("id", "#{id}");
                VALUES("action", "#{action}");
                VALUES("user", "#{user}");
                VALUES("status", "#{status}");
                VALUES("res", "#{res}");
                VALUES("exception", "#{exception}");
                VALUES("code", "#{code}");
                VALUES("message", "#{message}");
                VALUES("create_time", "#{createTime}");
            }
        }.toString();
    }

    public String logList(Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("id", "action", "user", "status", "create_time");
                FROM("log_info");
                ORDER_BY("create_time desc");
            }
        }.toString() + "limit #{start},#{limit}";
    }

    public String countLog() {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("log_info");
            }
        }.toString();
    }
}
