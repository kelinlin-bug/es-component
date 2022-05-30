package org.kelinlin.bulider.terms;

import lombok.Getter;
import lombok.ToString;
import org.kelinlin.bulider.sort.EsSortBuilder;
import org.kelinlin.constant.EsConstant;
import org.kelinlin.enums.EsExceptionEnums;
import org.kelinlin.exception.EsException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 聚合 doc返回数据条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/24 13:55
 */
@ToString
public class TopHitsAggBuilder extends AbstractEsAggQueryBuilder {

    /**
     * 添加排序规则
     *
     * @param esSortBuilders 排序构造集合
     * @return {@link TopHitsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 14:35
     */
    public TopHitsAggBuilder sort(Collection<EsSortBuilder<?>> esSortBuilders) {
        this.sortBuilders.addAll(esSortBuilders);
        return this;
    }

    /**
     * 添加排序规则
     *
     * @param esSortBuilder 排序构造
     * @return {@link TopHitsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 14:35
     */
    public TopHitsAggBuilder sort(EsSortBuilder<?> esSortBuilder) {
        this.sortBuilders.add(esSortBuilder);
        return this;
    }

    /**
     * 设置offset
     *
     * @param offset offset
     * @return {@link TopHitsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 14:11
     */
    public TopHitsAggBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }

    /**
     * 设置limit
     *
     * @param limit limit
     * @return {@link TopHitsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 14:13
     */
    public TopHitsAggBuilder limit(int limit) {
        if (limit > EsConstant.DEFAULT_QUERY_LIMIT) {
            throw new EsException(EsExceptionEnums.LARGE_THAN_MAX_TOP_HITS_LIMIT);
        }
        this.limit = limit;
        return this;
    }

    /**
     * 创建 聚合 doc返回数据条件构造
     *
     * @param name
     * @return {@link TopHitsAggBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 14:22
     */
    public static TopHitsAggBuilder create(String name) {
        return new TopHitsAggBuilder(name);
    }

    private TopHitsAggBuilder(String name) {
        super(name);
    }

    /**
     * offset
     */
    @Getter
    private Integer offset = 0;

    /**
     * limit
     */
    @Getter
    private Integer limit = EsConstant.DEFAULT_TOP_HITS_LIMIT;

    /**
     * 排序集合
     */
    @Getter
    private Collection<EsSortBuilder<?>> sortBuilders = new ArrayList<>();
}
