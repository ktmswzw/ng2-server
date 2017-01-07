package com.xecoder.core.mapper;

import com.xecoder.core.entity.Reset;
import com.xecoder.core.entity.ResetCriteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResetMapper {
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