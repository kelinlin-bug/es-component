package org.kelinlin.bean.response;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * es 聚合 返回
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 17:23
 */
@Data
@ToString
public class EsAggResponse {

    /**
     * 状态
     */
    private Integer status = -1;

    /**
     * 索引名
     */
    private String indexName;

    /**
     * 聚合数据
     * key 聚合名称
     * value 聚合内容
     * 如果聚合条件中有分组项，可能存在多层嵌套
     */
    private Map<String, Object> aggMap;
}
