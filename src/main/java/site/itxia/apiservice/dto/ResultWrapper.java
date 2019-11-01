package site.itxia.apiservice.dto;

import lombok.Data;
import site.itxia.apiservice.enumable.ErrorCode;

/**
 * @author zhenxi
 * 包装返回的json.
 */
@Data
public class ResultWrapper<T> {

    private int errorCode;
    private String errorMessage;
    private T payload;

    /**
     * 包装返回payload.
     * 自动根据错误码添加上错误信息.
     */
    private ResultWrapper(ErrorCode errorCode, T payload) {
        this.errorCode = errorCode.getErrCode();
        this.errorMessage = errorCode.getErrMessage();
        this.payload = payload;
    }

    /**
     * 包装成功的返回值.
     * 返回代码默认为0.
     *
     * @param payload 响应中的payload.
     * @return 响应body.
     */
    public static <S> ResultWrapper wrapSuccess(S payload) {
        return new ResultWrapper<S>(ErrorCode.SUCCESS, payload);
    }

    /**
     * 包装返回代码和返回值.
     *
     * @param errorCode 错误码.
     * @param payload   响应中的payload.
     * @return 响应body.
     */
    public static <S> ResultWrapper wrap(ErrorCode errorCode, S payload) {
        return new ResultWrapper<S>(errorCode, payload);
    }

    /**
     * 仅包装返回代码.
     *
     * @param errorCode 错误码.
     * @return 响应body.
     */
    public static <S> ResultWrapper wrap(ErrorCode errorCode) {
        return new ResultWrapper<S>(errorCode, null);
    }
}
