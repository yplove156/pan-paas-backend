package com.yp.pan.serviceimpl;

import com.yp.pan.mapper.LogMapper;
import com.yp.pan.model.LogInfo;
import com.yp.pan.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void addLog(LogInfo logInfo) {
        int i = logMapper.addLog(logInfo);
        System.out.println(i);
    }

    @Override
    public List<LogInfo> logList(int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("limit", limit);
        return logMapper.logList(params);
    }

    @Override
    public int countLog() {
        return logMapper.countLog();
    }
}
