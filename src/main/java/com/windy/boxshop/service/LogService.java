package com.windy.boxshop.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.windy.boxshop.entity.Log;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author windy
 * @since 2024-03-20
 */
public interface LogService extends IService<Log> {
    IPage pageCC(IPage<Log> page, Wrapper wrapper);

    boolean save(Log log);
}
