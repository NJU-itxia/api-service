package site.itxia.apiservice.dto;

import lombok.Data;

/**
 * @author zhenxi
 * 包装返回的json.
 */
@Data
public class ResultWrapper<T> {

    private int errCode;
    private String errMessage;
    private T payload;

    /**
     * 包装返回payload.
     * 自动根据错误码添加上错误信息.
     */
    private ResultWrapper(int errCode, T payload) {
        this.errCode = errCode;
        this.errMessage = errCodeToMessage(errCode);
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
        return new ResultWrapper<S>(0, payload);
    }

    /**
     * 包装返回代码和返回值.
     *
     * @param errCode 错误码.
     * @param payload 响应中的payload.
     * @return 响应body.
     */
    public static <S> ResultWrapper wrap(int errCode, S payload) {
        return new ResultWrapper<S>(errCode, payload);
    }

    /**
     * 仅包装返回代码.
     *
     * @param errCode 错误码.
     * @return 响应body.
     */
    public static <S> ResultWrapper wrap(int errCode) {
        return new ResultWrapper<S>(errCode, null);
    }


    /**
     * 根据自定义错误码，返回对应的错误信息.
     * 错误信息详见API文档.
     *
     * @param errCode 自定义错误🐎
     * @return 对应的错误消息
     */
    private String errCodeToMessage(int errCode) {
        switch (errCode) {
            case 0:
                return "处理成功";
            case 1000:
                return "密码不正确";
            default:
                return "未知代码";
        }
    }
}
