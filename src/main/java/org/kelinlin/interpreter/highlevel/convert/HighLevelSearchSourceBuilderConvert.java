package org.kelinlin.interpreter.highlevel.convert;

import org.kelinlin.bean.doc.query.EsSearchSourceData;
import org.kelinlin.bean.doc.query.EsTimeData;
import org.kelinlin.bulider.sort.EsSortBuilder;
import org.kelinlin.bulider.terms.*;
import org.kelinlin.convert.SearchSourceBuilderConvert;
import org.kelinlin.enums.EsExceptionEnums;
import org.kelinlin.exception.EsException;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Collection;

/**
 * 默认查询条件RestHighLevelClient解析转换器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/19 10:44
 */
public class HighLevelSearchSourceBuilderConvert implements SearchSourceBuilderConvert<SearchSourceBuilder> {
    @Override
    public SearchSourceBuilder buildersConvert(EsSearchSourceData esSearchSourceData) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //查询条件转化
        //must
        Collection<EsQueryBuilder> mustBuilders = esSearchSourceData.getMustBuilders();
        if (null != mustBuilders && !mustBuilders.isEmpty()) {
            mustBuilders.forEach(esQueryBuilder -> boolQueryBuilder.must(this.queryBuilderConvert(esQueryBuilder)));
        }
        //should
        Collection<EsQueryBuilder> shouldBuilders = esSearchSourceData.getShouldBuilders();
        if (null != shouldBuilders && !shouldBuilders.isEmpty()) {
            shouldBuilders.forEach(esQueryBuilder -> boolQueryBuilder.should(this.queryBuilderConvert(esQueryBuilder)));
        }
        //filter
        Collection<EsQueryBuilder> filterBuilders = esSearchSourceData.getFilterBuilders();
        if (null != filterBuilders && !filterBuilders.isEmpty()) {
            filterBuilders.forEach(esQueryBuilder -> boolQueryBuilder.filter(this.queryBuilderConvert(esQueryBuilder)));
        }
        //must not
        Collection<EsQueryBuilder> mustNotBuilders = esSearchSourceData.getMustNotBuilders();
        if (null != mustNotBuilders && !mustNotBuilders.isEmpty()) {
            mustNotBuilders.forEach(esQueryBuilder -> boolQueryBuilder.mustNot(this.queryBuilderConvert(esQueryBuilder)));
        }

        sourceBuilder.query(boolQueryBuilder);
        //order
        Collection<EsSortBuilder<?>> esSortBuilders = esSearchSourceData.getSortBuilders();
        if (null != esSortBuilders && !esSortBuilders.isEmpty()) {
            //排序转化
            esSortBuilders.forEach(esSortBuilder -> sourceBuilder.sort(HighLevelSortBuilderConvert.getInstance()
                    .sortBuilderConvert(esSortBuilder)));
        }
        //起始位置和条数
        if (null != esSearchSourceData.getOffset()) {
            sourceBuilder.from(esSearchSourceData.getOffset());
        }
        if (null != esSearchSourceData.getLimit()) {
            sourceBuilder.size(esSearchSourceData.getLimit());
        }

