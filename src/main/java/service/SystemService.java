package service;


public class SystemService {
    public static void autoCalculateInterest() throws Exception {
        ExecuteQuery.executeReader("{CALL Savbase_AutomaticCalculateInterest()}"
                ,null);
    }
}
