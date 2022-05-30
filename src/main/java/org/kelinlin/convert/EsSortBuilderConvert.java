package org.kelinlin.convert;

import org.kelinlin.bulider.sort.EsSortBuilder;

/**
 * 排序解析转换器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/25 13:55
 */
public interface EsSortBuilderConvert<T> {

    /**
     * 排序解析转换
     *
     * @param esSortBuilder
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/25 13:56
     */
    T sortBuilderConvert(EsSortBuilder<?> esSortBuilder);
}
