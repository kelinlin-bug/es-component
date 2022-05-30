package org.kelinlin.service.impl;

import com.alibaba.fastjson2.JSON;
import lombok.NonNull;
import org.kelinlin.bean.doc.write.DocWriteData;
import org.kelinlin.bean.doc.write.EsOpData;
import org.kelinlin.bean.response.EsAggResponse;
import org.kelinlin.bean.response.EsResponse;
import org.kelinlin.bulider.soure.EsAggSourceBuilder;
import org.kelinlin.bulider.soure.EsSearchSourceBuilder;
import org.kelinlin.constant.EsConstant;
import org.kelinlin.enums.EsOpType;
import org.kelinlin.enums.EsRefreshPolicy;
import org.kelinlin.interpreter.EsInterpreter;
import org.kelinlin.listener.AsyncActionListener;
import org.kelinlin.listener.BulkAsyncActionListener;
import org.kelinlin.service.EsDocument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * EsDocument doc操作
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 14:50
 */
public class EsDocumentImpl implements EsDocument {

    @Override
    public <T> T singleQuery(String indexName, EsSearchSourceBuilder esSearchSourceBuilder, Class<T> tClass) {
        esSearchSourceBuilder.limit(1);
        EsResponse<T> esResponse = this.query(indexName, esSearchSourceBuilder, tClass);
        List<T> dataList = esResponse.getData();
        if (dataList == null || dataList.isEmpty()) {
            return null;
        }
        return dataList.get(0);
    }

    @Override
    public <T> EsResponse<T> query(String indexName, EsSearchSourceBuilder esSearchSourceBuilder, Class<T> tClass) {
        return esInterpreter.getDocQueryInterpreter().query(indexName.toLowerCase(Locale.ROOT), esSearchSourceBuilder, tClass);
    }

    @Override
    public <T> List<EsResponse<T>> queryMulti(String indexName, Collection<EsSearchSourceBuilder> esSearchSourceBuilders, Class<T> tClass) {
        return esInterpreter.getDocQueryInterpreter().queryMulti(indexName.toLowerCase(Locale.ROOT), esSearchSourceBuilders, tClass);
    }

    @Override
    public EsAggResponse aggQuery(String indexName, EsAggSourceBuilder esAggSourceBuilder, Class tClass) {
        return esInterpreter.getDocQueryInterpreter().aggQuery(indexName, esAggSourceBuilder, tClass);
    }

    @Override
    public EsResponse add(@NonNull String id, @NonNull String indexName, @NonNull Object document) {
        return this.add(id, indexName, document, null, EsConstant.DEFAULT_RETRY_TIMES);
    }

