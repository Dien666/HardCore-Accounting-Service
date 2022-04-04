package com.hardcore.accounting.Controllers;

import com.hardcore.accounting.converter.c2s.UserInfoC2SConverter;
import com.hardcore.accounting.exception.ErrorResponse;
import com.hardcore.accounting.exception.InvalidParameterException;
import com.hardcore.accounting.exception.ResourceNotFoundException;
import com.hardcore.accounting.exception.ServiceException;
import com.hardcore.accounting.manager.UserInfoManager;
import com.hardcore.accounting.model.service.UserInfo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("userInfoC2SConverter")
@RequestMapping("v1.0/users")
@Slf4j
public class UserController {

    private final UserInfoManager userInfoManager;
    private final UserInfoC2SConverter userInfoC2SConverter;

    @Autowired
    public UserController(final UserInfoManager userInfoManager,
                          final UserInfoC2SConverter userInfoC2SConverter){
        this.userInfoManager = userInfoManager;
        this.userInfoC2SConverter = userInfoC2SConverter;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserInfo> getUserInfoByUserID(@PathVariable("id") @NonNull Long userId){
        log.debug("Get info by users id {}", userId);
        if (userId <= 0L){
            throw new InvalidParameterException(String.format("The user id %s is invalid", userId));
        }
        val userInfo = userInfoManager.getUserInfoByUserId(userId);
        return ResponseEntity.ok(userInfoC2SConverter.convert(userInfo));
    }

    @ResponseBody
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserInfo> register(@RequestBody UserInfo userInfo){
        val userInfoToReturn = userInfoC2SConverter.convert(
                userInfoManager.register(userInfo.getUsername(), userInfo.getPassword()));
        assert userInfoToReturn != null;
        return ResponseEntity.ok(userInfoToReturn);
    }


}
