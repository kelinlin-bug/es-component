package org.kelinlin.bulider.terms;

import lombok.Getter;
import lombok.ToString;

/**
 * es查询multiMatchQuery条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 14:58
 */
@Getter
@ToString
public class MultiMatchEsQueryBuilder extends AbstractEsQueryBuilder {

    /**
     * 创建multiMatchQuery条件构造
     *
     * @param keys  条件名
     * @param value 条件值数组
     * @return {@link MultiMatchEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 15:40
     */
    public static MultiMatchEsQueryBuilder create(String[] keys, Object value) {
        return new MultiMatchEsQueryBuilder(keys, value);
    }

    private MultiMatchEsQueryBuilder(String[] keys, Object value) {
        this.keys = keys;
        this.value = value;
    }

    private MultiMatchEsQueryBuilder() {
        throw new IllegalStateException("Invalid structure");
    }

    /**
     * 条件名
     */
    private String[] keys;

    /**
     * 条件值数组
     */
    private Object value;
}
