package com.windy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@ApiModel(value = "Storage对象", description = "")
public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "初始容量")
    private Integer initialCapacity;

    @ApiModelProperty(value = "统计容量")
    private Integer calculatedCapacity;

    @ApiModelProperty(value = "剩余容量")
    private Integer remainingCapacity;

    private String name;

}
