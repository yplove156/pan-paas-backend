package com.yp.pan.controller;

import com.yp.pan.model.NoticeInfo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping("/{page}")
    public Object notices(@PathVariable Integer page) {
        return null;
    }

    @PostMapping
    public Object send(@RequestBody NoticeInfo noticeInfo) {
        return null;
    }

    @GetMapping("/user/{page}")
    public Object userNotices(@PathVariable Integer page) {
        return null;
    }

    @GetMapping("/not-read")
    public Object notRead() {
        return null;
    }

    @DeleteMapping("/{id}")
    public Object deleteNotice(@PathVariable String id) {
        return null;
    }
}
