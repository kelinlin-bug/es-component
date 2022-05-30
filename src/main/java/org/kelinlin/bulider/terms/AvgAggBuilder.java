package org.kelinlin.bulider.terms;

import lombok.ToString;

/**
 * 聚合 平均值构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 15:10
 */
@ToString
public class AvgAggBuilder extends AbstractEsAggQueryBuilder {


    /**
     * 创建平均值构造
     *
     * @param name 平均值别名
     * @return {@link AvgAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 15:16
     */
    public static AvgAggBuilder create(String name) {
        return new AvgAggBuilder(name);
    }

    private AvgAggBuilder(String name) {
        super(name);
    }
}
