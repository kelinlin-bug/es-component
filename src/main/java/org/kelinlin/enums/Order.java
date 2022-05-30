package org.kelinlin.enums;

/**
 * 排序方式
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/19 18:10
 */
public enum Order {

    /**
     * 升序
     */
    ASC("asc"),

    /**
     * 降序
     */
    DESC("desc"),
    ;

    private final String sortOrder;

    public final String getOrder() {
        return this.sortOrder;
    }

    Order(String order) {
        this.sortOrder =order;
    }
}
