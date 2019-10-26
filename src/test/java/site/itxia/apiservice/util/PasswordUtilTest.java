package site.itxia.apiservice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void encryptAndValidate() {
        String originPassword = "1sfeewi532g^@5dadw";
        var encrypted = PasswordUtil.encrypt(originPassword);
        assertNotEquals(originPassword, encrypted);
        assertTrue(PasswordUtil.validate(originPassword, encrypted));
        assertFalse(PasswordUtil.validate(originPassword, originPassword));
    }
}