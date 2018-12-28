package com.yp.pan.service;

import com.yp.pan.model.LogInfo;

import java.util.List;
import java.util.Map;

public interface LogService {

    void addLog(LogInfo logInfo);

    List<LogInfo> logList(int start, int limit);

    int countLog();
}
