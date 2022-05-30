package org.kelinlin.bulider.terms;

/**
 * es 聚合查询条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 16:19
 */
public class EsAggQueryBuilders {

    /**
     * 平均值聚合
     *
     * @param name 聚合名称
     * @return {@link AvgAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 16:37
     */
    public static AvgAggBuilder avg(String name) {
        return AvgAggBuilder.create(name);
    }

    /**
     * 平均值聚合
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link AvgAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 16:37
     */
    public static AvgAggBuilder avg(String name, String field) {
        return AvgAggBuilder.create(name).field(field);
    }

    /**
     * 近似数据计数聚合
     *
     * @param name 聚合名称
     * @return {@link CardinalityAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 16:41
     */
    public static CardinalityAggBuilder cardinality(String name) {
        return CardinalityAggBuilder.create(name);
    }

    /**
     * 近似数据计数聚合
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link CardinalityAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 16:42
     */
    public static CardinalityAggBuilder cardinality(String name, String field) {
        return CardinalityAggBuilder.create(name).field(field);
    }

    /**
     * 近似数据计数聚合 (命中字段为keyword类型)
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link CardinalityAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 16:42
     */
    public static CardinalityAggBuilder cardinalityKeywordField(String name, String field) {
        return CardinalityAggBuilder.create(name).keywordField(field);
    }

    /**
     * 最大值聚合
     *
     * @param name 聚合名称
     * @return {@link MaxAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 16:47
     */
    public static MaxAggBuilder max(String name) {
        return MaxAggBuilder.create(name);
    }

    /**
     * 最大值聚合
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link MaxAggBuilder}
     * @throws
     * @author Chenbin Wang
     * @date 2022/4/24 16:48
     */
    public static MaxAggBuilder max(String name, String field) {
        return MaxAggBuilder.create(name).field(field);
    }

    /**
     * 最小值聚合
     *
     * @param name 聚合名称
     * @return {@link MinAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 16:50
     */
    public static MinAggBuilder min(String name) {
        return MinAggBuilder.create(name);
    }

    /**
     * 最小值聚合
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link MinAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 16:50
     */
    public static MinAggBuilder min(String name, String field) {
        return MinAggBuilder.create(name).field(field);
    }

    /**
     * 缺失聚合
     *
     * @param name 聚合名称
     * @return {@link MissingAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 17:08
     */
    public static MissingAggBuilder miss(String name) {
        return MissingAggBuilder.create(name);
    }

    /**
     * 缺失聚合
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link MissingAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 17:08
     */
    public static MissingAggBuilder miss(String name, String field) {
        return MissingAggBuilder.create(name).field(field);
    }

    /**
     * 缺失聚合（命中字段为keyword类型）
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link MissingAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 17:09
     */
    public static MissingAggBuilder missKeywordField(String name, String field) {
        return MissingAggBuilder.create(name).keywordField(field);
    }

    /**
     * 求和聚合
     *
     * @param name 聚合名称
     * @return {@link SumAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 17:11
     */
    public static SumAggBuilder sum(String name) {
        return SumAggBuilder.create(name);
    }

    /**
     * 求和聚合
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link SumAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 17:11
     */
    public static SumAggBuilder sum(String name, String field) {
        return SumAggBuilder.create(name).field(field);
    }

    /**
     * 分组聚合
     *
     * @param name 聚合名称
     * @return {@link TermsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 17:14
     */
    public static TermsAggBuilder terms(String name) {
        return TermsAggBuilder.create(name);
    }

    /**
     * 分组聚合
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link TermsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 17:14
     */
    public static TermsAggBuilder terms(String name, String field) {
        return TermsAggBuilder.create(name).field(field);
    }

    /**
     * 分组聚合（命中字段为keyword类型）
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link TermsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 17:14
     */
    public static TermsAggBuilder termsKeywordField(String name, String field) {
        return TermsAggBuilder.create(name).keywordField(field);
    }

    /**
     * 返回doc数据
     *
     * @param name
     * @return {@link TopHitsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/25 9:33
     */
    public static TopHitsAggBuilder topHits(String name) {
        return TopHitsAggBuilder.create(name);
    }

    /**
     * 数量聚合
     *
     * @param name 聚合名称
     * @return {@link ValueCountAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/25 9:44
     */
    public static ValueCountAggBuilder count(String name) {
        return ValueCountAggBuilder.create(name);
    }

    /**
     * 数量聚合
     *
     * @param name  聚合名称
     * @param field 命中字段
     * @return {@link ValueCountAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/25 9:45
     */
    public static ValueCountAggBuilder count(String name, String field) {
        return ValueCountAggBuilder.create(name).field(field);
    }

    /**
     * 数量聚合（命中字段为keyword类型）
     *
     * @param name  聚合名称
     * @param field 命中索引
     * @return {@link ValueCountAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/25 9:45
     */
    public static ValueCountAggBuilder countKeywordField(String name, String field) {
        return ValueCountAggBuilder.create(name).keywordField(field);
    }


    private EsAggQueryBuilders() {
        throw new IllegalStateException("Utility class");
    }
}
