package site.itxia.apiservice.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 密码加密/解密工具类.
 * */
@Component
public class PasswordUtil {

    private static Log log = LogFactory.getLog(PasswordUtil.class);

    /**
     * 加密盐
     * */
    private static String salt = null;

    /**
     * 用于从配置文件注入盐值.
     */
    @Value("${itxia.password.salt}")
    private void setSalt(String newSalt) {
        salt = newSalt;
    }

    /**
     * 加密原始密码.
     *
     * @param originPassword 原始密码
     * @return 加密的密码
     */
    public static String encrypt(String originPassword) {
        if (salt == null) {
            salt = "";
            log.warn("加密密码盐为null.");
        }
        var temp = DigestUtils.sha256Hex(originPassword).concat(salt);
        return DigestUtils.sha256Hex(temp);
    }

    /**
     * 校验密码.
     *
     * @param toValidate 需要校验的密码
     * @param encrypted  加密的密码
     * @return 密码是否正确
     */
    public static boolean validate(String toValidate, String encrypted) {
        return encrypt(toValidate).equals(encrypted);
    }

}
