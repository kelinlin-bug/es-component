package org.kelinlin.interpreter;

import org.kelinlin.bean.response.EsAggResponse;
import org.kelinlin.bean.response.EsResponse;
import org.kelinlin.bulider.soure.EsAggSourceBuilder;
import org.kelinlin.bulider.soure.EsSearchSourceBuilder;

import java.util.Collection;
import java.util.List;

/**
 * es doc query 解释器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 11:32
 */
public interface DocQueryInterpreter {

    /**
     * 查询
     *
     * @param indexName             索引名
     * @param esSearchSourceBuilder es查询条件构造
     * @param tClass                返回数据类型
     * @return {@link EsResponse <T>}
     * @author Chenbin Wang
     * @date 2022/4/20 13:44
     */
    <T> EsResponse<T> query(String indexName, EsSearchSourceBuilder esSearchSourceBuilder, Class<T> tClass);

    /**
     * 批量查询
     *
     * @param indexName              索引名
     * @param esSearchSourceBuilders es查询条件构造集合
     * @param tClass                 返回数据类型
     * @return {@link List<EsResponse<T>>}
     * @author Chenbin Wang
     * @date 2022/5/14 16:08
     */
    <T> List<EsResponse<T>> queryMulti(String indexName, Collection<EsSearchSourceBuilder> esSearchSourceBuilders, Class<T> tClass);

    /**
     * 聚合查询
     *
     * @param indexName          索引名
     * @param esAggSourceBuilder 聚合查询条件
     * @param tClass             doc返回类型
     * @return {@link EsAggResponse}
     * @author Chenbin Wang
     * @date 2022/4/26 16:40
     */
    EsAggResponse aggQuery(String indexName, EsAggSourceBuilder esAggSourceBuilder, Class tClass);
}
