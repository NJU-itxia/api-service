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
     * 包装失败的请求，自动根据错误码生成错误信息.
     */
    public ResultWrapper(int errCode, T payload) {
        this.errCode = errCode;
        this.errMessage = errCodeToMessage(errCode);
        this.payload = payload;
    }

    /**
     * 包装成功的请求，自动添加错误码 0 .
     */
    public ResultWrapper(T payload) {
        this(0, payload);
    }

    public static <S> ResultWrapper wrapSuccess(S payload) {
        return new ResultWrapper<S>(payload);
    }

    public static <S> ResultWrapper wrapFail(int errCode, S payload) {
        return new ResultWrapper<S>(errCode, payload);
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
