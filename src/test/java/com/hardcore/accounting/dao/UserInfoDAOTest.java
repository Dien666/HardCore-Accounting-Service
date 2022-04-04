package com.hardcore.accounting.dao;

import com.hardcore.accounting.dao.mapper.UserInfoMapper;
import com.hardcore.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserInfoDAOTest {
    @Mock
    private UserInfoMapper userInfoMapper;

    private UserInfoDAO userInfoDAO;

    @BeforeEach
    public void setup() {
        userInfoDAO = new UserInfoDAOImpl(userInfoMapper);
    }

    @Test
    public void testGetUserInfoById () {
        // Arrange
        val userId = 100L;
        val username = "hardcore";
        val password = "hardcore";

        val userInfo = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();
        doReturn(userInfo).when(userInfoMapper).getUserInfoByUserId(userId);
        // Act
        val result = userInfoDAO.getUserInfoById(userId);

        // Assert
        assertEquals(userInfo, result);

        verify(userInfoMapper).getUserInfoByUserId(userId);

    }
}
