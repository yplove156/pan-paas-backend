package com.yp.pan.mapper;

import com.yp.pan.model.LogInfo;
import com.yp.pan.provider.LogProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

public interface LogMapper {

    @InsertProvider(type = LogProvider.class, method = "addLog")
    int addLog(LogInfo logInfo);

    @SelectProvider(type = LogProvider.class, method = "logList")
    List<LogInfo> logList(Map<String, Object> params);

    @SelectProvider(type = LogProvider.class, method = "countLog")
    int countLog();
}
