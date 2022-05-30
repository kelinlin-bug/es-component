package org.kelinlin.interpreter;

import org.kelinlin.bean.index.EsMapping;
import org.kelinlin.bean.index.EsSetting;

import java.util.Collection;

/**
 * es index 解释器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/21 15:05
 */
public interface IndexInterpreter {

    /**
     * 创建索引
     *
     * @param indexName  索引名
     * @param esSettings EsSettings
     * @param esMappings EsMapping集合
     * @return {@link boolean}
     * @author Chenbin Wang
     * @date 2022/4/15 12:48
     */
    boolean createIndex(String indexName, EsSetting esSettings, Collection<EsMapping> esMappings);

    /**
     * 判断索引是否存在
     *
     * @param indexName 索引名
     * @return boolean
     * @author Chenbin Wang
     * @date 2021/11/12
     */
    boolean indexExists(String indexName);

    /**
     * 为索引赋别名
     *
     * @param indexName 索引名
     * @param alias     别名
     * @return {@link boolean}
     * @author Chenbin Wang
     * @date 2022/4/15 13:53
     */
    boolean addAlias(String indexName, String alias);

    /**
     * 删除索引
     *
     * @param indexName  索引名
     * @return {@link boolean}
     * @author Chenbin Wang
     * @date 2022/4/15 14:19
     */
    boolean deleteIndex(String indexName);
}
