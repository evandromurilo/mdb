package mdb; /**
 * Created by Evandro Murilo on 9/27/17.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper {
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/course?user=root&password=root");
        return conn;
    }

    public static ArrayList<HashMap<String, Object>> getAll(String query) throws SQLException {
        ArrayList<HashMap<String, Object>> resultList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = getConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            HashMap<String, Object> row = null;
            ResultSetMetaData metaData = rs.getMetaData();
            Integer columnCount = metaData.getColumnCount();

            while(rs.next()) {
                row = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; ++i) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                resultList.add(row);
            }
        }

        return resultList;
    }
}
