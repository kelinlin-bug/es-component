package org.kelinlin.bulider.sort;

import org.kelinlin.enums.Order;

/**
 * 桶排序构造，用于聚合terms下设置子聚合的排序
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 11:07
 */
public class BucketEsSortBuilder extends AbstractEsSortBuilder<BucketEsSortBuilder> {

    /**
     * 按桶排序构造
     *
     * @param path  子聚合路径（name）
     * @param order 排序方式
     * @return {@link BucketEsSortBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 11:21
     */
    public static BucketEsSortBuilder agg(String path, Order order) {
        return new BucketEsSortBuilder(path).order(order);
    }

    /**
     * 按数量排序
     *
     * @param order 排序方式
     * @return {@link BucketEsSortBuilder}
     * @author Chenbin Wang
     * @date 2022/4/25 13:37
     */
    public static BucketEsSortBuilder count(Order order) {
        return new BucketEsSortBuilder(BucketEsSortBuilder.COUNT_BUCKET_SORT).order(order);
    }

    /**
     * 按key值排序
     *
     * @param order 排序方式
     * @return {@link BucketEsSortBuilder}
     * @author Chenbin Wang
     * @date 2022/4/25 13:38
     */
    public static BucketEsSortBuilder key(Order order) {
        return new BucketEsSortBuilder(BucketEsSortBuilder.KEY_BUCKET_SORT).order(order);
    }

    /**
     * 创建桶排序构造
     *
     * @param path 子聚合路径（name）
     * @return {@link BucketEsSortBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 11:22
     */
    public static BucketEsSortBuilder create(String path) {
        return new BucketEsSortBuilder(path);
    }

    private BucketEsSortBuilder(String path) {
        super(path);
    }

    public static final String KEY_BUCKET_SORT = "key";

    public static final String COUNT_BUCKET_SORT = "count";

    public static final String AGG_BUCKET_SORT = "agg";

}
