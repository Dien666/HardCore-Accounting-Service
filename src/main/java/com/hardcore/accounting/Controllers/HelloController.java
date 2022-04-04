package com.hardcore.accounting.Controllers;

import com.hardcore.accounting.model.service.Greeting;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

// 对外的API接口
// "v1/application" "v1/users"

// 注意：
// 1. 不要有过多的处理逻辑
// 2. 参数校验越早越好

@RestController
public class HelloController {

//    @GetMapping("v1/greeting/{name}")
//    public String sayHello(@PathVariable("name") String name){
//        return String.format("Hello, %s", name);
//    }

    private AtomicLong counter = new AtomicLong();

    @GetMapping("v1.0/greeting")
    public Greeting sayHello(@RequestParam("name") String name){
        return new Greeting(counter.incrementAndGet(), String.format("Hello, %s", name));
    }

}
