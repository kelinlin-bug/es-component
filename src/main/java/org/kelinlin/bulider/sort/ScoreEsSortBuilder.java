package org.kelinlin.bulider.sort;

import lombok.ToString;
import org.kelinlin.constant.EsConstant;
import org.kelinlin.enums.Order;

/**
 * 分值排序构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 9:37
 */
@ToString
public class ScoreEsSortBuilder extends AbstractEsSortBuilder<ScoreEsSortBuilder> {

    /**
     * 创建分值排序构造
     *
     * @param order 排序方式
     * @return {@link ScoreEsSortBuilder}
     * @author Chenbin Wang
     * @date 2022/4/20 9:43
     */
    public static ScoreEsSortBuilder create(Order order) {
        return new ScoreEsSortBuilder().order(order);
    }

    /**
     * 创建分值排序构造
     *
     * @return {@link ScoreEsSortBuilder}
     * @throws
     * @author Chenbin Wang
     * @date 2022/4/20 9:43
     */
    public static ScoreEsSortBuilder create() {
        return new ScoreEsSortBuilder();
    }

    private ScoreEsSortBuilder() {
        super(EsConstant.TYPE_SCORE);
    }
}
