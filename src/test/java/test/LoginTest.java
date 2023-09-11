package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;


import static data.SQLHelper.cleanAuthCodes;
import static data.SQLHelper.cleanDatabase;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    LoginPage loginPage;

    @AfterEach
    void tearDown(){ cleanAuthCodes();}

    @AfterAll
    static void tearDownAll(){ cleanDatabase(); }

    @BeforeEach
    void setup(){ loginPage = open("http://localhost:9999", LoginPage.class);}

    @Test
    void shouldSuccesfullLogin(){
        var authInfo = DataHelper.getAutInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldErrorNotificationWithExistUser(){
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка");
    }

    @Test
    void shouldErrorNotificationLogin(){
        var authInfo = DataHelper.getAutInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка");
    }


}
