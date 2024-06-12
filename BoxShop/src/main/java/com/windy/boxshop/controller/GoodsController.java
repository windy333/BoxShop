package com.windy.boxshop.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.windy.boxshop.common.QueryPagePram;
import com.windy.boxshop.common.Result;
import com.windy.boxshop.entity.Goods;
import com.windy.boxshop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author windy
 * @since 2024-03-18
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods) {
        return goodsService.save(goods) ? Result.suc() : Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return goodsService.removeById(id) ? Result.suc() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods) {
        return goodsService.updateById(goods) ? Result.suc() : Result.fail();
    }

    //全部查询,这里要做数据可视化
    @GetMapping("/listAll")
    public Result listAll() {
        return Result.suc(goodsService.list());
    }

    //分页查询
    @PostMapping("/listPage")
    public Result listPageC1(@RequestBody QueryPagePram query) {

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String storageId = (String) param.get("storageId");
        String category = (String) param.get("category");

        IPage<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper();
        //输入框查询，返回给前端的判断
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Goods::getName, name);
        }
        if (StringUtils.isNotBlank(storageId) && !"null".equals(storageId)) {
            lambdaQueryWrapper.like(Goods::getStorageId, storageId);
        }
        if (StringUtils.isNotBlank(category) && !"null".equals(category)) {
            lambdaQueryWrapper.like(Goods::getCategory, category);
        }

        IPage result = goodsService.pageCC(page, lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }


}
