package com.hardcore.accounting.controller;

import com.hardcore.accounting.Controllers.UserController;
import com.hardcore.accounting.converter.c2s.UserInfoC2SConverter;
import com.hardcore.accounting.exception.GlobalExceptionHandler;
import com.hardcore.accounting.exception.ResourceNotFoundException;
import com.hardcore.accounting.manager.UserInfoManager;
import com.hardcore.accounting.model.common.UserInfo;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserInfoManager userInfoManager;
    @Mock
    private UserInfoC2SConverter userInfoC2SConverter;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    public void teardown() {
        reset(userInfoManager);
    }

    @Test
    public void testGetUserInfoByUserId() throws Exception {
        // Arrange
        long userId = 100L;
        val username = "hardcore";
        String password = "hardcore";
        val createTime = LocalDate.now();

        val userInfoCommon = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .createTime(createTime)
                .build();

        com.hardcore.accounting.model.service.UserInfo userInfoService = com.hardcore.accounting.model.service.UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();

        doReturn(userInfoCommon).when(userInfoManager).getUserInfoByUserId(anyLong());

        doReturn(userInfoService).when(userInfoC2SConverter).convert(userInfoCommon);

        // Act & Assert
        mockMvc.perform(get("/v1.0/users/"+userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("{\"id\":100,\"username\":\"hardcore\",\"password\":\"hardcore\"}"));

        verify(userInfoManager).getUserInfoByUserId(anyLong());
        verify(userInfoC2SConverter).convert(userInfoCommon);

    }

    @Test
    public void testGetUserInfoByUserIdWithInvalidUserId() throws Exception {
        // Arrange
        val userId = -100L;
        doThrow(new ResourceNotFoundException(String.format("The user id %s is invalid", userId)))
                .when(userInfoManager).getUserInfoByUserId(anyLong());

        // Act & Assert
        mockMvc.perform(get("/v1.0/users/"+userId))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("{\"code\":\"INVALID_PARAMETER\",\"errorType\":\"Client\",\"message\":\"The user id -100 is invalid\",\"statusCode\":400}"));

        // Assert
    }

}
