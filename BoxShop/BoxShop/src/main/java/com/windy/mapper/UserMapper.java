package com.windy.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.windy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author windy
 * @since 2024-02-29
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 查询,需要在xml中添加sql语句
    IPage pageSelect(IPage<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
