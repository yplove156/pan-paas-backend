package com.yp.pan.serviceimpl;

import com.yp.pan.mapper.LogMapper;
import com.yp.pan.model.LogInfo;
import com.yp.pan.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void addLog(LogInfo logInfo) {
        logMapper.addLog(logInfo);
    }
}
