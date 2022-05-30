package org.kelinlin.interpreter.highlevel.client;

import org.kelinlin.bean.config.ElasticsearchConfig;
import org.kelinlin.enums.EsExceptionEnums;
import org.kelinlin.exception.EsException;
import org.kelinlin.registry.ElasticsearchRegistry;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Arrays;
import java.util.Objects;

/**
 * 创建restHighLevelClient
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 17:46
 */
public class HighLevelClient {

    /**
     * 创建RestClientBuilder
     *
     * @param ipAddress ip地址数组
     * @return {@link RestClientBuilder}
     * @author Chenbin Wang
     * @date 2022/4/20 18:15
     */
    private static RestClientBuilder restClientBuilder(String[] ipAddress) {
        HttpHost[] hosts = Arrays.stream(ipAddress)
                .map(HighLevelClient::makeHttpHost)
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);
        return RestClient.builder(hosts);
    }

    /**
     * 创建RestHighLevelClient
     *
     * @return {@link RestHighLevelClient}
     * @author Chenbin Wang
     * @date 2022/4/20 18:15
     */
    private static RestHighLevelClient createRestHighLevelClient() {
        // 异步httpclient连接延时配置
        ElasticsearchRegistry elasticsearchRegistry = ElasticsearchRegistry.getInstance();
        if (null == elasticsearchRegistry) {
            throw new EsException(EsExceptionEnums.ES_CLIENT_NOT_REGISTER);
        }
        ElasticsearchConfig config = elasticsearchRegistry.getConfig();
        RestClientBuilder restClientBuilder = HighLevelClient.restClientBuilder(config.getIpAddress());
        restClientBuilder.setRequestConfigCallback(builder -> {
            builder.setConnectTimeout(config.getConnectTimeOut());
            builder.setSocketTimeout(config.getSocketTimeOut());
            builder.setConnectionRequestTimeout(config.getConnectionRequestTimeOut());
            return builder;
        });
        restClientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            httpAsyncClientBuilder.setMaxConnTotal(config.getMaxConnectNum());
            httpAsyncClientBuilder.setMaxConnPerRoute(config.getMaxConnectPerRoute());
            return httpAsyncClientBuilder;
        });
        return new RestHighLevelClient(restClientBuilder);
    }

    /**
     * 创建HttpHost
     *
     * @param ipStr ip字符串
     * @return {@link HttpHost}
     * @author Chenbin Wang
     * @date 2022/4/20 18:16
     */
    private static HttpHost makeHttpHost(String ipStr) {
        if (ipStr != null && !"".equals(ipStr)) {
            String[] address = ipStr.split(":");
            if (address.length == 2) {
                String ip = address[0];
                int port = Integer.parseInt(address[1]);
                return new HttpHost(ip, port, "http");
            }
        }
        return null;
    }

    public static synchronized RestHighLevelClient getInstance() {
        if (null == restHighLevelClient) {
            synchronized (HighLevelClient.class) {
                if (null == restHighLevelClient) {
                    restHighLevelClient = HighLevelClient.createRestHighLevelClient();
                }
            }
        }
        return restHighLevelClient;
    }

    private HighLevelClient() {
        throw new IllegalStateException("Invalid structure");
    }

    private static volatile RestHighLevelClient restHighLevelClient;
}
