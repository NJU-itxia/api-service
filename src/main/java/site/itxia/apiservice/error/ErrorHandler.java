package site.itxia.apiservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import site.itxia.apiservice.dto.ResultWrapper;

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
    public ResultWrapper<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        var result = new ResultWrapper<String>(20, e.getBindingResult().getFieldError().getDefaultMessage());
        return result;
    }

    /**
     * 处理JSON解析错误.
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResultWrapper<String> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return new ResultWrapper<>(20, e.getMessage());
    }
}
