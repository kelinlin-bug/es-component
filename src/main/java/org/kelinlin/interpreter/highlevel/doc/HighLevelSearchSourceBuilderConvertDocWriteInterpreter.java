package org.kelinlin.interpreter.highlevel.doc;

import lombok.NonNull;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.kelinlin.bean.doc.write.EsOpData;
import org.kelinlin.bean.response.EsResponse;
import org.kelinlin.enums.EsExceptionEnums;
import org.kelinlin.enums.EsRefreshPolicy;
import org.kelinlin.exception.EsException;
import org.kelinlin.interpreter.DocWriteInterpreter;
import org.kelinlin.interpreter.highlevel.client.HighLevelClient;
import org.kelinlin.listener.AsyncActionListener;
import org.kelinlin.listener.BulkAsyncActionListener;

import java.io.IOException;
import java.util.Collection;

/**
 * es doc RestHighLevelClient write 解释器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 18:12
 */
public class HighLevelSearchSourceBuilderConvertDocWriteInterpreter implements DocWriteInterpreter {

    /**
     * es 单次请求
     *
     * @param esOpData es 操作数据
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 18:16
     */
    @Override
    public EsResponse esDocSingleRequest(EsOpData esOpData) {
        DocWriteResponse docWriteResponse;
        try {
            switch (esOpData.getEsOpType()) {
                case UPDATE:
                    docWriteResponse = this.highLevelClient.update(esSingleOperationUpdate(esOpData, false), RequestOptions.DEFAULT);
                    break;
                case INDEX, CREATE:
                    docWriteResponse = this.highLevelClient.index(esSingleOperationAdd(esOpData, false), RequestOptions.DEFAULT);
                    break;
                case DELETE:
                    docWriteResponse = this.highLevelClient.delete(esSingleOperationDelete(esOpData, false), RequestOptions.DEFAULT);
                    break;
                default:
                    throw new EsException(EsExceptionEnums.ERROR_OP_TYPE);
            }
            return HighLevelSearchSourceBuilderConvertDocWriteInterpreter.createEsResponse(docWriteResponse);
        } catch (IOException e) {
            throw new EsException(EsExceptionEnums.ES_FAIL, e);
        }
    }

    @Override
    public void esDocSingleRequestAsync(EsOpData esOpData, AsyncActionListener asyncActionListener) {
        //创建监听器
        ActionListener actionListener = new ActionListener<DocWriteResponse>() {
            @Override
            public void onResponse(DocWriteResponse docWriteResponse) {
                EsResponse esResponse = HighLevelSearchSourceBuilderConvertDocWriteInterpreter.createEsResponse(docWriteResponse);
                asyncActionListener.onResponse(esResponse);
            }

            @Override
            public void onFailure(Exception e) {
                EsException esException = new EsException(EsExceptionEnums.ES_FAIL, e);
                asyncActionListener.onFailure(esException);
            }
        };
        switch (esOpData.getEsOpType()) {
            case UPDATE:
                this.highLevelClient.updateAsync(esSingleOperationUpdate(esOpData, false), RequestOptions.DEFAULT, actionListener);
                break;
            case INDEX, CREATE:
                this.highLevelClient.indexAsync(esSingleOperationAdd(esOpData, false), RequestOptions.DEFAULT, actionListener);
                break;
            case DELETE:
                this.highLevelClient.deleteAsync(esSingleOperationDelete(esOpData, false), RequestOptions.DEFAULT, actionListener);
                break;
            default:
                throw new EsException(EsExceptionEnums.ERROR_OP_TYPE);
        }
    }

