package com.windy.boxshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author windy
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Goods对象", description = "")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "物品名")
    private String name;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "分类")
    private String category;

    @ApiModelProperty(value = "仓库ID")
    private String storageId;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "单位容量")
    private BigDecimal unitVolume;

    @ApiModelProperty(value = "总容量")
    private BigDecimal totalVolume;

    @TableField(exist = false)
    private String storagename;   //后续添加数据(连表查询)

}


