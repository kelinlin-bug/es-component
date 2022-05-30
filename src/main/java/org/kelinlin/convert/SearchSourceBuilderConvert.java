package org.kelinlin.convert;

import org.kelinlin.bean.doc.query.EsSearchSourceData;

/**
 * 查询条件解析转换器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/19 10:28
 */
public interface SearchSourceBuilderConvert<T> {


    /**
     * 查询条件解析换器
     *
     * @param esSearchSourceData    searchSourceData
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/19 10:42
     */
    T buildersConvert(EsSearchSourceData esSearchSourceData);
}
