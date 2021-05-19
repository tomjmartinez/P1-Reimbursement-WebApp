package tests;

import modelpojos.User;
import org.junit.Assert;
import org.junit.Test;
import services.LoginService;

public class LoginServiceTest {
    LoginService ls = new LoginService();

    @Test
    public void testLogin(){

        User u1 = ls.login("tj@manager.dev", "123456", "manager");
        Assert.assertNotEquals("not working", null, u1);
    }
}
