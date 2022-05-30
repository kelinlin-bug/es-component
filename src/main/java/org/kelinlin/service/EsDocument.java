package org.kelinlin.service;

import lombok.NonNull;
import org.kelinlin.bean.doc.write.DocWriteData;
import org.kelinlin.bean.response.EsAggResponse;
import org.kelinlin.bean.response.EsResponse;
import org.kelinlin.bulider.soure.EsAggSourceBuilder;
import org.kelinlin.bulider.soure.EsSearchSourceBuilder;
import org.kelinlin.enums.EsRefreshPolicy;
import org.kelinlin.listener.AsyncActionListener;
import org.kelinlin.listener.BulkAsyncActionListener;

import java.util.Collection;
import java.util.List;

/**
 * EsDocument doc操作
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 14:50
 */
public interface EsDocument {

    /**
     * 查询单条数据，若存在多条返回第一条
     *
     * @param indexName             索引名
     * @param esSearchSourceBuilder es查询条件构造
     * @param tClass                返回数据类型
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/20 11:00
     */
    <T> T singleQuery(String indexName, EsSearchSourceBuilder esSearchSourceBuilder, Class<T> tClass);

    /**
     * 查询
     *
     * @param indexName             索引名
     * @param esSearchSourceBuilder es查询条件构造
     * @param tClass                返回数据类型
     * @return {@link EsResponse<T>}
     * @author Chenbin Wang
     * @date 2022/4/20 11:15
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
     * @date 2022/5/14 16:10
     */
    <T> List<EsResponse<T>> queryMulti(String indexName, Collection<EsSearchSourceBuilder> esSearchSourceBuilders, Class<T> tClass);

    /**
     * 聚合查询
     *
     * @param indexName          索引名
     * @param esAggSourceBuilder 聚合条件
     * @param tClass             doc数据返回类型，如果没有topHit聚合条件可为空
     * @return {@link EsAggResponse}
     * @author Chenbin Wang
     * @date 2022/4/26 16:35
     */
    EsAggResponse aggQuery(String indexName, EsAggSourceBuilder esAggSourceBuilder, Class tClass);

    /**
     * 添加，不覆盖id相同项
     * 默认刷新策略，有延迟，不重试
     *
     * @param id        id
     * @param indexName 索引名
     * @param document  文档内容
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 11:07
     */
    EsResponse add(@NonNull String id, @NonNull String indexName, @NonNull Object document);

