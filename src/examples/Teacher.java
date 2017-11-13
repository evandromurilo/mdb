package examples;

import mdb.Model;

import java.sql.SQLException;

public class Teacher extends Model {
    public static final String table = "teachers";
    public static final String[] attributes = {"id", "name", "age"};

    public Teacher() {
        super();
    }

    public Teacher(int id) throws SQLException {
        super(id);
    }
}
