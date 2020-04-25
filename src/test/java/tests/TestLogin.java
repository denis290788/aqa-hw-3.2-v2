package tests;

import pages.LoginPage;
import data.DataHelper;
import data.DbUtils;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class TestLogin {

    @AfterAll
    static void deleteData() throws SQLException {
        DbUtils.cleanData();
    }

    @Test
    public void testLoginWithVerificationCode() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DbUtils.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode.getCode());
        dashboardPage.dashboardPageVisible();
    }
}
