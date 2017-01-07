package com.xecoder.core.mapper;

import com.xecoder.core.entity.Permission;
import com.xecoder.core.entity.PermissionCriteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    int countByExample(PermissionCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    int deleteByExample(PermissionCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    int insert(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    int insertSelective(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    List<Permission> selectByExample(PermissionCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    Permission selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Permission record, @Param("example") PermissionCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Permission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_permission
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Permission record);
}