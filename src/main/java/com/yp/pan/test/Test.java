package com.yp.pan.test;

import com.yp.pan.annotation.RequireRole;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @RequireRole(value = "admin")
    @RequestMapping("/test")
    public Object test() {
        Obj obj = new Obj("666");
        return obj;
    }

    @RequestMapping("/test2")
    public Object test2(){
        return "000";
    }
}
