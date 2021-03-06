package com.hardcore.accounting.dao.mapper;

import com.hardcore.accounting.dao.provider.TagSqlProvider;
import com.hardcore.accounting.model.persistence.Tag;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface TagMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO hardcore_test.hcas_tag(description, user_id, status) "
            + "VALUES (#{description}, #{userId}, #{status})")
    int insertTag(Tag tag);

    /**
     * 调用sqlprovider是了局部更新，防止每次更新数据不全导致的覆盖问题
     * 即只更新有的部分，更新为null的部分保持原有不变
     * @param tag
     * @return
     */
    @Options(resultSets = "id, description, user_id, status, create_time, update_time")
    @UpdateProvider(type = TagSqlProvider.class, method = "updateTag")
    int updateTag(Tag tag);

    @Select("SELECT id, description, user_id, status, create_time, update_time FROM hardcore_test.hcas_tag "
            + "WHERE id = #{id}")
    @Results({
                 @Result(column = "id", property = "id"),
                 @Result(column = "description", property = "description"),
                 @Result(column = "user_id", property = "userId"),
                 @Result(column = "status", property = "status"),
                 @Result(column = "create_time", property = "createTime"),
                 @Result(column = "update_time", property = "updateTime"),
             })
    Tag getTagByTagId(@Param("id") Long tagId);

    @Select("SELECT id, description, user_id, status, create_time, update_time FROM hardcore_test.hcas_tag "
            + "WHERE description = #{description} and user_id = #{userId}")
    @Results({
                 @Result(column = "id", property = "id"),
                 @Result(column = "description", property = "description"),
                 @Result(column = "user_id", property = "userId"),
                 @Result(column = "status", property = "status"),
                 @Result(column = "create_time", property = "createTime"),
                 @Result(column = "update_time", property = "updateTime"),
             })
    Tag getTagByDescription(@Param("description") String description, @Param("userId") Long userId);


    @Select("SELECT id, description, user_id, status, create_time, update_time "
            + "FROM hardcore_test.hcas_tag WHERE user_id = #{userId} ORDER BY id")
    @Results({
                 @Result(column = "id", property = "id"),
                 @Result(column = "description", property = "description"),
                 @Result(column = "user_id", property = "userId"),
                 @Result(column = "status", property = "status"),
                 @Result(column = "create_time", property = "createTime"),
                 @Result(column = "update_time", property = "updateTime"),
             })
    List<Tag> getTags(@Param("userId") Long userId,
                      @Param("pageNum") int pageNum,
                      @Param("pageSize") int pageSize);

    @SelectProvider(type = TagSqlProvider.class, method = "getTagListByIds")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "description", property = "description"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "status", property = "status")
    })
    List<Tag> getTagListByIds(@Param("id") List<Long> ids);

}
