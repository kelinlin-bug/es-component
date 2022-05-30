package org.kelinlin.bean.doc.write;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.kelinlin.enums.EsOpType;
import org.kelinlin.enums.EsRefreshPolicy;

/**
 * es 操作数据
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 15:26
 */
@Getter
@ToString
@Builder
public class EsOpData {

    /**
     * _id
     */
    private String id;

    /**
     * 索引名
     */
    private String indexName;

    /**
     * 文档内容
     */
    private String document;

    /**
     * Es操作枚举
     */
    private EsOpType esOpType;

    /**
     * 重试次数
     */
    private Integer retry;

    /**
     * 请求刷新策略
     */
    private EsRefreshPolicy esRefreshPolicy;

}
