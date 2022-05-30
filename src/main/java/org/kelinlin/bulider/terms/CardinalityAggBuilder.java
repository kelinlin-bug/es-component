package org.kelinlin.bulider.terms;

import lombok.ToString;

/**
 * 聚合 近似与基数的数量构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 11:30
 */
@ToString
public class CardinalityAggBuilder extends KeywordEsQueryBuilder {

    /**
     * 创建聚合 近似与基数的数量构造
     *
     * @param name 数量别名
     * @return {@link CardinalityAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 13:47
     */
    public static CardinalityAggBuilder create(String name) {
        return new CardinalityAggBuilder(name);
    }


    private CardinalityAggBuilder(String name) {
        super(name);
    }
}
