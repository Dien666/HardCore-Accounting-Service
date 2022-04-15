package com.hardcore.accounting.model.common;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class UserInfo {
    private Long id;
    private String salt;
    private String username;
    private String password;
    private LocalDate createTime;
}
