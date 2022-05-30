package org.kelinlin.interpreter.highlevel;

import org.kelinlin.interpreter.AbstractEsInterpreter;
import org.kelinlin.interpreter.highlevel.doc.HighLevelDocQueryInterpreter;
import org.kelinlin.interpreter.highlevel.doc.HighLevelSearchSourceBuilderConvertDocWriteInterpreter;
import org.kelinlin.interpreter.highlevel.index.HighLevelIndexInterpreter;

/**
 * RestHighLevelClient解释器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 14:22
 */
public class HighLevelEsInterpreter extends AbstractEsInterpreter {


    public static synchronized HighLevelEsInterpreter getInstance() {
        if (null == highLevelDocInterpreter) {
            synchronized (HighLevelEsInterpreter.class) {
                if (null == highLevelDocInterpreter) {
                    highLevelDocInterpreter = new HighLevelEsInterpreter();
                }
            }
        }
        return highLevelDocInterpreter;
    }

    private static volatile HighLevelEsInterpreter highLevelDocInterpreter;

    private HighLevelEsInterpreter() {
        this.setDocQueryInterpreter(HighLevelDocQueryInterpreter.getInstance());
        this.setDocWriteInterpreter(HighLevelSearchSourceBuilderConvertDocWriteInterpreter.getInstance());
        this.setIndexInterpreter(HighLevelIndexInterpreter.getInstance());
    }
}
