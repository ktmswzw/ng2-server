package com.xecoder.core.service;

import com.xecoder.core.entity.ExtMsg;

import java.util.List;

/**
 * Created by ZHEJIANG RUIZHENG  on 2014/7/11.
 */
public interface ExtMsgService {

    List<ExtMsg> findList(ExtMsg extMsg);

    void save(ExtMsg extMsg);

    ExtMsg get(int id);

    void update(ExtMsg extMsg);

    void delete(int id);

}
