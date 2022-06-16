package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Account;
import models.OpenCloseReport;
import models.RevenueReport;
import models.builder.AccountBuilder;
import models.builder.OpenCloseReportBuilder;
import models.builder.RevenueReportBuilder;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;

public class ReportService {
    public static ObservableList<RevenueReport> getRevenues() throws Exception {
        CachedRowSet data = ExecuteQuery.executeReader("{CALL Savbase_LoadRevenueData()}", null);
        ArrayList<RevenueReport> revenueReports = new ArrayList<>();
        while (data.next()) {
            RevenueReportBuilder reportBuilder = new RevenueReportBuilder().setDate(data.getDate("Ngay"))
                    .setSavingType(data.getString("TenLoaiSTK"))
                    .setTotalRevenue(data.getDouble("TongThu"))
                    .setTotalCost(data.getDouble("TongChi"))
                    .setDifference(data.getDouble("ChenhLech"));
            revenueReports.add(reportBuilder.getResult());
        }
        ObservableList<RevenueReport> result = FXCollections.observableArrayList(revenueReports);
        return result;

    }

    public static ObservableList<OpenCloseReport> getOpenCloses() throws Exception {
        CachedRowSet data = ExecuteQuery.executeReader("{CALL Savbase_LoadRevenueData()}", null);
        ArrayList<OpenCloseReport> openCloseReports = new ArrayList<>();

        while (data.next()) {
            OpenCloseReportBuilder reportBuilder = new OpenCloseReportBuilder().setDate(data.getDate("Ngay"))
                    .setSavingType(data.getString("TenLoaiSTK"))
                    .setOpen(data.getInt("SoSoMo"))
                    .setClose(data.getInt("SoSoDong"))
                    .setDifference(data.getInt("ChenhLech"));

        }
        ObservableList<OpenCloseReport> result = FXCollections.observableArrayList(openCloseReports);
        return result;

    }
}
