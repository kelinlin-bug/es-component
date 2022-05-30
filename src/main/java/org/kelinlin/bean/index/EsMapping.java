package org.kelinlin.bean.index;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * EsMapping 用于创建index时设置mappings
 *
 * @author Chenbin Wang
 * @date 2021/11/15
 */
@Getter
@Builder
@ToString
public class EsMapping {

    /**
     * EsMapping名 字段名
     */
    private String mappingName;

    /**
     * 类型
     */
    private String type;

    /**
     * 分词器
     */
    private String analyzer;

    /**
     * 查询分词器
     */
    private String search_analyzer;

    /**
     * 相似度算法
     */
    private String similarity;
}
