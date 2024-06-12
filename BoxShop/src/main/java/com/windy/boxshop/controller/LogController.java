package com.windy.boxshop.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.windy.boxshop.common.QueryPagePram;
import com.windy.boxshop.common.Result;
import com.windy.boxshop.entity.Goods;
import com.windy.boxshop.entity.Log;
import com.windy.boxshop.service.GoodsService;
import com.windy.boxshop.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author windy
 * @since 2024-03-20
 */
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;
    @Autowired
    private GoodsService goodsService;

    //添加日志记录
    @PostMapping("/save")
    public Result save(@RequestBody Log log) {
        log.setCreatetime(LocalDateTime.now()); // 设置当前时间为操作时间
        // 根据货品ID查询对应的货品信息
        Goods goods = goodsService.getById(log.getGoods());

        if (goods == null) {
            return Result.fail();
        }

        // 更新货品数量
        if (log.getType() == 1) {
            goods.setQuantity(goods.getQuantity() + log.getCount());
        } else if (log.getType() == 2) {
            if (goods.getQuantity() < log.getCount()) {
                return Result.fail();
            }
            goods.setQuantity(goods.getQuantity() - log.getCount());
        } else {
            return Result.fail();
        }

        // 更新货品信息（只更新与total_volume计算有关的字段）
        boolean updateResult = goodsService.updateQuantity(goods);

        if (!updateResult) {
            return Result.fail();
        }

        // 保存操作日志
        boolean saveLogResult = logService.save(log);
        if (!saveLogResult) {
            return Result.fail();
        }

        return Result.suc();
    }

    //分页查询日志记录
    @PostMapping("/listPage")
    public Result listPageC1(@RequestBody QueryPagePram query) {

//        HashMap param = query.getParam();
//        String date = (String) param.get("date");
//        String type = (String) param.get("type");
//        String operator = (String) param.get("operator");

        IPage<Log> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Log> lambdaQueryWrapper = new QueryWrapper();

        IPage result = logService.pageCC(page, lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }
}
