package com.windy.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windy.entity.User;
import com.windy.mapper.UserMapper;
import com.windy.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author windy
 * @since 2024-02-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public IPage pageSelect(IPage<User> page, Wrapper wrapper) {
        return userMapper.pageSelect(page, wrapper);
    }
}
