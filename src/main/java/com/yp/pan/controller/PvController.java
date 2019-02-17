package com.yp.pan.controller;

import org.springframework.web.bind.annotation.*;

/**
 * PvController class
 *
 * @author Administrator
 * @date 2019/02/14
 */
@RestController
@RequestMapping("/pv")
public class PvController {

    @GetMapping("/namespaces/{namespace}/{pv}")
    public Object getPv(@PathVariable String namespace, @PathVariable String pv) {
        return null;
    }

    @GetMapping("/namespaces/{namespace}")
    public Object getPvInNamespace(@PathVariable String namespace) {
        return null;
    }

    @PostMapping
    public Object createPv(){
        return null;
    }
}
