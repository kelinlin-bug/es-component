package org.kelinlin.component;

import org.kelinlin.interpreter.EsInterpreter;
import org.kelinlin.interpreter.highlevel.HighLevelEsInterpreter;
import org.kelinlin.service.EsDocument;
import org.kelinlin.service.EsIndices;
import org.kelinlin.service.impl.EsDocumentImpl;
import org.kelinlin.service.impl.EsIndicesImpl;

/**
 * es通用工具
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 9:59
 */
public class EsComponent {

    /**
     * index 操作
     *
     * @param esInterpreter es doc 解释器
     * @return {@link EsIndices}
     * @author Chenbin Wang
     * @date 2022/4/15 14:45
     */
    public static final EsIndices indices(EsInterpreter esInterpreter) {
        return EsIndicesImpl.getInstance(esInterpreter);
    }

    /**
     * index 操作
     *
     * @return {@link EsIndices}
     * @author Chenbin Wang
     * @date 2022/4/15 14:45
     */
    public static final EsIndices indices() {
        return EsIndicesImpl.getInstance(HighLevelEsInterpreter.getInstance());
    }

    /**
     * doc 操作
     *
     * @param esInterpreter es doc 解释器
     * @return {@link EsDocument}
     * @author Chenbin Wang
     * @date 2022/4/15 14:53
     */
    public static final EsDocument doc(EsInterpreter esInterpreter) {
        return EsDocumentImpl.getInstance(esInterpreter);
    }

    /**
     * doc 操作
     *
     * @return {@link EsDocument}
     * @author Chenbin Wang
     * @date 2022/4/15 14:53
     */
    public static final EsDocument doc() {
        return EsDocumentImpl.getInstance(HighLevelEsInterpreter.getInstance());
    }

    private EsComponent() {
        throw new IllegalStateException("Utility class");
    }
}
