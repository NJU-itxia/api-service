package site.itxia.apiservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.enumable.ErrorCode;

/**
 * @author zhenxi
 * 全局处理抛出的异常.
 */
@ControllerAdvice
public class ErrorHandler {

    /**
     * 处理参数校验产生的错误.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResultWrapper methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return ResultWrapper.wrap(ErrorCode.INVALID_ARGUMENTS, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 处理JSON解析错误.
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResultWrapper httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return ResultWrapper.wrap(ErrorCode.JSON_PARSE_ERROR, e.getMessage());
    }
}
