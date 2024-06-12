package com.windy.boxshop.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.windy.boxshop.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author windy
 * @since 2024-02-29
 */
public interface UserService extends IService<User> {
    IPage pageCC(IPage<User> page, Wrapper wrapper);
}
