package com.sherlochao.model;

import lombok.Data;
import lombok.ToString;



/**
 * 查询结果
 */
@Data
@ToString
public class QueryResponseVo implements java.io.Serializable{

    private static final long serialVersionUID = 392690847241237351L;

    private Integer queryNum;
    
    private String queryMsg;
}