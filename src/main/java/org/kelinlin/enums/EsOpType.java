package org.kelinlin.enums;

/**
 * Es操作枚举
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 15:19
 */
public enum EsOpType {

    /**
     * 创建并替换_id相同的doc
     */
    INDEX(0),

    /**
     * 创建doc
     */
    CREATE(1),

    /**
     * 更新doc
     */
    UPDATE(2),

    /**
     * 删除doc
     */
    DELETE(3);

    /**
     * 操作类型
     */
    private final byte esOp;

    public final byte getEsOp() {
        return this.esOp;
    }

    EsOpType(int esOp) {
        this.esOp = (byte) esOp;
    }
}
