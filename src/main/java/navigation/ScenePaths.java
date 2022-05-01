package navigation;

public interface ScenePaths {
    public static String SPLASH = "/views/splash.fxml";
    public static String LOGIN = "/views/login.fxml";
    public static String HOME = "/views/home.fxml";

    public interface TabPagePaths {
        public static String ACCOUNTS = "";
        public static String TRANSACTIONS = "";
        public static String REPORT = "";
        public static String SETTING = "";

        public static interface TransactionsTab {
            public static String MONEY_DEPOSIT = "";
            public static String MONEY_WITHDRAW = "";
        }

        public static interface ReportTab {
            public static String SALES = "";
            public static String OPEN_CLOSE_ACCOUNTS = "";
        }

        public static interface SettingTab {
            public static String ACCOUNT_TYPES = "";
            public static String OTHERS = "";
            public static String EMPLOYEES = "";
        }
    }

    public interface DialogPaths {
        public static String LOGIN = "/views/login.fxml";

    }
}
