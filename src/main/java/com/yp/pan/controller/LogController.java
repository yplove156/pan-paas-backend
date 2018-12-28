package com.yp.pan.controller;

import com.yp.pan.dto.ApplicationDto;
import com.yp.pan.model.LogInfo;
import com.yp.pan.service.LogService;
import com.yp.pan.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/{page}")
    public Object logList(@PathVariable Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        int limit = 20;
        int total = logService.countLog();
        if (total == 0) {
            return new Page<LogInfo>(page, limit, 0, total, new ArrayList<>()) {
            };
        }
        int start = (page - 1) * limit;
        int totalPage = (total + limit - 1) / limit;
        List<LogInfo> list = logService.logList(start, limit);
        return new Page<LogInfo>(page, limit, totalPage, total, list) {
        };
    }
}
