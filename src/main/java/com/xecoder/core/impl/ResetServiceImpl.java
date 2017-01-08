package com.xecoder.core.impl;



import com.xecoder.core.entity.Reset;
import com.xecoder.core.entity.ResetCriteria;
import com.xecoder.core.mapper.ResetMapper;
import com.xecoder.core.service.ResetService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class ResetServiceImpl implements ResetService{

    @Autowired
    private ResetMapper resetMapper;
    @Override
    public void update(Reset reset) {
        resetMapper.updateByPrimaryKeySelective(reset);
    }

    @Override
    public void save(Reset reset) {
        //添加前失效之前的数据
        Reset reset1 = new Reset();
        reset1.setMail(reset.getMail());
        List<Reset> list= find(reset1);
        for(Reset reset2 : list)
        {
            reset2.setState(0);
            update(reset2);
        }
        resetMapper.insertSelective(reset);
    }

    @Override
    public List<Reset> find(Reset reset) {
        return resetMapper.selectByExample(getCriteria(reset));
    }

    public ResetCriteria getCriteria(Reset reset)
    {
        ResetCriteria criteria = new ResetCriteria();
        ResetCriteria.Criteria cri = criteria.createCriteria();
        if (reset != null) {
            cri.andStateEqualTo(1);
            if (StringUtils.isNotBlank(reset.getMail())) {
                cri.andMailEqualTo(reset.getMail());
            }
            if (StringUtils.isNotBlank(reset.getSalt())) {
                cri.andSaltEqualTo(reset.getSalt());
            }
            if(reset.getRequestDate()!=null)
            {
                cri.addCriterion(" request_date > date_add(unix_timestamp('"+reset.getRequestDate().toString()+"'), interval -30 minute) ");
            }
            //cri.addCriterion(" cust_Id in (1,2,3)");
        }
        if(reset != null && reset.getSort() != null && reset.getOrder() != null){
            criteria.setOrderByClause(reset.getSort() + " " + reset.getOrder());
        }
        return criteria;
    }
}
