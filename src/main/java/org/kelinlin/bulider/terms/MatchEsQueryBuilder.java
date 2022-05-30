package org.kelinlin.bulider.terms;

import lombok.Getter;
import lombok.ToString;

/**
 * es查询matchQuery条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 14:57
 */
@Getter
@ToString
public class MatchEsQueryBuilder extends AbstractEsQueryBuilder {

    /**
     * 创建matchQuery条件构造
     *
     * @param key   条件名
     * @param value 条件值
     * @return {@link MatchEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 15:42
     */
    public static MatchEsQueryBuilder create(String key, Object value) {
        return new MatchEsQueryBuilder(key, value);
    }

    private MatchEsQueryBuilder(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    private MatchEsQueryBuilder() {
        throw new IllegalStateException("Invalid structure");
    }

    /**
     * 条件名
     */
    private String key;

    /**
     * 条件值
     */
    private Object value;
}
