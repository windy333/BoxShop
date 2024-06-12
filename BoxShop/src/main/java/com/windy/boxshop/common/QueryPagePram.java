package com.windy.boxshop.common;

import lombok.Data;

import java.util.HashMap;

//分页功能
@Data
public class QueryPagePram {
    public static int PAGE_SIZE = 8;
    public static int PAGE_NUM = 1;
    private int pageSize = PAGE_SIZE;
    private int pageNum = PAGE_NUM;
    private HashMap param = new HashMap();

}
