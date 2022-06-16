package service;

import utils.LocalSettings;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class ExecuteQuery {
    public static CachedRowSet executeReader(String query, Object[] paramList) throws Exception {
        Connection connection = DriverManager.getConnection(LocalSettings.getConnectionString());
        try {
            CallableStatement preparedStatement = connection.prepareCall(query);
            preparedStatement = (CallableStatement) addParameter(query, preparedStatement, paramList);
            ResultSet data = preparedStatement.executeQuery();
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet rowSet = factory.createCachedRowSet();
            rowSet.populate(data);
            return rowSet;
        } finally {
            connection.close();
        }
    }
    public static Integer executeUpdate(String query, Object[] paramList) throws Exception {
        Connection connection = DriverManager.getConnection(LocalSettings.getConnectionString());
        try{

            CallableStatement preparedStatement = connection.prepareCall(query);
            preparedStatement = (CallableStatement) addParameter(query, preparedStatement, paramList);
            int rowEffected = preparedStatement.executeUpdate();
            return rowEffected;
        }
        finally {
            connection.close();
        }
    }

    private static PreparedStatement addParameter(String query, PreparedStatement statement, Object[] paramList) throws SQLException {
        if (paramList != null) {
            String[] parameters = query.split("\\s+");
            int i = 0;
            for (int j = 0; j < parameters.length; j++) {
                if (parameters[j].contains("?")) {
                    statement.setObject(i + 1, paramList[i]);
                    i++;
                }
            }
        }
        return statement;
    }


}
