package command;

import constants.Constants;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import models.RevenueReport;
import service.ReportService;

public class GetRevenueReportsCommand extends BaseCommand  {
    public GetRevenueReportsCommand() {
        task = new Task<ObservableList<RevenueReport>>() {
            @Override
            protected ObservableList<RevenueReport> call() throws Exception {
                Thread.sleep(Constants.DEFAULT_COMMAND_DELAY_TIME);
                ObservableList<RevenueReport> reports = ReportService.getRevenues();
                return reports;
            }
        };
    }
}
