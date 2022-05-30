package org.kelinlin.interpreter;

import lombok.NonNull;
import org.kelinlin.bean.doc.write.EsOpData;
import org.kelinlin.bean.response.EsResponse;
import org.kelinlin.enums.EsRefreshPolicy;
import org.kelinlin.listener.AsyncActionListener;
import org.kelinlin.listener.BulkAsyncActionListener;

import java.util.Collection;

/**
 * es doc write 解释器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/19 9:43
 */
public interface DocWriteInterpreter {

    /**
     * es 单次请求
     *
     * @param esOpData es 操作数据
     * @return {@link EsResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 18:16
     */
    EsResponse esDocSingleRequest(EsOpData esOpData);

    /**
     * es 异步单次请求
     *
     * @param esOpData            es 操作数据
     * @param asyncActionListener 异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:32
     */
    void esDocSingleRequestAsync(EsOpData esOpData, AsyncActionListener asyncActionListener);

    /**
     * 批量操作（返回成功条数）
     *
     * @param esOpDataCollection es 操作数据集合
     * @param esRefreshPolicy    es 请求刷新策略
     * @return {@link int} 成功条数
     * @author Chenbin Wang
     * @date 2022/4/18 9:55
     */
    int esDocBatchOperation(@NonNull Collection<EsOpData> esOpDataCollection, EsRefreshPolicy esRefreshPolicy);

    /**
     * 异步批量操作
     *
     * @param esOpDataCollection      es 操作数据集合
     * @param esRefreshPolicy         es 请求刷新策略
     * @param bulkAsyncActionListener 批量操作异步回调监听器
     * @author Chenbin Wang
     * @date 2022/4/28 17:20
     */
    void esDocBatchOperationAsync(@NonNull Collection<EsOpData> esOpDataCollection, EsRefreshPolicy esRefreshPolicy, BulkAsyncActionListener bulkAsyncActionListener);


}
