package org.kelinlin.bulider.terms;

import org.kelinlin.constant.EsConstant;

import java.util.Arrays;
import java.util.Collection;

/**
 * es 查询条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 15:58
 */
public class EsQueryBuilders {

    /**
     * 创建termQuery条件构造
     * 不分词，效果类似于sql =
     *
     * @param key   条件名
     * @param value 条件值
     * @return {@link TermEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:01
     */
    public static TermEsQueryBuilder termQuery(String key, Object value) {
        return TermEsQueryBuilder.create(key, value);
    }

    /**
     * 创建termQuery条件构造
     * 不分词，效果类似于sql =
     *
     * @param key   条件名
     * @param value 条件值
     * @return {@link TermEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:01
     */
    public static TermEsQueryBuilder termKeywordQuery(String key, Object value) {
        return TermEsQueryBuilder.create(key + EsConstant.TYPE_KEYWORD, value);
    }

    /**
     * 创建termsQuery条件构造
     * 不分词，效果类似于sql in
     *
     * @param key    条件名
     * @param values 条件值集合
     * @return {@link TermsEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:04
     */
    public static TermsEsQueryBuilder termsQuery(String key, Collection<Object> values) {
        return TermsEsQueryBuilder.create(key, values);
    }

    /**
     * 创建termsQuery条件构造
     * 不分词，效果类似于sql in
     *
     * @param key    条件名
     * @param values 条件值集合
     * @return {@link TermsEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:04
     */
    public static TermsEsQueryBuilder termsKeywordQuery(String key, Collection<Object> values) {
        return TermsEsQueryBuilder.create(key + EsConstant.TYPE_KEYWORD, values);
    }

    /**
     * 创建termsQuery条件构造
     * 不分词，效果类似于sql in
     *
     * @param key    条件名
     * @param values 条件值数组
     * @return {@link TermsEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:04
     */
    public static TermsEsQueryBuilder termsQuery(String key, Object... values) {
        return TermsEsQueryBuilder.create(key, Arrays.asList(values));
    }


    /**
     * 创建matchQuery条件构造
     * 分词查询，效果取决于分词器
     *
     * @param key   条件名
     * @param value 条件值
     * @return {@link MatchEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:15
     */
    public static MatchEsQueryBuilder matchQuery(String key, Object value) {
        return MatchEsQueryBuilder.create(key, value);
    }

    /**
     * 创建multiMatchQuery条件构造
     * 分词查询，效果取决于分词器
     *
     * @param value 条件值
     * @param keys  条件名数组
     * @return {@link MultiMatchEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:15
     */
    public static MultiMatchEsQueryBuilder multiMatchQuery(Object value, String... keys) {
        return MultiMatchEsQueryBuilder.create(keys, value);
    }

    /**
     * 创建wildcardQuery条件构造
     * 模糊查询，效果类似于sql like
     *
     * @param key   条件名
     * @param value 条件值
     * @return {@link WildCardEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:23
     */
    public static WildCardEsQueryBuilder wildcardKeywordQuery(String key, String value) {
        return WildCardEsQueryBuilder.create(key + EsConstant.TYPE_KEYWORD, value);
    }

    /**
     * 创建wildcardQuery条件构造
     * 模糊查询，效果类似于sql like
     *
     * @param key   条件名
     * @param value 条件值
     * @return {@link WildCardEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:23
     */
    public static WildCardEsQueryBuilder wildcardQuery(String key, String value) {
        return WildCardEsQueryBuilder.create(key, value);
    }

    /**
     * 创建rangeQuery条件构造
     * 范围查询，效果类似于sql <，<=等
     *
     * @param key 条件名
     * @return {@link RangeEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:29
     */
    public static RangeEsQueryBuilder rangeQuery(String key) {
        return RangeEsQueryBuilder.create(key);
    }

    /**
     * 创建existsQuery条件构造
     * 存在判断查询，效果类似于sql is not null
     *
     * @param key 条件名
     * @return {@link ExistsEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:48
     */
    public static ExistsEsQueryBuilder existsQuery(String key) {
        return ExistsEsQueryBuilder.create(key);
    }

    /**
     * 创建existsQuery条件构造
     * 存在判断查询，效果类似于sql is not null
     *
     * @param key 条件名
     * @return {@link ExistsEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:48
     */
    public static ExistsEsQueryBuilder existsKeywordQuery(String key) {
        return ExistsEsQueryBuilder.create(key + EsConstant.TYPE_KEYWORD);
    }

    private EsQueryBuilders() {
        throw new IllegalStateException("Utility class");
    }
}
