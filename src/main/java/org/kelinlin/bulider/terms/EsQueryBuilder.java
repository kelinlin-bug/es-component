package org.kelinlin.bulider.terms;

/**
 * es查询条件构造
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 13:52
 */
public interface EsQueryBuilder {


    /**
     * es查询条件构造
     *
     * @return {@link EsQueryBuilder}
     * @author Chenbin Wang
     * @date 2022/4/18 15:52
     */
    <T extends EsQueryBuilder> T getEsQueryBuilder();
}