        //超时设置
        EsTimeData timeOut = esSearchSourceData.getTimeOut();
        if (null != timeOut) {
            sourceBuilder.timeout(new TimeValue(timeOut.getDuration(), timeOut.getTimeUnit()));
        }
        //聚合设置
        EsAggQueryBuilder esAggQueryBuilder = esSearchSourceData.getEsAggQueryBuilder();
        if (null != esAggQueryBuilder) {
            AbstractEsAggQueryBuilder builder = esAggQueryBuilder.getEsAggQueryBuilder();
            if (null != builder.getScoreAggBuilder()) {
                sourceBuilder.aggregation((AggregationBuilder) builder.getScoreAggBuilder());
            } else {
                sourceBuilder.aggregation(HighLevelAggBuilderConvert.getInstance().buildersConvert(esAggQueryBuilder));
            }
        }
        return sourceBuilder;
    }

    /**
     * EsQueryBuilder 转化
     *
     * @param esQueryBuilder es查询条件构造
     * @return {@link QueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/19 17:46
     */
    private QueryBuilder queryBuilderConvert(EsQueryBuilder esQueryBuilder) {
        //存在
        if (esQueryBuilder instanceof ExistsEsQueryBuilder) {
            ExistsEsQueryBuilder existsEsQueryBuilder = esQueryBuilder.getEsQueryBuilder();
            return QueryBuilders.existsQuery(existsEsQueryBuilder.getKey());
        } else if (esQueryBuilder instanceof MatchEsQueryBuilder) {
            //分词匹配
            MatchEsQueryBuilder matchEsQueryBuilder = esQueryBuilder.getEsQueryBuilder();
            return QueryBuilders.matchQuery(matchEsQueryBuilder.getKey(), matchEsQueryBuilder.getValue());
        } else if (esQueryBuilder instanceof MultiMatchEsQueryBuilder) {
            //分词匹配
            MultiMatchEsQueryBuilder multiMatchEsQueryBuilder = esQueryBuilder.getEsQueryBuilder();
            return QueryBuilders.multiMatchQuery(multiMatchEsQueryBuilder.getValue(), multiMatchEsQueryBuilder.getKeys());
        } else if (esQueryBuilder instanceof RangeEsQueryBuilder) {
            //范围
            RangeEsQueryBuilder rangeEsQueryBuilder = esQueryBuilder.getEsQueryBuilder();
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(rangeEsQueryBuilder.getKey());
            if (null != rangeEsQueryBuilder.getFrom()) {
                rangeQueryBuilder.from(rangeEsQueryBuilder.getFrom(), rangeEsQueryBuilder.getIncludeLower());
            }
            if (null != rangeEsQueryBuilder.getTo()) {
                rangeQueryBuilder.to(rangeEsQueryBuilder.getTo(), rangeEsQueryBuilder.getIncludeUpper());
            }
            return rangeQueryBuilder;
        } else if (esQueryBuilder instanceof TermEsQueryBuilder) {
            //匹配
            TermEsQueryBuilder termEsQueryBuilder = esQueryBuilder.getEsQueryBuilder();
            return QueryBuilders.termQuery(termEsQueryBuilder.getKey(), termEsQueryBuilder.getValue());
        } else if (esQueryBuilder instanceof TermsEsQueryBuilder) {
            //匹配
            TermsEsQueryBuilder termsEsQueryBuilder = esQueryBuilder.getEsQueryBuilder();
            return QueryBuilders.termsQuery(termsEsQueryBuilder.getKey(), termsEsQueryBuilder.getValues());
        } else if (esQueryBuilder instanceof WildCardEsQueryBuilder) {
            //模糊
            WildCardEsQueryBuilder wildCardEsQueryBuilder = esQueryBuilder.getEsQueryBuilder();
            return QueryBuilders.wildcardQuery(wildCardEsQueryBuilder.getKey(), wildCardEsQueryBuilder.getValue());
        } else {
            throw new EsException(EsExceptionEnums.ERROR_QUERY_TYPE);
        }
    }

    public static synchronized HighLevelSearchSourceBuilderConvert getInstance() {
        if (null == highLevelSearchSourceBuilderConvert) {
            synchronized (HighLevelSearchSourceBuilderConvert.class) {
                if (null == highLevelSearchSourceBuilderConvert) {
                    highLevelSearchSourceBuilderConvert = new HighLevelSearchSourceBuilderConvert();
                }
            }
        }
        return highLevelSearchSourceBuilderConvert;
    }

    private HighLevelSearchSourceBuilderConvert() {
        super();
    }

    private static volatile HighLevelSearchSourceBuilderConvert highLevelSearchSourceBuilderConvert;
}
