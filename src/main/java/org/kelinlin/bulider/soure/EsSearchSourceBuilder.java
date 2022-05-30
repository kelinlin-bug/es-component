package org.kelinlin.bulider.soure;

import lombok.NonNull;
import lombok.ToString;
import org.kelinlin.bean.doc.query.EsSearchSourceData;
import org.kelinlin.bulider.sort.EsSortBuilder;
import org.kelinlin.constant.EsConstant;
import org.kelinlin.convert.SearchSourceBuilderConvert;

import java.util.ArrayList;
import java.util.Collection;

/**
 * es查询条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 13:43
 */
@ToString
public class EsSearchSourceBuilder extends AbstractEsSourceBuilder<EsSearchSourceBuilder> {

    /**
     * 设置offset
     *
     * @param offset offset
     * @return {@link EsSearchSourceBuilder}
     * @author Chenbin Wang
     * @date 2022/4/19 18:06
     */
    public EsSearchSourceBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }

    /**
     * 设置limit
     *
     * @param limit limit
     * @return {@link EsSearchSourceBuilder}
     * @author Chenbin Wang
     * @date 2022/4/19 18:07
     */
    public EsSearchSourceBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * 添加排序条件
     *
     * @param esSortBuilder 排序构造
     * @return {@link EsSearchSourceBuilder}
     * @author Chenbin Wang
     * @date 2022/4/20 10:13
     */
    public EsSearchSourceBuilder sort(@NonNull EsSortBuilder<?> esSortBuilder) {
        this.sortBuilders.add(esSortBuilder);
        return this;
    }

    /**
     * 添加排序条件
     *
     * @param esSortBuilders 排序构造集合
     * @return {@link EsSearchSourceBuilder}
     * @author Chenbin Wang
     * @date 2022/4/24 14:07
     */
    public EsSearchSourceBuilder sort(Collection<EsSortBuilder<?>> esSortBuilders) {
        this.sortBuilders.addAll(esSortBuilders);
        return this;
    }


    /**
     * 查询条件解析转换
     *
     * @param builderConvert
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/19 10:42
     */
    public <T> T queryBuildersConvert(SearchSourceBuilderConvert<T> builderConvert) {
        return builderConvert.buildersConvert(this.toSearchSourceData());
    }

    /**
     * 创建SearchSourceData
     *
     * @return {@link EsSearchSourceData}
     * @author Chenbin Wang
     * @date 2022/4/20 10:26
     */
    @Override
    public EsSearchSourceData toSearchSourceData() {
        return EsSearchSourceData.builder().mustBuilders(this.getMustBuilders()).shouldBuilders(this.getShouldBuilders())
                .filterBuilders(this.getFilterBuilders()).mustNotBuilders(this.getMustNotBuilders()).sortBuilders(this.sortBuilders)
                .offset(this.offset).limit(this.limit).timeOut(this.getTimeOut()).build();
    }

    public static EsSearchSourceBuilder create() {
        return new EsSearchSourceBuilder();
    }


    private EsSearchSourceBuilder() {
        super();
    }

    /**
     * offset
     */
    private Integer offset = 0;

    /**
     * limit
     */
    private Integer limit = EsConstant.DEFAULT_QUERY_LIMIT;

    /**
     * 排序集合
     */
    private Collection<EsSortBuilder<?>> sortBuilders = new ArrayList<>();

}
