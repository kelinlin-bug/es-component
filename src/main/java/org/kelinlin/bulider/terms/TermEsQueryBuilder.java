package org.kelinlin.bulider.terms;

import lombok.Getter;
import lombok.ToString;

/**
 * es查询termQuery条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 14:49
 */
@Getter
@ToString
public class TermEsQueryBuilder extends AbstractEsQueryBuilder {

    /**
     * 创建termQuery条件构造
     *
     * @param key   条件名
     * @param value 条件值
     * @return {@link TermEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 15:34
     */
    public static TermEsQueryBuilder create(String key, Object value) {
        return new TermEsQueryBuilder(key, value);
    }

    private TermEsQueryBuilder(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    private TermEsQueryBuilder() {
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
