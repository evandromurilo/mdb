package mdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private String table;
    private HashMap<String, Object> attributes;
    private String[] attribute_list;
    private Class c;

    public Model() {
        c = getClass();

        try {
            this.table = (String) c.getField("table").get(null);
            this.attribute_list = (String[]) c.getField("attributes").get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        this.attributes = new HashMap<>();

        for (String key : attribute_list) {
            this.attributes.put(key, null);
        }
    }

    public Model(int id) throws SQLException {
        this();

        ArrayList<HashMap<String, Object>> rs = DBHelper.getAll("SELECT *" +
                " FROM " + table + " WHERE id = " + id);

        if (rs.size() > 0) {
            this.attributes = rs.get(0);
        } else {
            throw new SQLException();
        }
    }

    public void setAttributes(HashMap<String, Object> map) {
        this.attributes = map;
    }

    public void set(String key, Object value) {
        attributes.put(key, value);
    }

    public Object get(String key) {
        return attributes.get(key);
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    private String generateInsertQuery() {
        String query = "INSERT INTO " + table + "(";

        for (int i = 1; i < attribute_list.length; ++i) {
            query += attribute_list[i];

            if (i + 1 != attribute_list.length) query += ", ";
        }

        query += ") values (";

        for (int i = 1; i < attribute_list.length; ++i) {
            query += "?";
            if (i + 1 != attribute_list.length) query += ", ";
        }

        query += ")";

        System.out.println(query);
        return query;
    }

    private String generateUpdateQuery() {
        String query = "UPDATE " + table +
                " SET ";

        for (int i = 1; i < attribute_list.length; ++i) {
            query += attribute_list[i];
            query += " = ";
            query += "?";

            if (i + 1 != attribute_list.length) query += ", ";
        }

        query += " WHERE id = " + get("id");

        System.out.println(query);
        return query;
    }

    private void insert() throws SQLException {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(generateInsertQuery())) {

            for (int i = 1; i < attribute_list.length; ++i) {
                String key = attribute_list[i];
                Object value = attributes.get(key);

                System.out.println(i + ": " + key + ", " + value);

                if (value.getClass() == Integer.class) {
                    pstmt.setInt(i, (Integer) value);
                } else if (value.getClass() == Boolean.class) {
                    if ((boolean) value) pstmt.setInt(i, 1);
                    else pstmt.setInt(i, 0);
                } else {
                    pstmt.setString(i, value.toString());
                }
            }

            pstmt.executeUpdate();

            try (PreparedStatement stmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                 ResultSet rs = stmt.executeQuery()) {

                rs.next();
                set("id", rs.getInt(1));
            }
        }
    }

    private void update() throws SQLException {
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(generateUpdateQuery())) {

            for (int i = 1; i < attribute_list.length; ++i) {
                String key = attribute_list[i];
                Object value = attributes.get(key);

                System.out.println(i + ": " + key + ", " + value);

                if (value.getClass() == Integer.class) {
                    pstmt.setInt(i, (Integer) value);
                } else if (value.getClass() == Boolean.class) {
                    if ((boolean) value) pstmt.setInt(i, 1);
                    else pstmt.setInt(i, 0);
                } else {
                    pstmt.setString(i, value.toString());
                }
            }

            pstmt.executeUpdate();
        }
    }

    public void save() throws SQLException {
        if (get("id") == null) insert();
        else update();
    }
}