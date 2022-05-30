package org.kelinlin.bulider.sort;

import lombok.ToString;
import org.kelinlin.enums.Order;

/**
 * field排序构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 9:26
 */
@ToString
public class FieldEsSortBuilder extends AbstractEsSortBuilder<FieldEsSortBuilder> {

    /**
     * 创建文件排序构造
     *
     * @param param 排序字段
     * @param order 排序方式
     * @return {@link FieldEsSortBuilder}
     * @author Chenbin Wang
     * @date 2022/4/20 9:36
     */
    public static FieldEsSortBuilder create(String param, Order order) {
        return new FieldEsSortBuilder(param).order(order);
    }

    /**
     * 创建文件排序构造
     *
     * @param param 排序字段
     * @return {@link FieldEsSortBuilder}
     * @author Chenbin Wang
     * @date 2022/4/20 9:36
     */
    public static FieldEsSortBuilder create(String param) {
        return new FieldEsSortBuilder(param);
    }

    private FieldEsSortBuilder(String field) {
        super(field);
    }

}
