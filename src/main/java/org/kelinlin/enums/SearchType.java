package org.kelinlin.enums;

/**
 * 查询方式
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/5/25 18:03
 */
public enum SearchType {

    /**
     * 预算分，数据更准确，性能差
     */
    DFS_QUERY_THEN_FETCH((byte) 0),

    /**
     * 不预算分
     */
    QUERY_THEN_FETCH((byte) 1)
    ;

    private byte id;

    SearchType(byte id) {
        this.id = id;
    }

    public byte id() {
        return this.id;
    }

    public final byte getId() {
        return this.id;
    }
}
