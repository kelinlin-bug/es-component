package org.kelinlin.bulider.terms;

import lombok.ToString;

/**
 * 聚合 最大值构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 14:49
 */
@ToString
public class MaxAggBuilder extends AbstractEsAggQueryBuilder {

    /**
     * 创建最大值构造
     *
     * @param name 最大值别名
     * @return {@link MaxAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 14:52
     */
    public static MaxAggBuilder create(String name) {
        return new MaxAggBuilder(name);
    }

    private MaxAggBuilder(String name) {
        super(name);
    }

}
