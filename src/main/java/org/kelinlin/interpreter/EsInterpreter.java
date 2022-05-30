package org.kelinlin.interpreter;

/**
 * doc 解释器
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/20 14:03
 */
public interface EsInterpreter {

    /**
     * 获取es doc query 解释器
     *
     * @return {@link DocQueryInterpreter}
     * @author Chenbin Wang
     * @date 2022/4/20 14:04
     */
    <T extends DocQueryInterpreter> T getDocQueryInterpreter();

    /**
     * 获取es doc write 解释器
     *
     * @return {@link DocWriteInterpreter}
     * @author Chenbin Wang
     * @date 2022/4/20 14:06
     */
    <T extends DocWriteInterpreter> T getDocWriteInterpreter();

    /**
     * 获取es index 解释器
     *
     * @return {@link T}
     * @throws
     * @author Chenbin Wang
     * @date 2022/4/21 18:35
     */
    <T extends IndexInterpreter> T getIndexInterpreter();

    /**
     * 获取doc 解释器
     *
     * @return {@link T}
     * @author Chenbin Wang
     * @date 2022/4/20 14:14
     */
    <T extends EsInterpreter> T getEsInterpreter();
}
