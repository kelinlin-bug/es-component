package org.kelinlin.bulider.soure;

import lombok.ToString;
import org.kelinlin.bean.doc.query.EsSearchSourceData;
import org.kelinlin.bulider.terms.EsAggQueryBuilder;
import org.kelinlin.convert.SearchSourceBuilderConvert;

/**
 * es 聚合构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/22 13:56
 */
@ToString
public class EsAggSourceBuilder extends AbstractEsSourceBuilder<EsAggSourceBuilder> {

    /**
     * 添加聚合条件
     *
     * @param esAggQueryBuilder 聚合条件
     * @return {@link EsAggSourceBuilder}
     * @author Chenbin Wang
     * @date 2022/4/25 10:31
     */
    public EsAggSourceBuilder agg(EsAggQueryBuilder esAggQueryBuilder) {
        this.esAggQueryBuilder = esAggQueryBuilder;
        return this;
    }


    public static EsAggSourceBuilder create() {
        return new EsAggSourceBuilder();
    }

    /**
     * 查询条件解析转换
     *
     * @param builderConvert
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/19 10:42
     */
    public <T> T queryBuildersConvert(SearchSourceBuilderConvert<T> builderConvert) {
        return builderConvert.buildersConvert(this.toSearchSourceData());
    }

    private EsAggSourceBuilder() {
        super();
    }

    @Override
    EsSearchSourceData toSearchSourceData() {
        return EsSearchSourceData.builder().mustBuilders(this.getMustBuilders()).shouldBuilders(this.getShouldBuilders())
                .filterBuilders(this.getFilterBuilders()).mustNotBuilders(this.getMustNotBuilders())
                .esAggQueryBuilder(this.esAggQueryBuilder).timeOut(this.getTimeOut()).build();
    }

    /**
     * 聚合
     */
    private EsAggQueryBuilder esAggQueryBuilder;
}