    /**
     * 批量操作（返回成功条数）
     *
     * @param esOpDataCollection es 操作数据集合
     * @param esRefreshPolicy    es 请求刷新策略
     * @return {@link int}
     * @author Chenbin Wang
     * @date 2022/4/18 9:55
     */
    @Override
    public int esDocBatchOperation(@NonNull Collection<EsOpData> esOpDataCollection, EsRefreshPolicy esRefreshPolicy) {
        if (esOpDataCollection.isEmpty()) {
            return 0;
        }
        BulkRequest bulkRequest = new BulkRequest();
        //生成批量操作
        esOpDataCollection.forEach(esOpData -> bulkRequest.add(esSingleOperation(esOpData, true)));
        //设置批量刷新策略
        if (esRefreshPolicy != null) {
            bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.parse(esRefreshPolicy.getValue()));
        }
        int failedCount = 0;
        try {
            BulkResponse bulkResponse = this.highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            for (BulkItemResponse response : bulkResponse) {
                if (response.isFailed()) {
                    failedCount++;
                }
            }
            return esOpDataCollection.size() + failedCount;
        } catch (IOException e) {
            throw new EsException(EsExceptionEnums.ES_FAIL, e);
        }
    }

    @Override
    public void esDocBatchOperationAsync(@NonNull Collection<EsOpData> esOpDataCollection, EsRefreshPolicy esRefreshPolicy, BulkAsyncActionListener bulkAsyncActionListener) {
        if (esOpDataCollection.isEmpty()) {
            return;
        }
        BulkRequest bulkRequest = new BulkRequest();
        //生成批量操作
        esOpDataCollection.forEach(esOpData -> bulkRequest.add(esSingleOperation(esOpData, true)));
        //设置批量刷新策略
        if (esRefreshPolicy != null) {
            bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.parse(esRefreshPolicy.getValue()));
        }
        this.highLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(BulkResponse bulkResponse) {
                int failedCount = 0;
                for (BulkItemResponse response : bulkResponse) {
                    if (response.isFailed()) {
                        failedCount++;
                    }
                }
                bulkAsyncActionListener.onResponse(bulkResponse.getItems().length - failedCount);
            }

            @Override
            public void onFailure(Exception e) {
                EsException esException = new EsException(EsExceptionEnums.ES_FAIL, e);
                bulkAsyncActionListener.onFailure(esException);
            }
        });
    }

    /**
     * 生成es请求
     *
     * @param esOpData es操作数据
     * @return {@link WriteRequest<?>}
     * @throws
     * @author Chenbin Wang
     * @date 2022/4/15 16:20
     */
    private DocWriteRequest<?> esSingleOperation(EsOpData esOpData, boolean isBulk) {

        switch (esOpData.getEsOpType()) {
            case UPDATE:
                return this.esSingleOperationUpdate(esOpData, isBulk);
            case INDEX, CREATE:
                return this.esSingleOperationAdd(esOpData, isBulk);
            case DELETE:
                return this.esSingleOperationDelete(esOpData, isBulk);
            default:
                throw new EsException(EsExceptionEnums.ERROR_OP_TYPE);
        }
    }

    /**
     * 生成es delete请求
     *
     * @param esOpData es操作数据
     * @param isBulk   是否为批量操作
     * @return {@link DeleteRequest}
     * @author chenbin wang
     * @date 2021/12/28 10:57
     */
    private DeleteRequest esSingleOperationDelete(EsOpData esOpData, boolean isBulk) {
        DeleteRequest request = new DeleteRequest(esOpData.getIndexName(), esOpData.getId());
        //非批量请求设置请求刷新策略
        if (!isBulk && null != esOpData.getEsRefreshPolicy()) {
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.parse(esOpData.getEsRefreshPolicy().getValue()));
        }
        return request;
    }


    /**
     * 生成es create/index请求
     *
     * @param esOpData es操作数据
     * @param isBulk   是否为批量操作
     * @return {@link IndexRequest}
     * @author Chenbin Wang
     * @date 2022/4/15 16:20
     */
    private IndexRequest esSingleOperationAdd(EsOpData esOpData, boolean isBulk) {
        IndexRequest request = new IndexRequest(esOpData.getIndexName());
        //非批量请求设置请求刷新策略
        if (!isBulk && null != esOpData.getEsRefreshPolicy()) {
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.parse(esOpData.getEsRefreshPolicy().getValue()));
        }
        request.id(esOpData.getId());
        request.opType(DocWriteRequest.OpType.fromId(esOpData.getEsOpType().getEsOp()));
        return request.source(esOpData.getDocument(), XContentType.JSON);
    }


    /**
     * 生成es update请求
     *
     * @param esOpData es操作数据
     * @param isBulk   是否为批量操作
     * @return {@link UpdateRequest}
     * @author Chenbin Wang
     * @date 2022/4/15 16:15
     */
    private UpdateRequest esSingleOperationUpdate(EsOpData esOpData, boolean isBulk) {
        UpdateRequest request = new UpdateRequest(esOpData.getIndexName(), esOpData.getId());
        //非批量请求设置请求刷新策略
        if (!isBulk && null != esOpData.getEsRefreshPolicy()) {
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.parse(esOpData.getEsRefreshPolicy().getValue()));
        }
        if (null != esOpData.getRetry() && esOpData.getRetry() > 0) {
            request.retryOnConflict(esOpData.getRetry());
        }
        return request.doc(esOpData.getDocument(), XContentType.JSON);
    }

    /**
     * 生成批量返回
     *
     * @param docWriteResponse RestHighLevelClient doc读返回
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/28 17:23
     */
    public static EsResponse createEsResponse(DocWriteResponse docWriteResponse) {
        EsResponse esResponse = new EsResponse<>();
        esResponse.setStatus(docWriteResponse.status().getStatus());
        esResponse.setId(docWriteResponse.getId());
        esResponse.setIndexName(docWriteResponse.getIndex());
        return esResponse;
    }

    public static synchronized HighLevelSearchSourceBuilderConvertDocWriteInterpreter getInstance() {
        if (null == highLevelSearchSourceBuilderConvertDocWriteInterpreter) {
            synchronized (HighLevelSearchSourceBuilderConvertDocWriteInterpreter.class) {
                if (null == highLevelSearchSourceBuilderConvertDocWriteInterpreter) {
                    highLevelSearchSourceBuilderConvertDocWriteInterpreter = new HighLevelSearchSourceBuilderConvertDocWriteInterpreter();
                }
            }
        }
        return highLevelSearchSourceBuilderConvertDocWriteInterpreter;
    }

    private HighLevelSearchSourceBuilderConvertDocWriteInterpreter() {
        this.highLevelClient = HighLevelClient.getInstance();
    }


    private static volatile HighLevelSearchSourceBuilderConvertDocWriteInterpreter highLevelSearchSourceBuilderConvertDocWriteInterpreter;

    private RestHighLevelClient highLevelClient;
}
