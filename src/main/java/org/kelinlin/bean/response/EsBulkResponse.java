package org.kelinlin.bean.response;

import lombok.ToString;

import java.util.Collection;

/**
 * es 批量返回
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 17:34
 */
@ToString
public class EsBulkResponse {

    /**
     * 返回信息集合
     */
    private Collection<EsResponse> esResponses;

    /**
     * 状态(-1 未执行，200 已经执行，具体情况详见esResponses)
     */
    private Integer status;

    /**
     * 创建es 批量返回
     *
     * @param esResponses 返回信息集合
     * @param status      状态
     * @return {@link EsBulkResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 9:42
     */
    public static EsBulkResponse create(Collection<EsResponse> esResponses, Integer status) {
        return new EsBulkResponse(esResponses, status);
    }

    /**
     * 创建es 批量返回
     *
     * @param esResponses 返回信息集合
     * @return {@link EsBulkResponse}
     * @author Chenbin Wang
     * @date 2022/4/18 9:42
     */
    public static EsBulkResponse create(Collection<EsResponse> esResponses) {
        return new EsBulkResponse(esResponses);
    }

    private EsBulkResponse() {
        throw new IllegalStateException("Invalid structure");
    }

    private EsBulkResponse(Collection<EsResponse> esResponses, Integer status) {
        this.esResponses = esResponses;
        this.status = status;
    }

    private EsBulkResponse(Collection<EsResponse> esResponses) {
        this.esResponses = esResponses;
        this.status = -1;
    }

    public Collection<EsResponse> getEsResponses() {
        return esResponses;
    }
}
