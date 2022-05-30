package org.kelinlin.bulider.terms;

import lombok.Getter;
import lombok.ToString;
import org.kelinlin.bulider.sort.BucketEsSortBuilder;

/**
 * 聚合 分组构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/22 15:27
 */
@ToString
public class TermsAggBuilder extends KeywordEsQueryBuilder {

    /**
     * 设置返回的桶数
     *
     * @param size 数量
     * @return {@link TermsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/25 10:11
     */
    public TermsAggBuilder bucketSize(int size) {
        this.bucketSize = size;
        return this;
    }

    /**
     * 设置子聚合排序
     *
     * @param bucketEsSortBuilder 桶排序构造
     * @return {@link TermsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 11:27
     */
    public TermsAggBuilder bucketSort(BucketEsSortBuilder bucketEsSortBuilder) {
        this.bucketEsSortBuilder = bucketEsSortBuilder;
        return this;
    }

    /**
     * 创建分组构造
     *
     * @param name 分组名
     * @return {@link TermsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 10:59
     */
    public static TermsAggBuilder create(String name) {
        return new TermsAggBuilder(name);
    }

    private TermsAggBuilder(String name) {
        super(name);
    }

    /**
     * 桶排序构造
     */
    @Getter
    private BucketEsSortBuilder bucketEsSortBuilder = null;

    /**
     * 返回的桶数
     */
    @Getter
    private Integer bucketSize = 10;
}
