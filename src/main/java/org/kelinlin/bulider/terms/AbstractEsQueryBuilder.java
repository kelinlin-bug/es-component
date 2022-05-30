package org.kelinlin.bulider.terms;

import lombok.ToString;

/**
 * es查询条件构造基类
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 14:46
 */
@ToString
public abstract class AbstractEsQueryBuilder implements EsQueryBuilder {

    @Override
    public <T extends EsQueryBuilder> T getEsQueryBuilder() {
        return (T) this;
    }
}
