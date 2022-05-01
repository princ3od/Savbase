package navigation;

public interface ScenePaths {
    String SPLASH = "/views/splash.fxml";
    String LOGIN = "/views/login.fxml";
    String HOME = "/views/home.fxml";

    interface TabPagePaths {
        String ACCOUNTS = "/views/accounts/accounts_tab.fxml";
        String TRANSACTIONS = "/views/transactions/transactions_tab.fxml";
        String REPORT = "/views/report/report_tab.fxml";
        String SETTING = "/views/setting/setting_tab.fxml";

        interface TransactionsTab {
            String MONEY_DEPOSIT = "";
            String MONEY_WITHDRAW = "";
        }

        interface ReportTab {
            String SALES = "";
            String OPEN_CLOSE_ACCOUNTS = "";
        }

        interface SettingTab {
            String ACCOUNT_TYPES = "";
            String OTHERS = "";
            String EMPLOYEES = "";
        }
    }

    interface DialogPaths {
        String LOGIN = "/views/login.fxml";

    }
}
