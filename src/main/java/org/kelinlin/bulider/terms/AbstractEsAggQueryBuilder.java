package org.kelinlin.bulider.terms;

import lombok.Getter;
import lombok.ToString;
import org.kelinlin.convert.AggBuilderConvert;
import org.kelinlin.interpreter.highlevel.convert.HighLevelAggBuilderConvert;

/**
 * es聚合查询条件构造基类
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 14:46
 */
@ToString
@Getter
public abstract class AbstractEsAggQueryBuilder implements EsAggQueryBuilder {

    @Override
    public <T extends EsAggQueryBuilder> T getEsAggQueryBuilder() {
        return (T) this;
    }

    @Override
    public <T extends EsAggQueryBuilder> T subAggregation(EsAggQueryBuilder esAggQueryBuilder, AggBuilderConvert aggBuilderConvert) {
        if (null == this.scoreAggBuilder) {
            this.scoreAggBuilder = aggBuilderConvert.buildersConvert(this);
        }
        AbstractEsAggQueryBuilder abstractEsAggQueryBuilder = esAggQueryBuilder.getEsAggQueryBuilder();
        Object target;
        if (null == abstractEsAggQueryBuilder.scoreAggBuilder) {
            target = aggBuilderConvert.buildersConvert(esAggQueryBuilder);
        } else {
            target = abstractEsAggQueryBuilder.scoreAggBuilder;
        }
        this.scoreAggBuilder = aggBuilderConvert.subAggBuilder(this.scoreAggBuilder, target);
        return this.getEsAggQueryBuilder();
    }

    @Override
    public <T extends EsAggQueryBuilder> T subAggregation(EsAggQueryBuilder esAggQueryBuilder) {
        return this.subAggregation(esAggQueryBuilder, AbstractEsAggQueryBuilder.defaultAggBuilderConvert);
    }

    protected AbstractEsAggQueryBuilder(String name) {
        this.name = name;
    }


    /**
     * 设置命中字段
     *
     * @param field 分组字段
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/24 11:50
     */
    public <T extends EsAggQueryBuilder> T field(String field) {
        this.field = field;
        return (T) this;
    }

    /**
     * 默认聚合转换器
     */
    private static final AggBuilderConvert defaultAggBuilderConvert = HighLevelAggBuilderConvert.getInstance();

    /**
     * 转换后的聚合条件
     */
    private Object scoreAggBuilder;


    /**
     * 聚合桶名
     */
    private String name;

    /**
     * 聚合命中字段
     */
    private String field;
}
