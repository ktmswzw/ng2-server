package com.xecoder.core.service;

import com.xecoder.core.entity.Reset;

import java.util.List;

/**
 * Created by ZHEJIANG RUIZHENG  on 2014/8/30.
 */
public interface ResetService {
    void update(Reset reset);
    void save(Reset reset);
    List<Reset> find(Reset reset);
}
