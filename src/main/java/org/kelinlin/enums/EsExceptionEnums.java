package org.kelinlin.enums;

import lombok.ToString;

import java.io.Serializable;

/**
 * EsExceptionEnums
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 10:57
 */
@ToString
public enum EsExceptionEnums implements Serializable {

    ES_FAIL(7000, "es错误"),
    ES_MAPPINGS_TYPE_ERROR(7001, "mappings 格式错误"),
    INDEX_ALREADY_EXIST(7002, "索引已存在"),
    INDEX_IS_IN_EXISTENCE(7003, "索引不存在"),
    ERROR_OP_TYPE(7004, "不支持的操作类型"),
    ERROR_QUERY_TYPE(7005, "不支持的查询条件类型"),

    ERROR_SORT_TYPE(7006, "不支持的排序类型"),

    ERROR_AGG_TYPE(7007, "不支持的聚合类型"),

    LARGE_THAN_MAX_TOP_HITS_LIMIT(7008, "topHits 查询深度(offset + limit)最大为100"),
    ES_CLIENT_NOT_REGISTER(7010, "使用前请先注册"),
    ES_CLIENT_ALREADY_REGISTER(7011, "服务已注册"),
    ERROR_ES_CODE(7099, "es返回code：");
    private final Integer code;
    private final String msg;

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    EsExceptionEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
