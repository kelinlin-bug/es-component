package org.kelinlin.bean.doc.query;

import lombok.Builder;
import lombok.Getter;
import org.kelinlin.bulider.sort.EsSortBuilder;
import org.kelinlin.bulider.terms.EsAggQueryBuilder;
import org.kelinlin.bulider.terms.EsQueryBuilder;

import java.util.Collection;

/**
 * SearchSourceData
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 10:23
 */
@Getter
@Builder
public class EsSearchSourceData {

    /**
     * offset
     */
    private Integer offset;

    /**
     * limit
     */
    private Integer limit;

    /**
     * 超时时间
     */
    private EsTimeData timeOut;

    /**
     * must 条件集合（参与算分）
     */
    private Collection<EsQueryBuilder> mustBuilders;

    /**
     * should 条件集合
     */
    private Collection<EsQueryBuilder> shouldBuilders;

    /**
     * filter 条件集合（不参与算分）
     */
    private Collection<EsQueryBuilder> filterBuilders;

    /**
     * mustNot 条件集合
     */
    private Collection<EsQueryBuilder> mustNotBuilders;

    /**
     * 排序集合
     */
    private Collection<EsSortBuilder<?>> sortBuilders;

    /**
     * 聚合条件
     */
    private EsAggQueryBuilder esAggQueryBuilder;
}
