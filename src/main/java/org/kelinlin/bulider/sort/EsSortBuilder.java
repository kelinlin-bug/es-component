package org.kelinlin.bulider.sort;

import org.kelinlin.enums.Order;

/**
 * 排序构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/19 18:26
 */
public interface EsSortBuilder<T extends EsSortBuilder> {

    /**
     * 获取排序构造
     *
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/20 9:45
     */
    T getEsSortBuilder();


    /**
     * 设置排序方式
     *
     * @param order 排序方式
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/20 9:45
     */
    T order(Order order);
}
