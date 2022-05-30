package com.hardcore.accounting.manager;

import com.hardcore.accounting.converter.p2c.UserInfoP2CConverter;
import com.hardcore.accounting.dao.UserInfoDAO;
import com.hardcore.accounting.exception.InvalidParameterException;
import com.hardcore.accounting.exception.ResourceNotFoundException;
import com.hardcore.accounting.model.common.UserInfo;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * controller -> manager -> DAO -> MySQL
 */
@Component
public class UserInfoManagerImpl implements UserInfoManager{

    private final UserInfoDAO userInfoDAO;
    private final UserInfoP2CConverter userInfoP2CConverter;

    @Autowired
    public UserInfoManagerImpl(final UserInfoDAO userInfoDAO,
                               final UserInfoP2CConverter userInfoP2CConverter) {
        this.userInfoDAO = userInfoDAO;
        this.userInfoP2CConverter = userInfoP2CConverter;
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        val userInfo =
                Optional.ofNullable(userInfoDAO.getUserInfoById(userId))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s was not found", userId)));
        return userInfoP2CConverter.convert(userInfo);
    }

    @Override
    public UserInfo getUserInfoByUserName(String username) {
        val userInfo =
                Optional.ofNullable(userInfoDAO.getUserInfoByUsername(username))
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s was not found", username)));
        return userInfoP2CConverter.convert(userInfo);
    }

    @Override
    public void login(String username, String password) {
        // get subject
        Subject subject = SecurityUtils.getSubject();
        // construct token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

        // login
        subject.login(usernamePasswordToken);
    }

    @Override
    public UserInfo register(String username, String password) {
        com.hardcore.accounting.model.persistence.UserInfo userInfo =
                userInfoDAO.getUserInfoByUsername(username);
        if (userInfo != null) {
            throw new InvalidParameterException(String.format("The user %s was registered."));
        }

        // set random salt
        String salt = UUID.randomUUID().toString();
        String encryptedPassword = new Sha256Hash(password, salt, 1000).toBase64();

        val newUserInfo = com.hardcore.accounting.model.persistence.UserInfo.builder()
                .username(username)
                .password(encryptedPassword)
                .salt(salt)
                .createTime(LocalDate.now())
                .build();

        userInfoDAO.createNewUser(newUserInfo);

        return userInfoP2CConverter.convert(newUserInfo);
    }

}
