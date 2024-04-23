package com.windy.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.windy.entity.Storage;
import com.windy.mapper.StorageMapper;
import com.windy.service.StorageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author windy
 * @since 2024-03-18
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {
    @Resource
    private StorageMapper storageMapper;

    @Override
    public IPage pageSelect(IPage<Storage> page, Wrapper wrapper) {
        return storageMapper.pageSelect(page, wrapper);
    }
}
