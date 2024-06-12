package com.windy.boxshop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.windy.boxshop.entity.Storage;
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
public interface StorageMapper extends BaseMapper<Storage> {
    //查询
    IPage pageCC(IPage<Storage> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
