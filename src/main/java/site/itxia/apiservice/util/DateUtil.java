package site.itxia.apiservice.util;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhenxi
 */
@Component
public class DateUtil {

    /**
     * 获取当前unix时间.
     *
     * @return 当前unix时间.(秒)
     */
    public static int getCurrentUnixTime() {
        var current = new Date();
        long time = current.getTime();
        return (int) (time / 1000L);
    }

}
