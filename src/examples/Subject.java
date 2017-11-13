package examples;

import mdb.Model;

import java.sql.SQLException;

public class Subject extends Model {
    public static final String table = "subjects";
    public static final String[] attributes = {"id", "name", "course_id", "teacher_id"};

    public Subject() {
        super();
    }

    public Subject(int id) throws SQLException {
        super(id);
    }

    public Course getCourse() {
        try {
            return new Course((Integer) get("course_id"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Teacher getTeacher() {
        try {
            return new Teacher((Integer) get("course_id"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return (String) get("name");
    }
}

