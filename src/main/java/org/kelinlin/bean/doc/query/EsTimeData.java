package org.kelinlin.bean.doc.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

/**
 * 时长
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 11:02
 */
@Data
@ToString
@AllArgsConstructor
public class EsTimeData {

    /**
     * 时长
     */
    Long duration;

    /**
     * 时间格式
     */
    TimeUnit timeUnit;
}
