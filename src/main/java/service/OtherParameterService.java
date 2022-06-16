package service;

import models.OtherParameter;
import models.builder.OtherParameterBuilder;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;

public class OtherParameterService {
    public static OtherParameter get() throws Exception {
        CachedRowSet data = ExecuteQuery.executeReader("{CALL Savbase_LoadOtherParameters()}", null);
        ArrayList<OtherParameter> otherParameters = new ArrayList<>();
        if (!data.next()) return null;
        OtherParameterBuilder otherParameterBuilder = new OtherParameterBuilder()
                .setMinInitDeposit(data.getDouble("SoTienGuiBDToiThieu"))
                .setMinDepositAmount(data.getDouble("SoTienGuiThemToiThieu"))
                .setControlClosingSaving(data.getBoolean("ChucNangDongMoSo"));

        return (otherParameterBuilder.getResult());



    }

    public static void update(double minDepositAmount, double minInitDeposit, boolean controlClosingSaving) throws Exception {
        ExecuteQuery.executeReader("{CALL Savbase_EditOtherParameters(?, ?, ?)}"
                , new Object[]{minDepositAmount, minInitDeposit, controlClosingSaving});
    }
}
