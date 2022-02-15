package com.hardcore.accounting.dao.mapper;

import com.hardcore.accounting.model.persistence.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {

    @Select("SELECT id, username, password, update_time, create_time FROM hcas_userinfo WHERE id = #{id}")
    public UserInfo getUserInfoByUserId(@Param("id") Long id);

}
