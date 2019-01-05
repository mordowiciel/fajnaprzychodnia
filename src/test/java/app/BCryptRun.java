package app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BCryptRun {

    @Test
    public void generatePassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "test123";
        String encoded = passwordEncoder.encode(password);
        System.out.println(encoded);
    }
}
