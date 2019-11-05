package site.itxia.apiservice.enumable;

/**
 * 枚举错误代码.
 * 只显示概要信息，详细信息会加在payload中.
 */
public enum ErrorCode {
    SUCCESS(0, "请求成功"),
    INVALID_ARGUMENTS(10, "请求参数无效"),
    UNAUTHORIZED(11, "无权限执行"),
    JSON_PARSE_ERROR(12, "JSON解析错误"),
    MEMBER_ALREADY_EXISTS(13, "成员已存在"),
    ORDER_ALREADY_ACCEPTED(14, "预约单早已被接受");

    private int errCode;
    private String errMessage;

    private ErrorCode(int errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
