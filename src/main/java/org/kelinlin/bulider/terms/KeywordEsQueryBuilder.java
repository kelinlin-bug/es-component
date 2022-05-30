package org.kelinlin.bulider.terms;

import org.kelinlin.constant.EsConstant;

/**
 * 包含 keyword 类型 es聚合查询构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 16:32
 */
public class KeywordEsQueryBuilder extends AbstractEsAggQueryBuilder {

    /**
     * 设置命中字段(keyword 类型)
     *
     * @param field 分组字段
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/24 11:50
     */
    public <T extends EsAggQueryBuilder> T keywordField(String field) {
        this.field(field + EsConstant.TYPE_KEYWORD);
        return (T) this;
    }

    public KeywordEsQueryBuilder(String name) {
        super(name);
    }
}
