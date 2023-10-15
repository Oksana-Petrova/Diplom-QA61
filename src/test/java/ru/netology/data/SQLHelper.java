package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.DriverManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLHelper {
    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.user");
    private static String password = System.getProperty("db.password");

    @SneakyThrows
    public static void clearAllData(){
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(url, user, password);
        runner.update(conn, "DELETE FROM credit_request_entity;");
        runner.update(conn, "DELETE FROM order_entity;");
        runner.update(conn, "DELETE FROM payment_entity;");
    }
    @SneakyThrows
    public static void checkPaymentStatus(StatusCard expectedStatus){
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(url, user, password);
        var paymentDataSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        var payment = runner.query(conn, paymentDataSQL, new ScalarHandler<String>());
        assertEquals(expectedStatus.getStatusName(), payment);
    }
    @SneakyThrows
    public static void checkCreditStatus(StatusCard expectedStatus){
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(url, user, password);
        var creditDataSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        var credit = runner.query(conn, creditDataSQL, new ScalarHandler<String>());
        assertEquals(expectedStatus.getStatusName(), credit);
    }

    @SneakyThrows
    public static void checkAmountOfTravelInDB(int expectedAmount){
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(url, user, password);
        var paymentDataSQL = "SELECT amount FROM payment_entity ORDER BY created DESC LIMIT 1;";
        var amount = runner.query(conn, paymentDataSQL, new ScalarHandler<Integer>());
        assertEquals(expectedAmount, amount);
    }
}