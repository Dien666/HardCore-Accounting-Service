package com.hardcore.accounting.Controllers;

import com.hardcore.accounting.manager.UserInfoManager;
import com.hardcore.accounting.model.service.UserInfo;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1.0/session")
public class sessionController {

    private static final String SUCCESS = "success";
    private static final String STATUS = "status";
    private static final String USERNAME = "username";
    private static final int OK = 200;
    private static final int NOT_FOUND = 400;
    private final UserInfoManager userInfoManager;

    @Autowired
    public sessionController(UserInfoManager userInfoManager) {
        this.userInfoManager = userInfoManager;
    }

    /**
     * login with username and password
     * @param userInfo
     * @return The response for login
     */
    @PostMapping(produces = "application/json", consumes = "application/json")
    public Map<String, String> login(@RequestBody UserInfo userInfo) {
        val response = new HashMap<String, String>();
        userInfoManager.login(userInfo.getUsername(), userInfo.getPassword());
        response.put(STATUS, SUCCESS);
        response.put(USERNAME, userInfo.getUsername());
        return response;
    }

}
