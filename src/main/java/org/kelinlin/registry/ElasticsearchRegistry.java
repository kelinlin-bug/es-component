package org.kelinlin.registry;

import lombok.Getter;
import lombok.ToString;
import org.kelinlin.bean.config.ElasticsearchConfig;
import org.kelinlin.enums.EsExceptionEnums;
import org.kelinlin.exception.EsException;

/**
 * es注册器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 17:46
 */
@ToString
@Getter
public class ElasticsearchRegistry {

    /**
     * 配置注册
     *
     * @return {@link ElasticsearchRegistry}
     * @throws
     * @author Chenbin Wang
     * @date 2022/4/20 18:00
     */
    public static void register(ElasticsearchConfig elasticsearchConfig) {
        if (null == elasticsearchRegistry) {
            synchronized (ElasticsearchRegistry.class) {
                if (null == elasticsearchRegistry) {
                    elasticsearchRegistry = new ElasticsearchRegistry(elasticsearchConfig);
                    return;
                }
            }
        }
        throw new EsException(EsExceptionEnums.ES_CLIENT_ALREADY_REGISTER);
    }

    public static ElasticsearchRegistry getInstance() {
        return elasticsearchRegistry;
    }

    private ElasticsearchRegistry() {
        throw new IllegalStateException("Invalid structure");
    }

    private ElasticsearchRegistry(ElasticsearchConfig elasticsearchConfig) {
        this.config = elasticsearchConfig;
    }

    private ElasticsearchConfig config;

    private static volatile ElasticsearchRegistry elasticsearchRegistry;
}
