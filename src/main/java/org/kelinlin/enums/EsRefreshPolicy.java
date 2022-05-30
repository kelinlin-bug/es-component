package org.kelinlin.enums;

/**
 * es 请求刷新策略
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 15:53
 */
public enum EsRefreshPolicy {

    /**
     * 请求后不刷新，es默认策略
     */
    DEFAULT("false"),

    /**
     * 请求后不刷新，es默认策略
     */
    NONE("false"),

    /**
     * 请求后强制刷新，此刷新策略不适用于大数据量索引或大吞吐量，用于为流量非常低的索引提供一致的视图
     */
    IMMEDIATE("true"),
    /**
     * 保持此请求处于打开状态，直到刷新使此请求的内容对搜索可见。此刷新策略与高索引和搜索吞吐量兼容，但它会导致请求等待回复，直到发生刷新。
     */
    WAIT_UNTIL("wait_for");

    private final String value;

    EsRefreshPolicy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
