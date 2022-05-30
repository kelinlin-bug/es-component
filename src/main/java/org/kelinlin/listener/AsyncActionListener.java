package org.kelinlin.listener;

import org.kelinlin.bean.response.EsResponse;
import org.kelinlin.exception.EsException;

/**
 * 异步回调监听器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/28 17:05
 */
public interface AsyncActionListener {

    /**
     * 返回回调方法
     *
     * @param esResponse 返回数据
     * @author Chenbin Wang
     * @date 2022/4/28 16:56
     */
    void onResponse(EsResponse esResponse);

    /**
     * 抛错回调
     *
     * @param esException 返回错误
     * @author Chenbin Wang
     * @date 2022/4/28 16:57
     */
    void onFailure(EsException esException);
}
