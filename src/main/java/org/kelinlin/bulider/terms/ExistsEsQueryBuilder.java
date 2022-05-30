package org.kelinlin.bulider.terms;

import lombok.Getter;
import lombok.ToString;

/**
 * es查询existsQuery条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 16:41
 */
@Getter
@ToString
public class ExistsEsQueryBuilder extends AbstractEsQueryBuilder {

    /**
     * 创建existsQuery条件构造
     *
     * @param key
     * @return {@link ExistsEsQueryBuilder}
     * @throws
     * @author Chenbin Wang
     * @date 2022/4/18 16:45
     */
    public static ExistsEsQueryBuilder create(String key) {
        return new ExistsEsQueryBuilder(key);
    }

    private ExistsEsQueryBuilder(String key) {
        this.key = key;
    }

    private ExistsEsQueryBuilder() {
        throw new IllegalStateException("Invalid structure");
    }

    /**
     * 条件名
     */
    private String key;
}
