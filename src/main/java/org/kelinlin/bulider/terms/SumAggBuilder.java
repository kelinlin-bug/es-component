package org.kelinlin.bulider.terms;

import lombok.ToString;

/**
 * 聚合 求和构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 14:38
 */
@ToString
public class SumAggBuilder extends AbstractEsAggQueryBuilder {

    /**
     * 创建求和构造
     *
     * @param name 求和别名
     * @return {@link SumAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 14:42
     */
    public static SumAggBuilder create(String name) {
        return new SumAggBuilder(name);
    }

    private SumAggBuilder(String name) {
        super(name);
    }

}
