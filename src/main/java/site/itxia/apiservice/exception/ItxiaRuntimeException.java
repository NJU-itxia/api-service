package site.itxia.apiservice.exception;

import site.itxia.apiservice.enumable.ErrorCode;

/**
 * @author zhenxi
 */
public class ItxiaRuntimeException extends RuntimeException {

    private final ErrorCode errorCode;

    public ItxiaRuntimeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return this.errorCode.getErrCode() + ":" + this.errorCode.getErrMessage();
    }
}
