package org.kelinlin.bulider.soure;

import lombok.Getter;
import lombok.NonNull;
import org.kelinlin.bean.doc.query.EsSearchSourceData;
import org.kelinlin.bean.doc.query.EsTimeData;
import org.kelinlin.bulider.terms.EsQueryBuilder;
import org.kelinlin.enums.SearchType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * esSourceBuilder基类
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/22 14:00
 */
@Getter
public abstract class AbstractEsSourceBuilder<T extends AbstractEsSourceBuilder> {


    /**
     * 添加 must 条件
     * 参与算分，效果类似于sql中and
     *
     * @param esQueryBuilder es查询条件
     * @return {@link EsSearchSourceBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 13:54
     */
    public T must(@NonNull EsQueryBuilder esQueryBuilder) {
        this.mustBuilders.add(esQueryBuilder);
        return (T) this;
    }

    /**
     * 添加 should 条件
     * 效果类似于sql中的or
     *
     * @param esQueryBuilder es查询条件
     * @return {@link EsSearchSourceBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 13:54
     */
    public T should(@NonNull EsQueryBuilder esQueryBuilder) {
        this.shouldBuilders.add(esQueryBuilder);
        return (T) this;
    }

    /**
     * 添加 filter 条件
     * 不参与算分，效果类似于sql中的and，性能优于must
     *
     * @param esQueryBuilder es查询条件
     * @return {@link EsSearchSourceBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 13:54
     */
    public T filter(@NonNull EsQueryBuilder esQueryBuilder) {
        this.filterBuilders.add(esQueryBuilder);
        return (T) this;
    }

    /**
     * 添加 mustNot 条件（must 的反义）
     *
     * @param esQueryBuilder es查询条件
     * @return {@link EsSearchSourceBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 13:54
     */
    public T mustNot(@NonNull EsQueryBuilder esQueryBuilder) {
        this.mustNotBuilders.add(esQueryBuilder);
        return (T) this;
    }

    /**
     * 设置超时时间
     *
     * @param duration 超时时间
     * @param timeUnit 时间单位
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/5/25 17:58
     */
    public T timeOut(Long duration, TimeUnit timeUnit) {
        this.timeOut = new EsTimeData(duration, timeUnit);
        return (T) this;
    }

    /**
     * 设置分片查询的优先级
     *
     * @param preference 分片查询的优先级
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/5/25 17:59
     */
    public T preference(String preference) {
        this.preference = preference;
        return (T) this;
    }

    /**
     * 设置查询方式
     *
     * @param searchType 查询方式
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/5/25 18:08
     */
    public T searchType(SearchType searchType) {
        this.searchType = searchType;
        return (T) this;
    }

    /**
     * 超时时间
     */
    private EsTimeData timeOut = null;

    /**
     * 分片查询的优先级
     */
    private String preference;

    /**
     * 查询方式
     */
    private SearchType searchType;

    /**
     * must 条件集合（参与算分）
     */
    private Collection<EsQueryBuilder> mustBuilders = new ArrayList<>();

    /**
     * should 条件集合
     */
    private Collection<EsQueryBuilder> shouldBuilders = new ArrayList<>();

    /**
     * filter 条件集合（不参与算分）
     */
    private Collection<EsQueryBuilder> filterBuilders = new ArrayList<>();

    /**
     * mustNot 条件集合
     */
    private Collection<EsQueryBuilder> mustNotBuilders = new ArrayList<>();

    /**
     * 数据转换
     *
     * @return {@link EsSearchSourceData}
     * @author Chenbin Wang
     * @date 2022/4/27 10:24
     */
    abstract EsSearchSourceData toSearchSourceData();
}
