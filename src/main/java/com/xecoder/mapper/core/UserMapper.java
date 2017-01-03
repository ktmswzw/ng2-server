package com.xecoder.mapper.core;

import com.xecoder.config.MybatisMapper;
import com.xecoder.entity.core.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MybatisMapper<User>{

}