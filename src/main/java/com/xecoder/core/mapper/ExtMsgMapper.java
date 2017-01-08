package com.xecoder.core.mapper;

import com.xecoder.config.MybatisMapper;
import com.xecoder.core.entity.ExtMsg;
import com.xecoder.core.entity.ExtMsgCriteria;
import com.xecoder.core.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExtMsgMapper extends MybatisMapper<User> {
    int countByExample(ExtMsgCriteria example);

    int deleteByExample(ExtMsgCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(ExtMsg record);

    int insertSelective(ExtMsg record);

    List<ExtMsg> selectByExample(ExtMsgCriteria example);

    ExtMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ExtMsg record, @Param("example") ExtMsgCriteria example);

    int updateByExample(@Param("record") ExtMsg record, @Param("example") ExtMsgCriteria example);

    int updateByPrimaryKeySelective(ExtMsg record);

    int updateByPrimaryKey(ExtMsg record);
}