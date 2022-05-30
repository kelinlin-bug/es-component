package org.kelinlin.enums;

/**
 * es client 枚举
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/5/27 13:39
 */
public enum EsClient {

    /**
     * Elasticsearch7.15 后 RestHighLevelClient 已被官方标记弃用
     */
    Rest_High_Level_Client((byte) 0),
    ;

    private final byte clientType;

    public final byte getClientType() {
        return this.clientType;
    }

    EsClient(byte clientType) {
        this.clientType = clientType;
    }
}
