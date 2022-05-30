package com.hardcore.accounting.dao.mapper;

import com.hardcore.accounting.dao.provider.RecordSqlProvider;
import com.hardcore.accounting.model.persistence.Record;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface RecordMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO hardcore_test.hcas_record(user_id, amount, note, category, status) "
            + "VALUES (#{userId}, #{amount}, #{note}, #{category}, #{status})")
    int insertRecord(Record record);

    /**
     * 调用sqlprovider是了局部更新，防止每次更新数据不全导致的覆盖问题
     * 即只更新有的部分，更新为null的部分保持原有不变
     * @param record
     * @return
     */
    @UpdateProvider(type = RecordSqlProvider.class, method = "updateRecord")
    int updateRecord(Record record);

    @Select("SELECT id, user_id, amount, note, category, status FROM hardcore_test.hcas_record WHERE id = #{id}")
    @Results({@Result(property = "id", column = "id"),
              @Result(property = "userId", column = "user_id"),
              @Result(property = "amount", column = "amount"),
              @Result(property = "category", column = "category"),
              @Result(property = "status", column = "status"),
              @Result(property = "tagList", javaType = List.class, column = "id",
                       many = @Many(select = "com.hardcore.accounting.dao.mapper."
                                            + "RecordTagMappingMapper.getTagListByRecordId"))})
    Record getRecordByRecordId(@Param("id") Long id);

    @Select("SELECT id, user_id, amount, note, category, status FROM hardcore_test.hcas_record ORDER by create_time")
    @Results({@Result(property = "id", column = "id"),
              @Result(property = "userId", column = "user_id"),
              @Result(property = "amount", column = "amount"),
              @Result(property = "category", column = "category"),
              @Result(property = "status", column = "status"),
              @Result(property = "tagList", javaType = List.class, column = "id",
                      many = @Many(select = "com.hardcore.accounting.dao.mapper."
                                            + "RecordTagMappingMapper.getTagListByRecordId"))})
    List<Record> getRecords(@Param("pageNum") int pageNum,
                            @Param("pageSize") int pageSize);
}
