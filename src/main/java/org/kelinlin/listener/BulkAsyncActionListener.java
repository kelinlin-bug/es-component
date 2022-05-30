package org.kelinlin.listener;

import org.kelinlin.exception.EsException;

/**
 * 批量操作异步回调监听器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/28 16:52
 */
public interface BulkAsyncActionListener {

    /**
     * 返回回调方法
     *
     * @param successCount 成功条数
     * @author Chenbin Wang
     * @date 2022/4/28 16:56
     */
    void onResponse(int successCount);

    /**
     * 抛错回调
     *
     * @param esException 返回错误
     * @author Chenbin Wang
     * @date 2022/4/28 16:57
     */
    void onFailure(EsException esException);
}
