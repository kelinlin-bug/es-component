package org.kelinlin.convert;

import org.kelinlin.bulider.terms.EsAggQueryBuilder;

/**
 * 聚合条件转换器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/25 11:02
 */
public interface AggBuilderConvert<T> {

    /**
     * 聚合条件转换器
     *
     * @param esAggQueryBuilder
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/25 11:04
     */
    T buildersConvert(EsAggQueryBuilder esAggQueryBuilder);

    /**
     * 将目标聚合条件合并至源聚合条件
     *
     * @param score  源聚合条件
     * @param target 目标聚合条件
     * @return {@link T}
     * @throws
     * @author Chenbin Wang
     * @date 2022/4/26 9:35
     */
    T subAggBuilder(T score, T target);
}
