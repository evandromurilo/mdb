package examples;

import mdb.Model;

import java.sql.SQLException;

public class Course extends Model {
    public static final String table = "courses";
    public static final String[] attributes = {"id", "name"};

    public Course() {
        super();
    }

    public Course(int id) throws SQLException {
        super(id);
    }

    public String toString() {
        return (String) get("name");
    }
}
