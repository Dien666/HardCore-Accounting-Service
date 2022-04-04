package com.hardcore.accounting.manager;

import com.hardcore.accounting.converter.p2c.UserInfoP2CConverter;
import com.hardcore.accounting.dao.UserInfoDAO;
import com.hardcore.accounting.dao.UserInfoDAOImpl;
import com.hardcore.accounting.exception.ResourceNotFoundException;
import com.hardcore.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserInfoManagerTest {

    private UserInfoManager userInfoManager;

    @Mock
    private UserInfoDAO userInfoDAO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        userInfoManager = new UserInfoManagerImpl(userInfoDAO, new UserInfoP2CConverter());
    }

    @Test
    public void testAddMethod() {
        val userId = 1L;
        val username = "hardcore";
        val password = "hardcore";

        val userInfo = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();

        doReturn(userInfo).when(userInfoDAO).getUserInfoById(eq(userId));

        val result = userInfoManager.getUserInfoByUserId(userId);
        // Junit 5
//        assertEquals(userId, result.getId());
//        assertEquals("hardcore", result.getUsername());
//        assertEquals("hardcore", result.getPassword());
        // AssertJ
        assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);

        verify(userInfoDAO).getUserInfoById(userId);
    }

    @Test
    void testUserInfoByUserIdWithInvalidUserId() {
        val userId = -1L;

        doReturn(null).when(userInfoDAO).getUserInfoById(eq(userId));

        assertThrows(ResourceNotFoundException.class, () -> userInfoManager.getUserInfoByUserId(userId));

        verify(userInfoDAO).getUserInfoById(userId);
    }
}
