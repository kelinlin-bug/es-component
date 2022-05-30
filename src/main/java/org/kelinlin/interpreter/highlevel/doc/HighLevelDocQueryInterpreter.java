package org.kelinlin.interpreter.highlevel.doc;


import com.alibaba.fastjson2.JSON;
import org.kelinlin.bean.response.EsAggResponse;
import org.kelinlin.bean.response.EsResponse;
import org.kelinlin.bulider.soure.EsAggSourceBuilder;
import org.kelinlin.bulider.soure.EsSearchSourceBuilder;
import org.kelinlin.convert.SearchSourceBuilderConvert;
import org.kelinlin.enums.EsExceptionEnums;
import org.kelinlin.enums.SearchType;
import org.kelinlin.exception.EsException;
import org.kelinlin.interpreter.DocQueryInterpreter;
import org.kelinlin.interpreter.highlevel.client.HighLevelClient;
import org.kelinlin.interpreter.highlevel.convert.HighLevelSearchSourceBuilderConvert;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.Min;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.aggregations.metrics.ValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * es doc RestHighLevelClient query 解释器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 13:46
 */
public class HighLevelDocQueryInterpreter implements DocQueryInterpreter {

    @Override
    public <T> EsResponse<T> query(String indexName, EsSearchSourceBuilder esSearchSourceBuilder, Class<T> tClass) {
        SearchSourceBuilder sourceBuilder = esSearchSourceBuilder.queryBuildersConvert(highLevelSearchSourceBuilderConvert);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(indexName);
        searchRequest.source(sourceBuilder);
        String preference = esSearchSourceBuilder.getPreference();
        if(null != preference && !"".equals(preference)) {
            searchRequest.preference(preference);
        }
        SearchType searchType = esSearchSourceBuilder.getSearchType();
        if (null != searchType) {
            searchRequest.searchType(org.elasticsearch.action.search.SearchType.fromId(searchType.getId()));
        }
        try {
            SearchResponse searchResponse = this.highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            EsResponse<T> esResponse = this.createResponse(indexName, searchResponse, tClass);
            if (esResponse.getStatus() == RestStatus.OK.getStatus()) {
                return esResponse;
            }
            throw new EsException(EsExceptionEnums.ERROR_ES_CODE.getCode(), EsExceptionEnums.ERROR_ES_CODE.getMsg() + searchResponse.status());
        } catch (IOException e) {
            throw new EsException(EsExceptionEnums.ES_FAIL, e);
        }
    }

    public <T> List<EsResponse<T>> queryMulti(String indexName, Collection<EsSearchSourceBuilder> esSearchSourceBuilders, Class<T> tClass) {
        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();
        esSearchSourceBuilders.forEach(esSearchSourceBuilder -> {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(indexName);
            SearchSourceBuilder sourceBuilder = esSearchSourceBuilder.queryBuildersConvert(highLevelSearchSourceBuilderConvert);
            searchRequest.source(sourceBuilder);
            String preference = esSearchSourceBuilder.getPreference();
            if(null != preference && !"".equals(preference)) {
                searchRequest.preference(preference);
            }
            SearchType searchType = esSearchSourceBuilder.getSearchType();
            if (null != searchType) {
                searchRequest.searchType(org.elasticsearch.action.search.SearchType.fromId(searchType.getId()));
            }
            multiSearchRequest.add(searchRequest);
        });
        List<EsResponse<T>> esResponses = new ArrayList<>(esSearchSourceBuilders.size());
        try {
            MultiSearchResponse multiSearchResponse = this.highLevelClient.msearch(multiSearchRequest, RequestOptions.DEFAULT);
            MultiSearchResponse.Item[] items = multiSearchResponse.getResponses();
            for (MultiSearchResponse.Item item : items) {
                if (item.isFailure()) {
                    continue;
                }
                esResponses.add(this.createResponse(indexName, item.getResponse(), tClass));
            }
            return esResponses;
        } catch (IOException e) {
            throw new EsException(EsExceptionEnums.ES_FAIL, e);
        }
    }

    /**
     * searchResponse -> EsResponse<T>
     *
     * @param indexName
     * @param searchResponse
     * @param tClass
     * @return {@link EsResponse<T>}
     * @throws
     * @author Chenbin Wang
     * @date 2022/5/14 16:03
     */
    private <T> EsResponse<T> createResponse(String indexName, SearchResponse searchResponse, Class<T> tClass) {
        EsResponse<T> esResponse = new EsResponse<>();
        esResponse.setIndexName(indexName);
        esResponse.setId("");
        esResponse.setStatus(searchResponse.status().getStatus());
        if (searchResponse.status() == RestStatus.OK) {
            List<T> dataList;
            SearchHits searchHits = searchResponse.getHits();
            esResponse.setTotal(searchHits.getTotalHits().value);
            if (searchHits.getTotalHits().value < 1) {
                dataList = Collections.emptyList();
            } else {
                dataList = Arrays.stream(searchHits.getHits())
                        .map(searchHit -> JSON.parseObject(searchHit.getSourceAsString(), tClass)).toList();
            }
            esResponse.setData(dataList);
        }
        return esResponse;
    }

