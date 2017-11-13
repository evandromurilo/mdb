package mdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelHelper {
    public static ArrayList getAll(Class c) throws SQLException {
        ArrayList<Model> models = new ArrayList<>();

        try {
            String table = (String) (c.getField("table")).get(null);

            ArrayList<HashMap<String, Object>> rs = DBHelper.getAll("SELECT * FROM " + table);

            for (HashMap<String, Object> result : rs) {
                Model model = (Model) c.newInstance();
                model.setAttributes(result);
                models.add(model);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return models;
    }

    public static ArrayList search(Class c, String attribute, String value) throws SQLException {
        ArrayList<Model> models = new ArrayList<>();

        try {
            String table = (String) (c.getField("table")).get(null);

            ArrayList<HashMap<String, Object>> rs = DBHelper.getAll("SELECT * FROM "
                    + table + " WHERE " + attribute + " LIKE '%" + value + "%'");

            for (HashMap<String, Object> result : rs) {
                Model model = (Model) c.newInstance();
                model.setAttributes(result);
                models.add(model);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return models;
    }

    public static Model find(Class c, String attribute, String value) throws SQLException {
        Model model = null;

        try {
            String table = (String) (c.getField("table")).get(null);

            ArrayList<HashMap<String, Object>> rs = DBHelper.getAll("SELECT * FROM "
                    + table + " WHERE " + attribute + " = '" + value + "'");

            HashMap<String, Object> result = rs.get(0);
            if (result != null) {
                model = (Model) c.newInstance();
                model.setAttributes(result);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return model;
    }
}
