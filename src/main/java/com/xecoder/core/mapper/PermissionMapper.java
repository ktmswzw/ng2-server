package com.xecoder.core.mapper;

import com.xecoder.config.MybatisMapper;
import com.xecoder.core.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by vincent on 1/8/17.
 */

@Mapper
public interface PermissionMapper extends MybatisMapper<Permission> {
}
