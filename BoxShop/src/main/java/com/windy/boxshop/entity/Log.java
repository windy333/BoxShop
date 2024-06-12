package com.windy.boxshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author windy
 * @since 2024-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Log对象", description = "")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "货品id")
    private Integer goods;

    @ApiModelProperty(value = "操作员")
    @TableField("userId")
    private Integer userid;

    @ApiModelProperty(value = "审批员")
    private Integer adminId;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createtime;

    @ApiModelProperty(value = "备注")
    private String remark;

    //设计连表查询字段
    private String storage;
    private String username;
    private String adminname;
    private String goodsname;
    private String storagename;

    @TableField(exist = false)
    private Integer type;


}
