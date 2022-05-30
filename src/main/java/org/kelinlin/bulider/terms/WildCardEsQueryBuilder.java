package org.kelinlin.bulider.terms;

import lombok.ToString;
import org.kelinlin.constant.EsConstant;

/**
 * es查询wildcardQuery条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 14:59
 */
@ToString
public class WildCardEsQueryBuilder extends AbstractEsQueryBuilder {

    /**
     * 前向匹配
     *
     * @return {@link WildCardEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:27
     */
    public WildCardEsQueryBuilder matchHead() {
        this.headMatch = !DEFAULT_MATCH_FLAG;
        return this;
    }

    /**
     * 末尾匹配
     *
     * @return {@link WildCardEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 16:27
     */
    public WildCardEsQueryBuilder matchEnd() {
        this.endMatch = !DEFAULT_MATCH_FLAG;
        return this;
    }

    /**
     * 创建wildcardQuery条件构造
     *
     * @param key   条件名
     * @param value 条件值
     * @return {@link WildCardEsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 15:27
     */
    public static WildCardEsQueryBuilder create(String key, String value) {
        return new WildCardEsQueryBuilder(key, value);
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        StringBuilder valueStrBuilder = new StringBuilder();
        if (Boolean.TRUE.equals(this.headMatch)) {
            valueStrBuilder.append(EsConstant.WILDCARD_MATCH_TAG);
        }
        valueStrBuilder.append(this.value);
        if (Boolean.TRUE.equals(this.endMatch)) {
            valueStrBuilder.append(EsConstant.WILDCARD_MATCH_TAG);
        }
        return valueStrBuilder.toString();
    }

    private WildCardEsQueryBuilder(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private WildCardEsQueryBuilder() {
        throw new IllegalStateException("Invalid structure");
    }

    /**
     * 条件名
     */
    private String key;

    /**
     * 条件值
     */
    private String value;

    /**
     * 前向匹配（默认不启用）
     */
    private Boolean headMatch = WildCardEsQueryBuilder.DEFAULT_MATCH_FLAG;

    /**
     * 末尾匹配（默认启用）
     */
    private Boolean endMatch = !WildCardEsQueryBuilder.DEFAULT_MATCH_FLAG;

    private static final boolean DEFAULT_MATCH_FLAG = false;
}
