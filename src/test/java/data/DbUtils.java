package data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    private DbUtils() {
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode() throws SQLException {
        String verificationCode = "";
        val codesSQL = "SELECT * FROM auth_codes ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pass")
        ) {
            val usersCode = runner.query(conn, codesSQL, new BeanHandler<>(User.class));
            verificationCode = usersCode.getCode();
        }
        return new VerificationCode(verificationCode);
    }

    public static void cleanData() throws SQLException {
        val deleteAuthCodes = "DELETE FROM auth_codes";
        val deleteCards = "DELETE FROM cards";
        val deleteUsers = "DELETE FROM users";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pass");
        ) {
            runner.execute(conn, deleteAuthCodes, new BeanHandler<>(User.class));
            runner.execute(conn, deleteCards, new BeanHandler<>(User.class));
            runner.execute(conn, deleteUsers, new BeanHandler<>(User.class));
        }
    }
}
