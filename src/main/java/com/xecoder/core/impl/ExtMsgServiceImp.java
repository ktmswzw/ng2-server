package com.xecoder.core.impl;


import com.xecoder.core.entity.ExtMsg;
import com.xecoder.core.entity.ExtMsgCriteria;
import com.xecoder.core.mapper.ExtMsgMapper;
import com.xecoder.core.service.ExtMsgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class ExtMsgServiceImp implements ExtMsgService {

    @Autowired
    private ExtMsgMapper mapper;

    @Override
    public List<ExtMsg> findList(ExtMsg extMsg) {
        ExtMsgCriteria criteria = new ExtMsgCriteria();
        ExtMsgCriteria.Criteria cri = criteria.createCriteria();
        if (extMsg != null) {
            if (StringUtils.isNotBlank(extMsg.getName())) {
                cri.andNameEqualTo(extMsg.getName());
            }

            if (StringUtils.isNotBlank(extMsg.getType())) {
                cri.andTypeEqualTo(extMsg.getType());
            }

        }
        if (extMsg != null && extMsg.getSort() != null && extMsg.getOrder() != null) {
            criteria.setOrderByClause(extMsg.getSort() + " " + extMsg.getOrder());
        }
        return mapper.selectByExample(criteria);
    }

    @Override
    public void save(ExtMsg extMsg) {
        mapper.insert(extMsg);
    }

    @Override
    public ExtMsg get(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(ExtMsg extMsg) {
        mapper.updateByPrimaryKey(extMsg);
    }

    @Override
    public void delete(int id) {
        mapper.deleteByPrimaryKey(id);
    }
}
