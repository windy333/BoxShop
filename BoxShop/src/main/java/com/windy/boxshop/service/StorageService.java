package com.windy.boxshop.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.windy.boxshop.entity.Storage;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author windy
 * @since 2024-03-18
 */
public interface StorageService extends IService<Storage> {
    IPage pageCC(IPage<Storage> page, Wrapper wrapper);
}
