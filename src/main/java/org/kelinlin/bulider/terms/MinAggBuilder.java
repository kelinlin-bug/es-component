package org.kelinlin.bulider.terms;

import lombok.ToString;

/**
 * 聚合 最小值构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 15:02
 */
@ToString
public class MinAggBuilder extends AbstractEsAggQueryBuilder {

    /**
     * 创建最小值构造
     *
     * @param name 最小值别名
     * @return {@link MinAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 15:04
     */
    public static MinAggBuilder create(String name) {
        return new MinAggBuilder(name);
    }

    private MinAggBuilder(String name) {
        super(name);
    }

}
