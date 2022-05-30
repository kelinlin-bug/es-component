package org.kelinlin.bean.doc.write;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * es doc基础请求信息
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/18 10:04
 */
@Data
@ToString
@AllArgsConstructor
public class DocWriteData {

    /**
     * _id
     */
    private String id;

    /**
     * 索引名
     */
    private String indexName;

    /**
     * 文档内容（删除不需要此项）
     */
    private Object document;
}
