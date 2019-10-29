package site.itxia.apiservice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhenxi
 */
class PasswordUtilTest {

    /**
     * 盐的字段.
     */
    private static final String SALT_FIELD_NAME = "salt";

    /**
     * 加密、解密密码.
     */
    @Test
    void encryptAndValidate() {
        String originPassword = "1sfeewi532g^@5dadw";
        var encrypted = PasswordUtil.encrypt(originPassword);
        assertNotEquals(originPassword, encrypted);
        assertTrue(PasswordUtil.validate(originPassword, encrypted));
        assertFalse(PasswordUtil.validate(originPassword, originPassword));
    }

    /**
     * 验证盐非null、非空格。
     */
    @Test
    void saltShouldNotBeEmpty() throws Exception {
        //利用反射测试private字段
        var saltField = PasswordUtil.class.getDeclaredField(SALT_FIELD_NAME);
        saltField.setAccessible(true);
        var saltValue = (String) saltField.get(new PasswordUtil());
        assertNotNull(saltValue);
        assertFalse(saltValue.isBlank());   //blank: 空或者只有空格
    }
}