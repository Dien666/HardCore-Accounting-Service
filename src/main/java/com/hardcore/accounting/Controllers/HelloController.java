package com.hardcore.accounting.Controllers;

import com.hardcore.accounting.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {

//    @GetMapping("v1/greeting/{name}")
//    public String sayHello(@PathVariable("name") String name){
//        return String.format("Hello, %s", name);
//    }
    private AtomicLong counter = new AtomicLong();

    @GetMapping("v1/greeting")
    public Greeting sayHello(@RequestParam("name") String name){
        return new Greeting(counter.incrementAndGet(), String.format("Hello, %s", name));
    }

}
