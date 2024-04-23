package com.windy.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.windy.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author windy
 * @since 2024-03-18
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    //查询(忽略报错)
    IPage pageSelect(IPage<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    //对数量的更新
    int updateQuantity(Goods goods);
}
