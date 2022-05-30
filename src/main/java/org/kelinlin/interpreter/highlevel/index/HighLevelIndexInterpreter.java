package org.kelinlin.interpreter.highlevel.index;

import com.alibaba.fastjson2.JSON;
import org.kelinlin.bean.index.EsMapping;
import org.kelinlin.bean.index.EsSetting;
import org.kelinlin.constant.EsConstant;
import org.kelinlin.enums.EsExceptionEnums;
import org.kelinlin.exception.EsException;
import org.kelinlin.interpreter.IndexInterpreter;
import org.kelinlin.interpreter.highlevel.client.HighLevelClient;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * es index RestHighLevelClient 解释器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/21 15:10
 */
public class HighLevelIndexInterpreter implements IndexInterpreter {

    @Override
    public boolean createIndex(String indexName, EsSetting esSettings, Collection<EsMapping> esMappings) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            //设置esSettings
            if (null != esSettings) {
                Settings.Builder builder = Settings.builder();
                Map<String, String> settingMap = JSON.parseObject(JSON.toJSONString(esSettings), Map.class);
                settingMap.forEach(builder::put);
                request.settings(builder);
            }
            //设置mappings
            if (null != esMappings && !esMappings.isEmpty()) {
                //索引mappings动态拼接
                XContentBuilder xContentBuilder = createEsMappers(esMappings);
                if (null != xContentBuilder) {
                    request.mapping(xContentBuilder);
                }
            }
            CreateIndexResponse createIndexResponse = this.highLevelClient.indices().create(request, RequestOptions.DEFAULT);
            return createIndexResponse.isAcknowledged();
        } catch (Exception e) {
            throw new EsException(EsExceptionEnums.ES_FAIL, e);
        }
    }

    @Override
    public boolean indexExists(String indexName) {
        try {
            return this.highLevelClient.indices()
                    .exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new EsException(EsExceptionEnums.ES_FAIL, e);
        }
    }

    @Override
    public boolean addAlias(String indexName, String alias) {
        try {
            AcknowledgedResponse acknowledgedResponse = this.highLevelClient
                    .indices()
                    .updateAliases(new IndicesAliasesRequest()
                                    .addAliasAction(new IndicesAliasesRequest
                                            .AliasActions(IndicesAliasesRequest
                                            .AliasActions.Type.ADD)
                                            .index(indexName)
                                            .alias(alias))
                            , RequestOptions.DEFAULT);
            return acknowledgedResponse.isAcknowledged();
        } catch (IOException e) {
            throw new EsException(EsExceptionEnums.ES_FAIL, e);
        }
    }

    @Override
    public boolean deleteIndex(String indexName) {
        try {
            AcknowledgedResponse acknowledgedResponse = this.highLevelClient
                    .indices()
                    .delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
            return acknowledgedResponse.isAcknowledged();
        } catch (IOException e) {
            throw new EsException(EsExceptionEnums.ES_FAIL, e);
        }
    }


    /**
     * 索引mappings动态拼接
     *
     * @param esMappings mapping集合
     * @return XContentBuilder
     * @throws IOException
     */
    private XContentBuilder createEsMappers(Collection<EsMapping> esMappings) throws IOException {
        //拼接mapping
        XContentBuilder mappings = JsonXContent.contentBuilder().startObject().startObject(EsConstant.PROPERTIES);
        boolean checkFlag = false;
        for (EsMapping esMapping : esMappings) {
            mappings.startObject(esMapping.getMappingName());
            Map<String, Object> mapping = JSON.parseObject(JSON.toJSONString(esMapping), Map.class);
            Set<Map.Entry<String, Object>> entries = mapping.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                if (EsConstant.MAPPING_NAME.equals(entry.getKey())) {
                    continue;
                }
                if (null != entry.getValue()) {
                    mappings.field(entry.getKey(), entry.getValue());
                    checkFlag = true;
                }
            }
            mappings.endObject();
        }
        mappings.endObject().endObject();
        if (checkFlag) {
            return mappings;
        }
        return null;
    }

    public static synchronized HighLevelIndexInterpreter getInstance() {
        if (null == highLevelIndexInterpreter) {
            synchronized (HighLevelIndexInterpreter.class) {
                if (null == highLevelIndexInterpreter) {
                    highLevelIndexInterpreter = new HighLevelIndexInterpreter();

                }
            }
        }
        return highLevelIndexInterpreter;
    }

    private HighLevelIndexInterpreter() {
        this.highLevelClient = HighLevelClient.getInstance();
    }

    private static volatile HighLevelIndexInterpreter highLevelIndexInterpreter;

    private RestHighLevelClient highLevelClient;
}
