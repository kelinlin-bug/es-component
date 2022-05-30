package org.kelinlin.bulider.terms;

import lombok.ToString;

/**
 * 聚合 数量构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 16:09
 */
@ToString
public class ValueCountAggBuilder extends KeywordEsQueryBuilder {

    /**
     * 创建数量构造
     *
     * @param name 数量别名
     * @return {@link ValueCountAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 16:12
     */
    public static ValueCountAggBuilder create(String name) {
        return new ValueCountAggBuilder(name);
    }

    private ValueCountAggBuilder(String name) {
        super(name);
    }
}
