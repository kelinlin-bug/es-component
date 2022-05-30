package org.kelinlin.service.impl;

import lombok.NonNull;
import org.kelinlin.interpreter.EsInterpreter;
import org.kelinlin.bean.index.EsMapping;
import org.kelinlin.bean.index.EsSetting;
import org.kelinlin.enums.EsExceptionEnums;
import org.kelinlin.exception.EsException;
import org.kelinlin.service.EsIndices;

import java.util.Collection;
import java.util.Locale;

/**
 * EsIndices 索引操作
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 14:35
 */
public class EsIndicesImpl implements EsIndices {

    @Override
    public boolean createIndex(@NonNull String indexName) {
        return this.createIndex(indexName, EsSetting.defaultEsSettings(), null);
    }

    @Override
    public boolean createIndex(@NonNull String indexName, EsSetting esSettings, Collection<EsMapping> esMappings) {
        //判断索引是否存在
        if (indexExists(indexName)) {
            throw new EsException(EsExceptionEnums.INDEX_ALREADY_EXIST);
        }
        return esInterpreter.getIndexInterpreter().createIndex(indexName.toLowerCase(Locale.ROOT), esSettings, esMappings);
    }


    @Override
    public boolean indexExists(@NonNull String indexName) {
        return esInterpreter.getIndexInterpreter().indexExists(indexName.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean addAlias(@NonNull String indexName, @NonNull String alias) {
        //判断索引是否存在
        if (!indexExists(indexName)) {
            throw new EsException(EsExceptionEnums.INDEX_IS_IN_EXISTENCE);
        }
        return esInterpreter.getIndexInterpreter().addAlias(indexName.toLowerCase(Locale.ROOT), alias.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean deleteIndex(@NonNull String indexName) {
        return esInterpreter.getIndexInterpreter().deleteIndex(indexName.toLowerCase(Locale.ROOT));
    }

    public static synchronized EsIndices getInstance(EsInterpreter esInterpreter) {
        if (null == esIndices) {
            synchronized (EsIndices.class) {
                if (null == esIndices) {
                    esIndices = new EsIndicesImpl(esInterpreter);
                }
            }
        }
        return esIndices;
    }

    private EsIndicesImpl() {
        throw new IllegalStateException("Invalid structure");
    }

    private EsIndicesImpl(EsInterpreter esInterpreter) {
        this.esInterpreter = esInterpreter;
    }

    private static volatile EsIndices esIndices;

    private EsInterpreter esInterpreter;
}
