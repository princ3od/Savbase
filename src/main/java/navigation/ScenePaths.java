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
            String SALES = "/views/report/revenue_tab.fxml";
            String OPEN_CLOSE_ACCOUNTS = "/views/report/open_close_tab.fxml";
        }

        interface SettingTab {
            String SAVING_TYPES = "/views/setting/saving_type_tab.fxml";
            String OTHER_PARAMS = "/views/setting/other_parameter_tab.fxml";
            String EMPLOYEES = "/views/setting/employee_tab.fxml";
        }
    }

    interface DialogPaths {
        String VIEW_ACCOUNT = "/views/dialogs/view_account_dialog.fxml";
        String ADD_ACCOUNT = "/views/dialogs/add_account_dialog.fxml";

        String VIEW_EMPLOYEE_INFO ="/views/dialogs/employee_info_dialog.fxml";
        String ADD_NEW_SAVING_TYPE="/views/dialogs/add_saving_type.fxml";

        String ADD_EMPLOYEE = "/views/dialogs/add_employee_dialog.fxml";
        String EDIT_EMPLOYEE = "/views/dialogs/edit_employee_dialog.fxml";
    }
}