    @Override
    public EsAggResponse aggQuery(String indexName, EsAggSourceBuilder esAggSourceBuilder, Class tClass) {
        SearchSourceBuilder sourceBuilder = esAggSourceBuilder.queryBuildersConvert(highLevelSearchSourceBuilderConvert);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(indexName);
        searchRequest.source(sourceBuilder);
        String preference = esAggSourceBuilder.getPreference();
        if(null != preference && !"".equals(preference)) {
            searchRequest.preference(preference);
        }
        SearchType searchType = esAggSourceBuilder.getSearchType();
        if (null != searchType) {
            searchRequest.searchType(org.elasticsearch.action.search.SearchType.fromId(searchType.getId()));
        }
        EsAggResponse esAggResponse = new EsAggResponse();
        esAggResponse.setIndexName(indexName);
        try {
            SearchResponse searchResponse = this.highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            esAggResponse.setStatus(searchResponse.status().getStatus());
            if (searchResponse.status() == RestStatus.OK) {
                Map<String, Aggregation> aggResponseMap = searchResponse.getAggregations().asMap();
                Map<String, Object> aggMap = this.aggregationsConvert(aggResponseMap, tClass);
                esAggResponse.setAggMap(aggMap);
                return esAggResponse;
            }
            throw new EsException(EsExceptionEnums.ERROR_ES_CODE.getCode(), EsExceptionEnums.ERROR_ES_CODE.getMsg() + searchResponse.status());
        } catch (IOException e) {
            throw new EsException(EsExceptionEnums.ES_FAIL, e);
        }
    }

    /**
     * 聚合内容返回递归解析
     *
     * @param aggregationMap 聚合内容
     * @param tClass         doc返回类型
     * @return {@link Map<String,Object>}
     * @author Chenbin Wang
     * @date 2022/4/26 16:39
     */
    private Map<String, Object> aggregationsConvert(Map<String, Aggregation> aggregationMap, Class tClass) {
        Map<String, Object> map = new HashMap<>(16);
        for (Map.Entry<String, Aggregation> entry : aggregationMap.entrySet()) {
            Aggregation aggregation = entry.getValue();
            if (aggregation instanceof Terms terms) {
                //分组
                Map<String, Object> bucketMap = new HashMap<>(16);
                for (Terms.Bucket bucket : terms.getBuckets()) {
                    Map<String, Aggregation> bucketAggMap = bucket.getAggregations().asMap();
                    //递归解析
                    Map<String, Object> termMap = this.aggregationsConvert(bucketAggMap, tClass);
                    bucketMap.put(bucket.getKeyAsString(), termMap);
                }
                map.put(entry.getKey(), bucketMap);
            } else if (aggregation instanceof Avg avg) {
                //平均值
                map.put(entry.getKey(), avg.getValue());
            } else if (aggregation instanceof Cardinality cardinality) {
                //近似与基数的数量
                map.put(entry.getKey(), cardinality.getValue());
            } else if (aggregation instanceof Max max) {
                //最大值
                map.put(entry.getKey(), max.getValue());
            } else if (aggregation instanceof Min min) {
                //最小值
                map.put(entry.getKey(), min.getValue());
            } else if (aggregation instanceof Missing missing) {
                //缺失
                map.put(entry.getKey(), missing.getDocCount());
            } else if (aggregation instanceof Sum sum) {
                //求和
                map.put(entry.getKey(), sum.getValue());
            } else if (aggregation instanceof ValueCount valueCount) {
                //数量
                map.put(entry.getKey(), valueCount.getValue());
            } else if (aggregation instanceof TopHits topHits) {
                //doc数据
                SearchHits searchHits = topHits.getHits();
                List<Object> dataList;
                if (searchHits.getTotalHits().value < 1) {
                    dataList = Collections.emptyList();
                } else {
                    if (null == tClass) {
                        dataList = Arrays.stream(searchHits.getHits())
                                .map(SearchHit::getSourceAsString).collect(Collectors.toList());
                    } else {
                        dataList = new ArrayList<>(searchHits.getHits().length);
                        for (SearchHit hit : searchHits.getHits()) {
                            dataList.add(JSON.parseObject(hit.getSourceAsString(), tClass));
                        }
                    }
                }
                map.put(entry.getKey(), dataList);
            }
        }
        return map;
    }

    public static synchronized HighLevelDocQueryInterpreter getInstance() {
        if (null == highLevelDocQueryInterpreter) {
            synchronized (HighLevelDocQueryInterpreter.class) {
                if (null == highLevelDocQueryInterpreter) {
                    highLevelDocQueryInterpreter = new HighLevelDocQueryInterpreter();
                }
            }
        }
        return highLevelDocQueryInterpreter;
    }

    private HighLevelDocQueryInterpreter() {
        this.highLevelSearchSourceBuilderConvert = HighLevelSearchSourceBuilderConvert.getInstance();
        this.highLevelClient = HighLevelClient.getInstance();
    }

    private static volatile HighLevelDocQueryInterpreter highLevelDocQueryInterpreter;

    private SearchSourceBuilderConvert<SearchSourceBuilder> highLevelSearchSourceBuilderConvert;

    private RestHighLevelClient highLevelClient;
}
