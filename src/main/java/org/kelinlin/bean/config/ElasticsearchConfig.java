package org.kelinlin.bean.config;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.kelinlin.enums.EsClient;

/**
 * es配置文件
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 18:02
 */
@Builder
@Getter
@ToString
public class ElasticsearchConfig {

    /**
     * ip:port 数组
     */
    String[] ipAddress;
    /**
     * 连接超时时间
     */
    private Integer connectTimeOut;
    /**
     * 连接超时时间
     */
    private Integer socketTimeOut;
    /**
     * 获取连接的超时时间
     */
    private Integer connectionRequestTimeOut;
    /**
     * 最大连接数
     */
    private Integer maxConnectNum;
    /**
     * 最大路由连接数
     */
    private Integer maxConnectPerRoute;

    /**
     * es client 类型
     */
    private EsClient essClient;
}
