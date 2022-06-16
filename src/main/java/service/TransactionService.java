package service;

import java.sql.Date;

public class TransactionService {
    public static void create(int type, int accountId, int employeeId, Date date, double amount) throws Exception {
        ExecuteQuery.executeReader("{CALL Savbase_InsertNewTransaction(?, ?, ?, ?, ?)}"
                , new Object[]{type, accountId, employeeId, date, amount});
    }

    public static void withdrawInterest(int accountId) throws Exception {
        ExecuteQuery.executeReader("{CALL Savbase_WithdrawInterest(?)}"
                , new Object[]{accountId});
    }
}
