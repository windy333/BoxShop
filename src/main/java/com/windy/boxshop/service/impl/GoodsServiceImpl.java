package com.windy.boxshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windy.boxshop.entity.Goods;
import com.windy.boxshop.mapper.GoodsMapper;
import com.windy.boxshop.service.GoodsService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author windy
 * @since 2024-03-18
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public IPage pageCC(IPage<Goods> page, Wrapper wrapper) {
        return goodsMapper.pageCC(page, wrapper);
    }

    @Override
    public boolean updateQuantity(Goods goods) {
        int rows = goodsMapper.updateQuantity(goods);
        return rows > 0;
    }
}
