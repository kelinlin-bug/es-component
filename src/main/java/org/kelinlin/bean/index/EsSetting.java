package org.kelinlin.bean.index;

import lombok.Data;
import lombok.ToString;
import org.kelinlin.constant.EsConstant;

/**
 * EsSettings 用于创建index时设置Settings
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 10:23
 */
@Data
@ToString
public class EsSetting {

    /**
     * 查询深度
     */
    private String max_result_window;

    /**
     * 分片数
     */
    private String number_of_shards;

    /**
     * 副本数
     */
    private String number_of_replicas;

    /**
     * 创建通用EsSettings
     *
     * @param maxResultWindow 查询深度
     * @param shards          分片数
     * @param replicas        副本数
     * @return {@link EsSetting}
     * @author Chenbin Wang
     * @date 2022/4/15 10:39
     */
    public static EsSetting createNormal(int maxResultWindow, int shards, int replicas) {
        return new EsSetting(maxResultWindow, shards, replicas);
    }

    /**
     * 获取默认EsSettings
     *
     * @return {@link EsSetting}
     * @author Chenbin Wang
     * @date 2022/4/15 10:46
     */
    public static EsSetting defaultEsSettings() {
        return EsSetting.DEFAULT_ES_SETTINGS;
    }

    private EsSetting(int maxResultWindow, int shards, int replicas) {
        this.max_result_window = String.valueOf(maxResultWindow);
        this.number_of_shards = String.valueOf(shards);
        this.number_of_replicas = String.valueOf(replicas);
    }


    private EsSetting() {
        throw new IllegalStateException("Invalid structure");
    }

    /**
     * 默认EsSettings
     */
    private static final EsSetting DEFAULT_ES_SETTINGS = EsSetting
            .createNormal(EsConstant.DEFAULT_RESULT_WINDOW, EsConstant.DEFAULT_SHARDS_NUM, EsConstant.DEFAULT_REPLICAS_NUM);

}
