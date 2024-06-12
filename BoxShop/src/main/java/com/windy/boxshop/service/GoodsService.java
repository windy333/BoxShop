package com.windy.boxshop.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.windy.boxshop.entity.Goods;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author windy
 * @since 2024-03-18
 */
public interface GoodsService extends IService<Goods> {
    IPage pageCC(IPage<Goods> page, Wrapper wrapper);

    boolean updateQuantity(Goods goods);
}
