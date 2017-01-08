package com.xecoder.core.mapper;

import com.xecoder.config.MybatisMapper;
import com.xecoder.core.entity.Reset;
import com.xecoder.core.entity.ResetCriteria;
import com.xecoder.core.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResetMapper extends MybatisMapper<User> {
    int countByExample(ResetCriteria example);

    int deleteByExample(ResetCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Reset record);

    int insertSelective(Reset record);

    List<Reset> selectByExample(ResetCriteria example);

    Reset selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Reset record, @Param("example") ResetCriteria example);

    int updateByExample(@Param("record") Reset record, @Param("example") ResetCriteria example);

    int updateByPrimaryKeySelective(Reset record);

    int updateByPrimaryKey(Reset record);
}