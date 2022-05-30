package org.kelinlin.bean.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * es 返回
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 17:23
 */
@Data
@ToString
public class EsResponse<T> {

    /**
     * 状态
     */
    private Integer status = -1;

    /**
     * 索引名
     */
    private String indexName;

    /**
     * doc id
     */
    private String id;

    /**
     * 总数量
     */
    private Long total = -1L;

    /**
     * 数据
     */
    private List<T> data;
}
