package com.yp.pan.serviceimpl;

import com.yp.pan.dto.ApplicationDto;
import com.yp.pan.mapper.ApplicationMapper;
import com.yp.pan.model.ApplicationInfo;
import com.yp.pan.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ApplicationServiceImpl class
 *
 * @author Administrator
 * @date 2018/12/17
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationMapper applicationMapper;

    @Autowired
    public ApplicationServiceImpl(ApplicationMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }

    @Override
    public int addApplication(ApplicationInfo applicationInfo) {
        return applicationMapper.addApplication(applicationInfo);
    }

    @Override
    public int openAppNo() {
        return applicationMapper.openAppNo();
    }

    @Override
    public List<ApplicationDto> openAppList(int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("limit", limit);
        return applicationMapper.openAppList(params);
    }

    @Override
    public int userAppNo(String userId) {
        return applicationMapper.userAppNo(userId);
    }

    @Override
    public List<ApplicationDto> userAppList(String userId, int start, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("start", start);
        params.put("limit", limit);
        return applicationMapper.userAppList(params);
    }

    @Override
    public int deleteApp(String id) {
        return applicationMapper.deleteApp(id);
    }

    @Override
    public int update(ApplicationInfo applicationInfo) {
        return applicationMapper.update(applicationInfo);
    }

    @Override
    public ApplicationInfo getById(String id) {
        return applicationMapper.getById(id);
    }
}
