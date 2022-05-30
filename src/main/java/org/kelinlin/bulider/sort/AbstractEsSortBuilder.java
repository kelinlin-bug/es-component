package org.kelinlin.bulider.sort;

import lombok.Getter;
import org.kelinlin.enums.Order;

/**
 * 排序构造基类
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/19 18:31
 */
@Getter
public abstract class AbstractEsSortBuilder<T extends EsSortBuilder> implements EsSortBuilder<T> {
    @Override
    public T getEsSortBuilder() {
        return (T) this;
    }

    @Override
    public T order(Order order) {
        this.order = order;
        return (T) this;
    }

    protected AbstractEsSortBuilder(String field) {
        this.field = field;
    }

    /**
     * 排序方式
     */
    private Order order = Order.ASC;

    /**
     * 排序字段
     */
    private String field;
}
