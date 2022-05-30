package org.kelinlin.exception;

import lombok.Data;
import lombok.ToString;
import org.kelinlin.enums.EsExceptionEnums;

/**
 * EsException
 *
 * @author Chenbin Wang
 * @version 1.0
 * @date 2022/4/15 10:53
 */
@Data
@ToString
public class EsException extends RuntimeException {

    /**
     * error code
     */
    private final int code;

    /**
     * error message
     */
    private final String msg;

    public EsException(EsExceptionEnums codeEnum) {
        super(codeEnum.getCode() + " " + codeEnum.getMsg());
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public EsException(int code, EsExceptionEnums codeEnum) {
        super(code + " " + codeEnum.getMsg());
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public EsException(EsExceptionEnums codeEnum, Throwable cause) {
        super(codeEnum.getCode() + " " + codeEnum.getMsg(), cause);
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public EsException(int code, String msg) {
        super(code + " " + msg);
        this.code = code;
        this.msg = msg;
    }
}
