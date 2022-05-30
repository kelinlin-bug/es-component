package org.kelinlin.interpreter;

import lombok.Setter;

/**
 * doc 解释器基类
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 14:12
 */
@Setter
public class AbstractEsInterpreter implements EsInterpreter {

    @Override
    public <T extends DocQueryInterpreter> T getDocQueryInterpreter() {
        return (T)this.docQueryInterpreter;
    }

    @Override
    public <T extends DocWriteInterpreter> T getDocWriteInterpreter() {
        return (T)this.docWriteInterpreter;
    }

    @Override
    public <T extends IndexInterpreter> T getIndexInterpreter() {
        return (T)this.indexInterpreter;
    }

    @Override
    public <T extends EsInterpreter> T getEsInterpreter() {
        return (T)this;
    }


    /**
     * es doc query 解释器
     */
    private DocQueryInterpreter docQueryInterpreter;

    /**
     * es doc write 解释器
     */
    private DocWriteInterpreter docWriteInterpreter;

    /**
     * es index 解释器
     */
    private IndexInterpreter indexInterpreter;
}
