package org.kelinlin.interpreter.highlevel.convert;

import org.kelinlin.bulider.sort.EsSortBuilder;
import org.kelinlin.bulider.sort.FieldEsSortBuilder;
import org.kelinlin.bulider.sort.ScoreEsSortBuilder;
import org.kelinlin.convert.EsSortBuilderConvert;
import org.kelinlin.enums.EsExceptionEnums;
import org.kelinlin.exception.EsException;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

/**
 * 默认排序RestHighLevelClient解析转换器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/25 13:57
 */
public class HighLevelSortBuilderConvert implements EsSortBuilderConvert<SortBuilder<?>> {

    @Override
    public SortBuilder sortBuilderConvert(EsSortBuilder<?> esSortBuilder) {
        //field排序
        if (esSortBuilder instanceof FieldEsSortBuilder fieldEsSortBuilder) {
            return SortBuilders.fieldSort(fieldEsSortBuilder.getField()).order(SortOrder.fromString(fieldEsSortBuilder.getOrder().getOrder()));
        } else if (esSortBuilder instanceof ScoreEsSortBuilder scoreEsSortBuilder) {
            //sort排序
            return SortBuilders.scoreSort().order(SortOrder.fromString(scoreEsSortBuilder.getOrder().getOrder()));
        } else {
            throw new EsException(EsExceptionEnums.ERROR_SORT_TYPE);
        }
    }

    public static synchronized HighLevelSortBuilderConvert getInstance() {
        if (null == highLevelSortBuilderConvert) {
            synchronized (HighLevelSortBuilderConvert.class) {
                if (null == highLevelSortBuilderConvert) {
                    highLevelSortBuilderConvert = new HighLevelSortBuilderConvert();
                }
            }
        }
        return highLevelSortBuilderConvert;
    }

    private HighLevelSortBuilderConvert() {
        super();
    }


    private static volatile HighLevelSortBuilderConvert highLevelSortBuilderConvert;
}
