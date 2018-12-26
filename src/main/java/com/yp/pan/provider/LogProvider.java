package com.yp.pan.provider;

import com.yp.pan.model.LogInfo;
import org.apache.ibatis.jdbc.SQL;

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
}