    /**
     * 异步添加，不覆盖id相同项
     * 默认刷新策略，有延迟，不重试
     *
     * @param id                  id
     * @param indexName           索引名
     * @param document            文档内容
     * @param asyncActionListener 异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:35
     */
    void addAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, AsyncActionListener asyncActionListener);

    /**
     * 添加，不覆盖id相同项
     *
     * @param id              id
     * @param indexName       索引名
     * @param document        文档内容
     * @param esRefreshPolicy 请求刷新策略
     * @param retryTimes      失败重试次数
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 11:08
     */
    EsResponse add(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes);

    /**
     * 异步添加，不覆盖id相同项
     *
     * @param id                  id
     * @param indexName           索引名
     * @param document            文档内容
     * @param esRefreshPolicy     请求刷新策略
     * @param retryTimes          失败重试次数
     * @param asyncActionListener 异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:37
     */
    void addAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes, AsyncActionListener asyncActionListener);

    /**
     * 添加，覆盖id相同项
     * 默认刷新策略，有延迟，不重试
     *
     * @param id        id
     * @param indexName 索引名
     * @param document  文档内容
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 11:15
     */
    EsResponse coverAndAdd(@NonNull String id, @NonNull String indexName, @NonNull Object document);

    /**
     * 异步添加，覆盖id相同项
     * 默认刷新策略，有延迟，不重试
     *
     * @param id                  id
     * @param indexName           索引名
     * @param document            文档内容
     * @param asyncActionListener 异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:38
     */
    void coverAndAddAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, AsyncActionListener asyncActionListener);

    /**
     * 添加，覆盖id相同项
     *
     * @param id              id
     * @param indexName       索引名
     * @param document        文档内容
     * @param esRefreshPolicy 请求刷新策略
     * @param retryTimes      失败重试次数
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 11:15
     */
    EsResponse coverAndAdd(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes);

    /**
     * 异步添加，覆盖id相同项
     *
     * @param id                  id
     * @param indexName           索引名
     * @param document            文档内容
     * @param esRefreshPolicy     请求刷新策略
     * @param retryTimes          失败重试次数
     * @param asyncActionListener 异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:39
     */
    void coverAndAddAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes, AsyncActionListener asyncActionListener);

    /**
     * 删除
     * 默认刷新策略，有延迟，不重试
     *
     * @param id        id
     * @param indexName 索引名
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 11:20
     */
    EsResponse delete(@NonNull String id, @NonNull String indexName);

    /**
     * 异步删除
     * 默认刷新策略，有延迟，不重试
     *
     * @param id                  id
     * @param indexName           索引名
     * @param asyncActionListener 异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:41
     */
    void deleteAsync(@NonNull String id, @NonNull String indexName, AsyncActionListener asyncActionListener);

    /**
     * 删除
     *
     * @param id              id
     * @param indexName       索引名
     * @param esRefreshPolicy 请求刷新策略
     * @param retryTimes      失败重试次数
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 11:20
     */
    EsResponse delete(@NonNull String id, @NonNull String indexName, EsRefreshPolicy esRefreshPolicy, int retryTimes);

    /**
     * 异步删除
     *
     * @param id                  id
     * @param indexName           索引名
     * @param esRefreshPolicy     请求刷新策略
     * @param retryTimes          失败重试次数
     * @param asyncActionListener 异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:42
     */
    void deleteAsync(@NonNull String id, @NonNull String indexName, EsRefreshPolicy esRefreshPolicy, int retryTimes, AsyncActionListener asyncActionListener);

    /**
     * 更新
     * 默认刷新策略，有延迟，不重试
     *
     * @param id        id
     * @param indexName 索引名
     * @param document  文档内容
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 11:15
     */
    EsResponse update(@NonNull String id, @NonNull String indexName, @NonNull Object document);

    /**
     * 异步更新
     * 默认刷新策略，有延迟，不重试
     *
     * @param id                  id
     * @param indexName           索引名
     * @param document            文档内容
     * @param asyncActionListener 异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:43
     */
    void updateAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, AsyncActionListener asyncActionListener);

    /**
     * 更新
     *
     * @param id              id
     * @param indexName       索引名
     * @param document        文档内容
     * @param esRefreshPolicy 请求刷新策略
     * @param retryTimes      失败重试次数
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 11:15
     */
    EsResponse update(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes);

    /**
     * 异步更新
     *
     * @param id                  id
     * @param indexName           索引名
     * @param document            文档内容
     * @param esRefreshPolicy     请求刷新策略
     * @param retryTimes          失败重试次数
     * @param asyncActionListener 异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:44
     */
    void updateAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes, AsyncActionListener asyncActionListener);

    /**
     * 批量添加，不覆盖id相同项（返回成功条数）
     * 默认刷新策略，有延迟，不重试
     *
     * @param addCollection 添加数据集合（id, 索引，内容）
     * @return {@link int}
     * @author Chenbin Wang
     * @date 2022/4/18 10:45
     */
    int addBulk(Collection<DocWriteData> addCollection);

    /**
     * 异步批量添加，不覆盖id相同项
     * 默认刷新策略，有延迟，不重试
     *
     * @param addCollection           添加数据集合（id, 索引，内容）
     * @param bulkAsyncActionListener 批量操作异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:45
     */
    void addBulkAsync(Collection<DocWriteData> addCollection, BulkAsyncActionListener bulkAsyncActionListener);

    /**
     * 批量添加，不覆盖id相同项（返回成功条数）
     *
     * @param addCollection   添加数据集合（id, 索引，内容）
     * @param esRefreshPolicy 请求刷新策略
     * @param retryTimes      失败重试次数
     * @return {@link int}
     * @author Chenbin Wang
     * @date 2022/4/18 10:45
     */
    int addBulk(Collection<DocWriteData> addCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes);

    /**
     * 异步批量添加，不覆盖id相同项
     *
     * @param addCollection           添加数据集合（id, 索引，内容）
     * @param esRefreshPolicy         请求刷新策略
     * @param retryTimes              失败重试次数
     * @param bulkAsyncActionListener 批量操作异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:46
     */
    void addBulkAsync(Collection<DocWriteData> addCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes, BulkAsyncActionListener bulkAsyncActionListener);

    /**
     * 批量添加，覆盖id相同项（返回成功条数）
     * 默认刷新策略，有延迟，不重试
     *
     * @param addCollection 添加数据集合（id, 索引，内容）
     * @return {@link int}
     * @author Chenbin Wang
     * @date 2022/4/18 10:45
     */
    int coverAndAddBulk(Collection<DocWriteData> addCollection);

    /**
     * 异步批量添加，不覆盖id相同项
     *
     * @param addCollection           添加数据集合（id, 索引，内容）
     * @param bulkAsyncActionListener 批量操作异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:48
     */
    void coverAndAddBulkAsync(Collection<DocWriteData> addCollection, BulkAsyncActionListener bulkAsyncActionListener);

    /**
     * 批量添加，覆盖id相同项（返回成功条数）
     *
     * @param addCollection   添加数据集合（id, 索引，内容）
     * @param esRefreshPolicy 请求刷新策略
     * @param retryTimes      失败重试次数
     * @return {@link int}
     * @author Chenbin Wang
     * @date 2022/4/18 10:45
     */
    int coverAndAddBulk(Collection<DocWriteData> addCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes);

    /**
     * 异步批量添加，不覆盖id相同项
     *
     * @param addCollection           添加数据集合（id, 索引，内容）
     * @param esRefreshPolicy         请求刷新策略
     * @param retryTimes              失败重试次数
     * @param bulkAsyncActionListener 批量操作异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:50
     */
    void coverAndAddBulkAsync(Collection<DocWriteData> addCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes, BulkAsyncActionListener bulkAsyncActionListener);

    /**
     * 批量删除（返回成功条数）
     * 默认刷新策略，有延迟，不重试
     *
     * @param deleteCollection 删除数据集合（id, 索引）
     * @return {@link int}
     * @author Chenbin Wang
     * @date 2022/4/18 10:32
     */
    int deleteBulk(@NonNull Collection<DocWriteData> deleteCollection);

    /**
     * 异步批量删除
     * 默认刷新策略，有延迟，不重试
     *
     * @param deleteCollection        删除数据集合（id, 索引）
     * @param bulkAsyncActionListener 批量操作异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:51
     */
    void deleteBulkAsync(@NonNull Collection<DocWriteData> deleteCollection, BulkAsyncActionListener bulkAsyncActionListener);

    /**
     * 批量删除（返回成功条数）
     *
     * @param deleteCollection 删除数据集合（id, 索引）
     * @param esRefreshPolicy  请求刷新策略
     * @param retryTimes       失败重试次数
     * @return {@link int}
     * @author Chenbin Wang
     * @date 2022/4/18 10:36
     */
    int deleteBulk(@NonNull Collection<DocWriteData> deleteCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes);

    /**
     * 异步批量删除
     *
     * @param deleteCollection        删除数据集合（id, 索引）
     * @param esRefreshPolicy         请求刷新策略
     * @param retryTimes              失败重试次数
     * @param bulkAsyncActionListener 批量操作异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:53
     */
    void deleteBulkAsync(@NonNull Collection<DocWriteData> deleteCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes, BulkAsyncActionListener bulkAsyncActionListener);

    /**
     * 批量更新(返回成功条数)
     * 默认刷新策略，有延迟，不重试
     *
     * @param updateCollection 更新数据集合（id, 索引，内容）
     * @return {@link int}
     * @author Chenbin Wang
     * @date 2022/4/18 10:23
     */
    int updateBulk(@NonNull Collection<DocWriteData> updateCollection);

    /**
     * 异步批量更新
     * 默认刷新策略，有延迟，不重试
     *
     * @param updateCollection        更新数据集合（id, 索引，内容）
     * @param bulkAsyncActionListener 批量操作异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:54
     */
    void updateBulkAsync(@NonNull Collection<DocWriteData> updateCollection, BulkAsyncActionListener bulkAsyncActionListener);

    /**
     * 批量更新（返回成功条数）
     *
     * @param updateCollection 更新数据集合（id, 索引，内容）
     * @param esRefreshPolicy  请求刷新策略
     * @param retryTimes       失败重试次数
     * @return {@link int}
     * @author Chenbin Wang
     * @date 2022/4/18 10:19
     */
    int updateBulk(@NonNull Collection<DocWriteData> updateCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes);

    /**
     * 异步批量更新
     *
     * @param updateCollection        更新数据集合（id, 索引，内容）
     * @param esRefreshPolicy         请求刷新策略
     * @param retryTimes              失败重试次数
     * @param bulkAsyncActionListener 批量操作异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:55
     */
    void updateBulkAsync(@NonNull Collection<DocWriteData> updateCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes, BulkAsyncActionListener bulkAsyncActionListener);
}
