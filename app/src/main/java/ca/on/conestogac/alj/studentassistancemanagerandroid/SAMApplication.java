package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SAMApplication extends Application {
    private static final String DB_NAME = "db_SAM";
    private static final int DB_VERSION = 1;

    private SQLiteOpenHelper helper;

    @Override
    public void onCreate() {

        helper = new SQLiteOpenHelper(this, DB_NAME, null, DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS tbl_assignments(" +
                        "AssignmentId INTEGER PRIMARY KEY, " +
                        "AssignmentName TEXT NOT NULL, " +
                        "DueDate INTEGER NOT NULL," +
                        "Duration REAL," +
                        "Period INTEGER NOT NULL," +
                        "Complete INTEGER," +
                        "Description TEXT," +
                        "Notified INTEGER)");

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // no-op
            }
        };


        super.onCreate();
    }

    public void addAssignment(String name, long dueDate, double duration,
                              int period, int complete, String desc)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_assignments(AssignmentName, DueDate, Duration, " +
                "Period, Complete, Description, Notified) VALUES ('" + name + "', '" + dueDate + "', '" +
                duration + "', '" + period + "', '" + complete + "', '" + desc + "', 0)");

    }

    public void updateAssignment(int id, String name, long dueDate, double duration,
                                 int period, int complete, String desc)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("UPDATE tbl_assignments SET (AssignmentName = '" + name +
                "', DueDate = '" + dueDate +
                "', Duration = '" + duration +
                "', Period = '" + period +
                "', Complete = '" + complete +
                "', Description = '" + desc + "')" +
                " WHERE AssignmentId = " + id);
    }

    public void updateNotified(int id, boolean notified) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int note;
        if (notified = false) {
            note = 0;
        } else {
            note = 1;
        }
        db.execSQL("UPDATE tble_assignments SET (Notified = '" + note + "') WHERE AssignmentId = " + id);
    }

    public void deleteAssignment(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tbl_assignments WHERE AssignmentId = " + id);
    }

    public Assignment getAssignment (int Id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM tbl_assignments WHERE AssignmentId = " + Id,
                null );

        if (c != null) {
            c.moveToFirst();
        }

        Assignment a = new Assignment();
        a.setId(c.getInt(0));
        a.setName(c.getString(1));
        a.setDueDate(c.getLong(2));
        a.setDuration(c.getLong(3));
        a.setPeriod(c.getLong(4));
        int comp = c.getInt(5);
        if (comp == 1) {
            a.setComplete(true);
        }
        else {
            a.setComplete(false);
        }
        a.setDesc(c.getString(6));
        int note = c.getInt(7);
        if (note == 1) {
            a.setNotified(true);
        }
        else {
            a.setNotified(false);
        }
        c.close();
        return a;
    }

    public List<Assignment> getAllAssignments () {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Assignment> assignments = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tbl_assignments ORDER BY dueDate",
                null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            while (c.getPosition() < c.getCount())
            {
                Assignment a = new Assignment();
                a.setId(c.getInt(0));
                a.setName(c.getString(1));
                a.setDueDate(c.getLong(2));
                a.setDuration(c.getLong(3));
                a.setPeriod(c.getLong(4));
                int comp = c.getInt(5);
                if (comp == 1) {
                    a.setComplete(true);
                }
                else {
                    a.setComplete(false);
                }
                a.setDesc(c.getString(6));
                int note = c.getInt(7);
                if (note == 1) {
                    a.setNotified(true);
                }
                else {
                    a.setNotified(false);
                }
                assignments.add(a);
                c.moveToNext();
            }
        }
        c.close();
        return assignments;
    }

    public void deleteAllAssignments() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tbl_assignments");
    }
}
