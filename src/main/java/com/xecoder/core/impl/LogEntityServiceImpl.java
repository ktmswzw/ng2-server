package com.xecoder.core.impl;


import com.xecoder.core.entity.LogEntity;
import com.xecoder.core.entity.LogEntityCriteria;
import com.xecoder.core.mapper.LogEntityMapper;
import com.xecoder.core.service.LogEntityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("logEntityService")
@Transactional
@SuppressWarnings("unchecked")
public class LogEntityServiceImpl implements LogEntityService {

    @Autowired
    private LogEntityMapper mapper;

    @Override
    public void delete(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<LogEntity> findAll() {
        LogEntityCriteria criteria = new LogEntityCriteria();
        LogEntityCriteria.Criteria cri = criteria.createCriteria();
        return mapper.selectByExample(criteria);
    }

    @Override
    public List<LogEntity> findByExample(LogEntity logEntity) {
        LogEntityCriteria criteria = new LogEntityCriteria();
        LogEntityCriteria.Criteria cri = criteria.createCriteria();

        if (logEntity != null) {
            if (StringUtils.isNotBlank(logEntity.getIpAddress())) {
                cri.andIpAddressEqualTo(logEntity.getIpAddress());
            }

            if (StringUtils.isNotBlank(logEntity.getUsername())) {
                cri.andUsernameEqualTo(logEntity.getUsername());
            }

            if (StringUtils.isNotBlank(logEntity.getLogLevel())) {
                cri.andLogLevelEqualTo(logEntity.getLogLevel());
            }

            if (StringUtils.isNotBlank(logEntity.getMessage())) {
                cri.andMessageLike("%" + logEntity.getMessage() + "%");
            }
        }

        return mapper.selectByExample(criteria);

    }


    @Override
    public LogEntity get(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(LogEntity logEntity) {
        mapper.insertSelective(logEntity);

    }

    @Override
    public void update(LogEntity logEntity) {
        mapper.updateByPrimaryKeySelective(logEntity);

    }

}
