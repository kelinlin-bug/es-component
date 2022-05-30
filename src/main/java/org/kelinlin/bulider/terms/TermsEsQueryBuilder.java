package org.kelinlin.bulider.terms;

import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

/**
 * es查询termsQuery条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 14:55
 */
@Getter
@ToString
public class TermsEsQueryBuilder extends AbstractEsQueryBuilder {

    /**
     * 创建termsQuery条件构造
     *
     * @param key    条件名
     * @param values values 条件值集合
     * @return
     */
    public static TermsEsQueryBuilder create(String key, Collection<Object> values) {
        return new TermsEsQueryBuilder(key, values);
    }

    private TermsEsQueryBuilder(String key, Collection<Object> values) {
        this.key = key;
        this.values = values;
    }

    private TermsEsQueryBuilder() {
        throw new IllegalStateException("Invalid structure");
    }

    /**
     * 条件名
     */
    private String key;

    /**
     * 条件值集合
     */
    private Collection<Object> values;
}
