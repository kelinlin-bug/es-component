package org.kelinlin.bulider.terms;

import lombok.Getter;
import lombok.ToString;

/**
 * es查询rangeQuery条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 15:01
 */
@Getter
@ToString
public class RangeEsQueryBuilder extends AbstractEsQueryBuilder {


    /**
     * 大于
     *
     * @param from 起始点
     * @return {@link RangeEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:36
     */
    public RangeEsQueryBuilder gt(Object from) {
        this.from = from;
        this.includeLower = !DEFAULT_INCLUDE_FLAG;
        return this;
    }

    /**
     * 大于等于
     *
     * @param from 起始点
     * @return {@link RangeEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:37
     */
    public RangeEsQueryBuilder gte(Object from) {
        this.from = from;
        this.includeLower = DEFAULT_INCLUDE_FLAG;
        return this;
    }

    /**
     * 小于
     *
     * @param to 截止点
     * @return {@link RangeEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:37
     */
    public RangeEsQueryBuilder lt(Object to) {
        this.to = to;
        this.includeUpper = !DEFAULT_INCLUDE_FLAG;
        return this;
    }

    /**
     * 小于等于
     *
     * @param to 截止点
     * @return {@link RangeEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:37
     */
    public RangeEsQueryBuilder lte(Object to) {
        this.to = to;
        this.includeUpper = DEFAULT_INCLUDE_FLAG;
        return this;
    }

    /**
     * 创建rangeQuery条件构造
     *
     * @param key 条件名
     * @return {@link RangeEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 15:36
     */
    public static RangeEsQueryBuilder create(String key) {
        return new RangeEsQueryBuilder(key);
    }

    private RangeEsQueryBuilder(String key) {
        this.key = key;
    }

    private RangeEsQueryBuilder() {
        throw new IllegalStateException("Invalid structure");
    }

    /**
     * 条件名
     */
    private String key;

    /**
     * 起始点
     */
    private Object from;

    /**
     * 截止点
     */
    private Object to;

    /**
     * 是否包含起始点
     */
    private Boolean includeLower = RangeEsQueryBuilder.DEFAULT_INCLUDE_FLAG;

    /**
     * 是否包含截止点
     */
    private Boolean includeUpper = RangeEsQueryBuilder.DEFAULT_INCLUDE_FLAG;


    private static final boolean DEFAULT_INCLUDE_FLAG = true;


}
