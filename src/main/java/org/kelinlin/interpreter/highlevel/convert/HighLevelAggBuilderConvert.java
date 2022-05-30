package org.kelinlin.interpreter.highlevel.convert;


import org.kelinlin.bulider.sort.BucketEsSortBuilder;
import org.kelinlin.bulider.terms.*;
import org.kelinlin.convert.AggBuilderConvert;
import org.kelinlin.enums.EsExceptionEnums;
import org.kelinlin.enums.Order;
import org.kelinlin.exception.EsException;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.metrics.TopHitsAggregationBuilder;

/**
 * 默认聚合转换器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/25 11:07
 */
public class HighLevelAggBuilderConvert implements AggBuilderConvert<AggregationBuilder> {


    @Override
    public AggregationBuilder buildersConvert(EsAggQueryBuilder esAggQueryBuilder) {
        //平均值聚合
        if (esAggQueryBuilder instanceof AvgAggBuilder) {
            AvgAggBuilder avgAggBuilder = esAggQueryBuilder.getEsAggQueryBuilder();
            return AggregationBuilders.avg(avgAggBuilder.getName()).field(avgAggBuilder.getField());
        } else if (esAggQueryBuilder instanceof CardinalityAggBuilder) {
            //近似与基数的数量聚合
            CardinalityAggBuilder cardinalityAggBuilder = esAggQueryBuilder.getEsAggQueryBuilder();
            return AggregationBuilders.cardinality(cardinalityAggBuilder.getName()).field(cardinalityAggBuilder.getField());
        } else if (esAggQueryBuilder instanceof MaxAggBuilder) {
            //最大值聚合
            MaxAggBuilder maxAggBuilder = esAggQueryBuilder.getEsAggQueryBuilder();
            return AggregationBuilders.max(maxAggBuilder.getName()).field(maxAggBuilder.getField());
        } else if (esAggQueryBuilder instanceof MinAggBuilder) {
            //最小值聚合
            MinAggBuilder minAggBuilder = esAggQueryBuilder.getEsAggQueryBuilder();
            return AggregationBuilders.min(minAggBuilder.getName()).field(minAggBuilder.getField());
        } else if (esAggQueryBuilder instanceof MissingAggBuilder) {
            //缺失聚合
            MissingAggBuilder missingAggBuilder = esAggQueryBuilder.getEsAggQueryBuilder();
            return AggregationBuilders.missing(missingAggBuilder.getName()).field(missingAggBuilder.getField());
        } else if (esAggQueryBuilder instanceof SumAggBuilder) {
            //求和聚合
            SumAggBuilder sumAggBuilder = esAggQueryBuilder.getEsAggQueryBuilder();
            return AggregationBuilders.sum(sumAggBuilder.getName()).field(sumAggBuilder.getField());
        } else if (esAggQueryBuilder instanceof TermsAggBuilder) {
            //分组聚合
            TermsAggBuilder termsAggBuilder = esAggQueryBuilder.getEsAggQueryBuilder();
            BucketEsSortBuilder bucketEsSortBuilder = termsAggBuilder.getBucketEsSortBuilder();
            BucketOrder order;
            if (null != bucketEsSortBuilder) {
                String filed = bucketEsSortBuilder.getField();
                boolean ascFlag = Order.ASC.equals(bucketEsSortBuilder.getOrder());
                if (BucketEsSortBuilder.COUNT_BUCKET_SORT.equals(filed)) {
                    order = BucketOrder.count(ascFlag);
                } else if (BucketEsSortBuilder.KEY_BUCKET_SORT.equals(filed)) {
                    order = BucketOrder.key(ascFlag);
                } else {
                    order = BucketOrder.aggregation(filed, ascFlag);
                }
            } else {
                order = BucketOrder.compound(BucketOrder.count(false));
            }
            return AggregationBuilders.terms(termsAggBuilder.getName()).field(termsAggBuilder.getField())
                    .order(order).size(termsAggBuilder.getBucketSize());
        } else if (esAggQueryBuilder instanceof TopHitsAggBuilder) {
            //返回doc
            TopHitsAggBuilder topHitsAggBuilder = esAggQueryBuilder.getEsAggQueryBuilder();
            TopHitsAggregationBuilder topHitsAggregationBuilder = AggregationBuilders.topHits(topHitsAggBuilder.getName())
                    .from(topHitsAggBuilder.getOffset()).size(topHitsAggBuilder.getLimit());
            topHitsAggBuilder.getSortBuilders().forEach(order -> topHitsAggregationBuilder
                    .sort(HighLevelSortBuilderConvert.getInstance().sortBuilderConvert(order)));
            return topHitsAggregationBuilder;
        } else if (esAggQueryBuilder instanceof ValueCountAggBuilder) {
            //数量聚合
            ValueCountAggBuilder valueCountAggBuilder = esAggQueryBuilder.getEsAggQueryBuilder();
            return AggregationBuilders.count(valueCountAggBuilder.getName()).field(valueCountAggBuilder.getField());
        } else {
            throw new EsException(EsExceptionEnums.ES_CLIENT_NOT_REGISTER);
        }
    }

    @Override
    public AggregationBuilder subAggBuilder(AggregationBuilder score, AggregationBuilder target) {
        return score.subAggregation(target);
    }


    public static synchronized HighLevelAggBuilderConvert getInstance() {
        if (null == highLevelAggBuilderConvert) {
            synchronized (HighLevelAggBuilderConvert.class) {
                if (null == highLevelAggBuilderConvert) {
                    highLevelAggBuilderConvert = new HighLevelAggBuilderConvert();
                }
            }
        }
        return highLevelAggBuilderConvert;
    }

    private HighLevelAggBuilderConvert() {
        super();
    }

    private static volatile HighLevelAggBuilderConvert highLevelAggBuilderConvert;
}
