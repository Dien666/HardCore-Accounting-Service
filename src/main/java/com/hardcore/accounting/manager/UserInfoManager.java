package com.hardcore.accounting.manager;

import com.hardcore.accounting.model.common.UserInfo;
import org.apache.catalina.User;

public interface UserInfoManager {
    /**
     * Get user information by user id
     * @param userId the specific user id
     */
    UserInfo getUserInfoByUserId(Long userId);

    /**
     * Get user information by user username
     * @param username the specific user name.
     */
    UserInfo getUserInfoByUserName(String username);

    /**
     * Login with username and password
     * @param username
     * @param password
     */
    void login(String username, String password);

    /**
     * Register new users
     * @param username
     * @param password
     */
    UserInfo register(String username, String password);

}