    @Override
    public void addAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, AsyncActionListener asyncActionListener) {
        this.addAsync(id, indexName, document, null, EsConstant.DEFAULT_RETRY_TIMES, asyncActionListener);
    }

    @Override
    public EsResponse add(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes) {
        return this.addBase(id, indexName, document, esRefreshPolicy, EsOpType.CREATE, retryTimes);
    }

    @Override
    public void addAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes, AsyncActionListener asyncActionListener) {
        this.addBaseAsync(id, indexName, document, esRefreshPolicy, EsOpType.CREATE, retryTimes, asyncActionListener);
    }

    @Override
    public EsResponse coverAndAdd(@NonNull String id, @NonNull String indexName, @NonNull Object document) {
        return this.coverAndAdd(id, indexName, document, null, EsConstant.DEFAULT_RETRY_TIMES);
    }

    @Override
    public void coverAndAddAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, AsyncActionListener asyncActionListener) {
        this.coverAndAddAsync(id, indexName, document, null, EsConstant.DEFAULT_RETRY_TIMES, asyncActionListener);
    }

    @Override
    public EsResponse coverAndAdd(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes) {
        return this.addBase(id, indexName, document, esRefreshPolicy, EsOpType.INDEX, retryTimes);
    }

    @Override
    public void coverAndAddAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes, AsyncActionListener asyncActionListener) {
        this.addBaseAsync(id, indexName, document, esRefreshPolicy, EsOpType.INDEX, retryTimes, asyncActionListener);
    }

    @Override
    public EsResponse delete(@NonNull String id, @NonNull String indexName) {
        return this.delete(id, indexName, null, EsConstant.DEFAULT_RETRY_TIMES);
    }

    @Override
    public void deleteAsync(@NonNull String id, @NonNull String indexName, AsyncActionListener asyncActionListener) {
        this.deleteAsync(id, indexName, null, EsConstant.DEFAULT_RETRY_TIMES, asyncActionListener);
    }

    @Override
    public EsResponse delete(@NonNull String id, @NonNull String indexName, EsRefreshPolicy esRefreshPolicy, int retryTimes) {
        EsOpData esOpData = EsOpData.builder().id(id).indexName(indexName.toLowerCase(Locale.ROOT)).esRefreshPolicy(esRefreshPolicy)
                .retry(retryTimes).esOpType(EsOpType.DELETE).build();
        return esInterpreter.getDocWriteInterpreter().esDocSingleRequest(esOpData);
    }

    @Override
    public void deleteAsync(@NonNull String id, @NonNull String indexName, EsRefreshPolicy esRefreshPolicy, int retryTimes, AsyncActionListener asyncActionListener) {
        EsOpData esOpData = EsOpData.builder().id(id).indexName(indexName.toLowerCase(Locale.ROOT)).esRefreshPolicy(esRefreshPolicy)
                .retry(retryTimes).esOpType(EsOpType.DELETE).build();
        this.esInterpreter.getDocWriteInterpreter().esDocSingleRequestAsync(esOpData, asyncActionListener);
    }

    @Override
    public EsResponse update(@NonNull String id, @NonNull String indexName, @NonNull Object document) {
        return this.update(id, indexName, document, null, EsConstant.DEFAULT_RETRY_TIMES);
    }

    @Override
    public void updateAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, AsyncActionListener asyncActionListener) {
        this.updateAsync(id, indexName, document, null, EsConstant.DEFAULT_RETRY_TIMES, asyncActionListener);
    }

    @Override
    public EsResponse update(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes) {
        EsOpData esOpData = EsOpData.builder().id(id).indexName(indexName.toLowerCase(Locale.ROOT)).document(JSON.toJSONString(document))
                .esRefreshPolicy(esRefreshPolicy).retry(retryTimes).esOpType(EsOpType.UPDATE).build();
        return esInterpreter.getDocWriteInterpreter().esDocSingleRequest(esOpData);
    }

    @Override
    public void updateAsync(@NonNull String id, @NonNull String indexName, @NonNull Object document, EsRefreshPolicy esRefreshPolicy, int retryTimes, AsyncActionListener asyncActionListener) {
        EsOpData esOpData = EsOpData.builder().id(id).indexName(indexName.toLowerCase(Locale.ROOT)).document(JSON.toJSONString(document))
                .esRefreshPolicy(esRefreshPolicy).retry(retryTimes).esOpType(EsOpType.UPDATE).build();
        this.esInterpreter.getDocWriteInterpreter().esDocSingleRequestAsync(esOpData, asyncActionListener);
    }

    @Override
    public int addBulk(Collection<DocWriteData> addCollection) {
        return this.addBulk(addCollection, null, EsConstant.DEFAULT_RETRY_TIMES);
    }

    @Override
    public void addBulkAsync(Collection<DocWriteData> addCollection, BulkAsyncActionListener bulkAsyncActionListener) {
        this.addBulkAsync(addCollection, null, EsConstant.DEFAULT_RETRY_TIMES, bulkAsyncActionListener);
    }

    @Override
    public int addBulk(Collection<DocWriteData> addCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes) {
        return this.addBulkBase(addCollection, esRefreshPolicy, EsOpType.CREATE, retryTimes);
    }

    @Override
    public void addBulkAsync(Collection<DocWriteData> addCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes, BulkAsyncActionListener bulkAsyncActionListener) {
        this.addBulkBaseAsync(addCollection, esRefreshPolicy, EsOpType.CREATE, retryTimes, bulkAsyncActionListener);
    }

    @Override
    public int coverAndAddBulk(Collection<DocWriteData> addCollection) {
        return this.coverAndAddBulk(addCollection, null, EsConstant.DEFAULT_RETRY_TIMES);
    }

    @Override
    public void coverAndAddBulkAsync(Collection<DocWriteData> addCollection, BulkAsyncActionListener bulkAsyncActionListener) {
        this.coverAndAddBulkAsync(addCollection, null, EsConstant.DEFAULT_RETRY_TIMES, bulkAsyncActionListener);
    }

    @Override
    public int coverAndAddBulk(Collection<DocWriteData> addCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes) {
        return this.addBulkBase(addCollection, esRefreshPolicy, EsOpType.INDEX, retryTimes);
    }

    @Override
    public void coverAndAddBulkAsync(Collection<DocWriteData> addCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes, BulkAsyncActionListener bulkAsyncActionListener) {
        this.addBulkBaseAsync(addCollection, esRefreshPolicy, EsOpType.INDEX, retryTimes, bulkAsyncActionListener);
    }

    @Override
    public int deleteBulk(@NonNull Collection<DocWriteData> deleteCollection) {
        return this.deleteBulk(deleteCollection, null, EsConstant.DEFAULT_RETRY_TIMES);
    }

    @Override
    public void deleteBulkAsync(@NonNull Collection<DocWriteData> deleteCollection, BulkAsyncActionListener bulkAsyncActionListener) {
        this.deleteBulkAsync(deleteCollection, null, EsConstant.DEFAULT_RETRY_TIMES, bulkAsyncActionListener);
    }

    @Override
    public int deleteBulk(@NonNull Collection<DocWriteData> deleteCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes) {
        Collection<EsOpData> deleteBulkCollection = this.createEsOpDataCollection(deleteCollection, EsOpType.DELETE, retryTimes);
        return esInterpreter.getDocWriteInterpreter().esDocBatchOperation(deleteBulkCollection, esRefreshPolicy);
    }

    @Override
    public void deleteBulkAsync(@NonNull Collection<DocWriteData> deleteCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes, BulkAsyncActionListener bulkAsyncActionListener) {
        Collection<EsOpData> deleteBulkCollection = this.createEsOpDataCollection(deleteCollection, EsOpType.DELETE, retryTimes);
        this.esInterpreter.getDocWriteInterpreter().esDocBatchOperationAsync(deleteBulkCollection, esRefreshPolicy, bulkAsyncActionListener);
    }

    @Override
    public int updateBulk(@NonNull Collection<DocWriteData> updateCollection) {
        return this.updateBulk(updateCollection, null, EsConstant.DEFAULT_RETRY_TIMES);
    }

    @Override
    public void updateBulkAsync(@NonNull Collection<DocWriteData> updateCollection, BulkAsyncActionListener bulkAsyncActionListener) {
        this.updateBulkAsync(updateCollection, null, EsConstant.DEFAULT_RETRY_TIMES, bulkAsyncActionListener);
    }

    @Override
    public int updateBulk(@NonNull Collection<DocWriteData> updateCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes) {
        Collection<EsOpData> updateBulkCollection = this.createEsOpDataCollection(updateCollection, EsOpType.UPDATE, retryTimes);
        return esInterpreter.getDocWriteInterpreter().esDocBatchOperation(updateBulkCollection, esRefreshPolicy);
    }

    @Override
    public void updateBulkAsync(@NonNull Collection<DocWriteData> updateCollection, EsRefreshPolicy esRefreshPolicy, int retryTimes, BulkAsyncActionListener bulkAsyncActionListener) {
        Collection<EsOpData> updateBulkCollection = this.createEsOpDataCollection(updateCollection, EsOpType.UPDATE, retryTimes);
        this.esInterpreter.getDocWriteInterpreter().esDocBatchOperationAsync(updateBulkCollection, esRefreshPolicy, bulkAsyncActionListener);
    }

    /**
     * 生成新增请求
     *
     * @param id              id
     * @param indexName       索引名
     * @param document        文档内容
     * @param esRefreshPolicy es 请求刷新策略
     * @param esOpType        Es操作枚举
     * @param retryTimes      失败重试次数
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 18:25
     */
    private EsResponse addBase(String id, String indexName, Object document, EsRefreshPolicy esRefreshPolicy, EsOpType esOpType, int retryTimes) {
        EsOpData esOpData = EsOpData.builder().id(id).indexName(indexName.toLowerCase(Locale.ROOT)).document(JSON.toJSONString(document))
                .esRefreshPolicy(esRefreshPolicy).retry(retryTimes).esOpType(esOpType).build();
        return esInterpreter.getDocWriteInterpreter().esDocSingleRequest(esOpData);
    }

    /**
     * 生成异步新增请求
     *
     * @param id                  id
     * @param indexName           索引名
     * @param document            文档内容
     * @param esRefreshPolicy     es 请求刷新策略
     * @param esOpType            Es操作枚举
     * @param retryTimes          失败重试次数
     * @param asyncActionListener 异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 18:04
     */
    private void addBaseAsync(String id, String indexName, Object document, EsRefreshPolicy esRefreshPolicy, EsOpType esOpType, int retryTimes, AsyncActionListener asyncActionListener) {
        EsOpData esOpData = EsOpData.builder().id(id).indexName(indexName.toLowerCase(Locale.ROOT)).document(JSON.toJSONString(document))
                .esRefreshPolicy(esRefreshPolicy).retry(retryTimes).esOpType(esOpType).build();
        esInterpreter.getDocWriteInterpreter().esDocSingleRequestAsync(esOpData, asyncActionListener);
    }

    /**
     * 生成新增批量请求（返回成功条数）
     *
     * @param addCollection   添加数据集合（id, 索引，内容）
     * @param esRefreshPolicy es 请求刷新策略
     * @param esOpType        Es操作枚举
     * @param retryTimes      失败重试次数
     * @return {@link int}
     * @author Chenbin Wang
     * @date 2022/4/18 18:26
     */
    private int addBulkBase(@NonNull Collection<DocWriteData> addCollection, EsRefreshPolicy esRefreshPolicy, EsOpType esOpType, int retryTimes) {
        Collection<EsOpData> addBulkCollection = this.createEsOpDataCollection(addCollection, esOpType, retryTimes);
        return esInterpreter.getDocWriteInterpreter().esDocBatchOperation(addBulkCollection, esRefreshPolicy);
    }

    /**
     * 生成异步新增批量请求
     *
     * @param addCollection           添加数据集合（id, 索引，内容）
     * @param esRefreshPolicy         es 请求刷新策略
     * @param esOpType                Es操作枚举
     * @param retryTimes              失败重试次数
     * @param bulkAsyncActionListener 批量操作异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 18:04
     */
    private void addBulkBaseAsync(@NonNull Collection<DocWriteData> addCollection, EsRefreshPolicy esRefreshPolicy, EsOpType esOpType, int retryTimes, BulkAsyncActionListener bulkAsyncActionListener) {
        Collection<EsOpData> addBulkCollection = this.createEsOpDataCollection(addCollection, esOpType, retryTimes);
        this.esInterpreter.getDocWriteInterpreter().esDocBatchOperationAsync(addBulkCollection, esRefreshPolicy, bulkAsyncActionListener);
    }

    /**
     * 生成EsOpData集合
     *
     * @param docWriteCollection 数据集合（id, 索引，内容）
     * @param esOpType           Es操作枚举
     * @param retryTimes         失败重试次数
     * @return {@link Collection<EsOpData>}
     * @author Chenbin Wang
     * @date 2022/4/28 18:18
     */
    private Collection<EsOpData> createEsOpDataCollection(Collection<DocWriteData> docWriteCollection, EsOpType esOpType, int retryTimes) {
        Collection<EsOpData> esOpDataCollection = new ArrayList<>(docWriteCollection.size());
        docWriteCollection.forEach(docWriteData -> {
            EsOpData esOpData = EsOpData.builder().id(docWriteData.getId()).indexName(docWriteData.getIndexName().toLowerCase(Locale.ROOT))
                    .document(null == docWriteData.getDocument() ? null : JSON.toJSONString(docWriteData.getDocument()))
                    .retry(retryTimes).esOpType(esOpType).build();
            esOpDataCollection.add(esOpData);
        });
        return esOpDataCollection;
    }

    public static synchronized EsDocument getInstance(EsInterpreter esInterpreter) {
        if (null == esDocument) {
            synchronized (EsDocument.class) {
                if (null == esDocument) {
                    esDocument = new EsDocumentImpl(esInterpreter);
                }
            }
        }
        return esDocument;
    }

    private EsDocumentImpl() {
        throw new IllegalStateException("Invalid structure");
    }

    private EsDocumentImpl(EsInterpreter esInterpreter) {
        this.esInterpreter = esInterpreter;
    }

    private static volatile EsDocument esDocument;

    private EsInterpreter esInterpreter;
}
