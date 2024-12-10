package pthttm.retail.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.Objects;

public class CustomPasswordEncoder extends BCryptPasswordEncoder {
    private static final String SUFFIX = "@ptithcm";
    @Override
    public String encode(CharSequence rawPassword) {
        String modifiedPassword = rawPassword + SUFFIX;
        return super.encode(modifiedPassword); // Mã hóa mật khẩu sau khi thêm chuỗi
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String modifiedPassword = rawPassword + SUFFIX;
        return super.matches(modifiedPassword, encodedPassword);
    }
}
