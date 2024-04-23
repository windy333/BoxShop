package com.windy.controller;


import com.windy.common.Result;
import com.windy.entity.Calculate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;

@RestController
public class CapacityController {

    // 调整因子AF
    private static final double[] AF = {0.37, 0.26, 0.28, 0.34, 0.63, 0.92, 0.23, 0.47, 0.39};

    @PostMapping("/calculateUnitCapacity")
    public Result calculateUnitCapacity(@RequestBody Calculate calculate) {
        double unitCapacity = calculateUnitCapacityLogic(calculate.getVolume(),
                calculate.getWeight(), calculate.getSpecies());
        // 单位容量保留一位小数
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedUnitCapacity = df.format(unitCapacity);
        return Result.suc(formattedUnitCapacity);
    }

    // 计算单位容量
    public double calculateUnitCapacityLogic(double volume, double weight, int species) {
        double af = AF[species - 1]; // 获取对应物品类别的调整因子
        return volume * weight * af;
    }
}