package org.kelinlin.bulider.terms;

import lombok.ToString;

/**
 * 聚合 缺失构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 15:34
 */
@ToString
public class MissingAggBuilder extends KeywordEsQueryBuilder {

    /**
     * 创建缺失构造
     *
     * @param name 缺失别名
     * @return {@link MissingAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 15:42
     */
    public static MissingAggBuilder create(String name) {
        return new MissingAggBuilder(name);
    }

    private MissingAggBuilder(String name) {
        super(name);
    }
}
