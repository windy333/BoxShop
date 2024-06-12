package com.windy.boxshop.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windy.boxshop.entity.Log;
import com.windy.boxshop.mapper.LogMapper;
import com.windy.boxshop.service.LogService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author windy
 * @since 2024-03-20
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
    @Resource
    private LogMapper logMapper;

    @Override
    public IPage pageCC(IPage<Log> page, Wrapper wrapper) {
        return logMapper.pageCC(page, wrapper);
    }

    @Override
    public boolean save(Log log) {
        int rows = logMapper.insert(log);
        return rows > 0;
    }
}
