package org.kelinlin.constant;


import org.kelinlin.bean.index.EsMapping;

/**
 * ES常用常量
 *
 * @author Chenbin Wang
 * @date 2021/11/17
 */
public class EsConstant {


    /**
     * 查询深度
     * settings
     */
    public static final String MAX_RESULT_WINDOW = "max_result_window";

    /**
     * 分片数
     * settings
     */
    public static final String NUMBER_OF_SHARDS = "number_of_shards";

    /**
     * 副本数
     * settings
     */
    public static final String NUMBER_OF_REPLICAS = "number_of_replicas";

    /**
     * text类型
     * 会分词，然后进行索引
     * 支持模糊、精确查询
     * 不支持聚合
     * mappings
     */
    public static final String TYPE_TEXT = "text";

    /**
     * keyword类型
     * 不进行分词，直接索引
     * 支持模糊、精确查询
     * 支持聚合
     * mappings
     */
    public static final String TYPE_KEYWORD = ".keyword";

    /**
     * 模糊查询匹配标记
     */
    public static final String WILDCARD_MATCH_TAG = "*";

    /**
     * _score
     */
    public static final String TYPE_SCORE = "_score";


    /**
     * {@link EsMapping}
     * EsMapping mapping名
     */
    public static final String MAPPING_NAME = "mappingName";

    /**
     * properties
     */
    public static final String PROPERTIES = "properties";

    /**
     * 默认查询深度
     */
    public static final int DEFAULT_RESULT_WINDOW = 10000000;

    /**
     * es查询默认limit
     */
    public static final int DEFAULT_QUERY_LIMIT = 2000;

    /**
     * es topHits limit
     */
    public static final int DEFAULT_TOP_HITS_LIMIT = 10;

    /**
     * 默认分片数
     */
    public static final int DEFAULT_SHARDS_NUM = 5;

    /**
     * 默认副本数
     */
    public static final int DEFAULT_REPLICAS_NUM = 1;

    /**
     * 默认重试次数
     */
    public static final int DEFAULT_RETRY_TIMES = 0;


    private EsConstant() {
        throw new IllegalStateException("Utility class");
    }

}
