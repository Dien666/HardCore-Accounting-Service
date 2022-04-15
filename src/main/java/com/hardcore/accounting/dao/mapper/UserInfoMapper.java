package com.hardcore.accounting.dao.mapper;

import com.hardcore.accounting.model.persistence.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserInfoMapper {

    @Select("SELECT id, username, password, update_time, create_time FROM hcas_userinfo WHERE id = #{id}")
    UserInfo getUserInfoByUserId(@Param("id") Long id);

    @Select("SELECT id, username, password, salt, update_time, create_time FROM hcas_userinfo WHERE username = #{username}")
    UserInfo getUserInfoByUserName(@Param("username") String username);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("Insert into hcas_userinfo(username, password, salt, create_time) " +
            "values (#{username}, #{password}, #{salt}, #{createTime})")
    int createNewUser(UserInfo userInfo);

}
