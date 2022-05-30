package org.kelinlin.bulider.terms;

import org.kelinlin.convert.AggBuilderConvert;

/**
 * es聚合查询条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 13:52
 */
public interface EsAggQueryBuilder {


    /**
     * es查询条件构造
     *
     * @return {@link EsAggQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 15:52
     */
    <T extends EsAggQueryBuilder> T getEsAggQueryBuilder();

    /**
     * 聚合条件拼接
     *
     * @param esAggQueryBuilder 聚合条件
     * @param aggBuilderConvert 聚合转换器
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/25 15:48
     */
    <T extends EsAggQueryBuilder> T subAggregation(EsAggQueryBuilder esAggQueryBuilder, AggBuilderConvert aggBuilderConvert);

    /**
     * 聚合条件拼接
     *
     * @param esAggQueryBuilder 聚合条件
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/25 15:48
     */
    <T extends EsAggQueryBuilder> T subAggregation(EsAggQueryBuilder esAggQueryBuilder);
}
