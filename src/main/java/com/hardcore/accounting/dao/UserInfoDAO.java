package com.hardcore.accounting.dao;

import com.hardcore.accounting.model.persistence.UserInfo;

public interface UserInfoDAO {

    UserInfo getUserInfoById(Long id);

    UserInfo getUserInfoByUsername(String username);

    void createNewUser(UserInfo userInfo);
}
