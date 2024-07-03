package com.windy.boxshop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.windy.boxshop.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author windy
 * @since 2024-03-20
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {
    //查询
    IPage<Log> pageCC(IPage<Log> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    //保存日志
    int insert(Log log);
}
