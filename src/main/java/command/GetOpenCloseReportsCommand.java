package command;

import constants.Constants;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import models.OpenCloseReport;
import service.ReportService;

public class GetOpenCloseReportsCommand extends BaseCommand  {
    public GetOpenCloseReportsCommand() {
        task = new Task<ObservableList<OpenCloseReport>>() {
            @Override
            protected ObservableList<OpenCloseReport> call() throws Exception {
                Thread.sleep(Constants.DEFAULT_COMMAND_DELAY_TIME);
                ObservableList<OpenCloseReport> reports = ReportService.getOpenCloses();
                return reports;
            }
        };
    }
}
