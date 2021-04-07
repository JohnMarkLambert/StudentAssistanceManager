package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SAMApplication extends Application {
    private static final String DB_NAME = "db_SAM";
    private static final int DB_VERSION = 2;

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
                        "Description TEXT," +
                        "Notified INTEGER)");

                db.execSQL("CREATE TABLE IF NOT EXISTS tbl_transactions(" +
                        "TransactionId INTEGER PRIMARY KEY," +
                        "Date INTEGER NOT NULL," +
                        "Amount REAL NOT NULL," +
                        "PaymentType INTEGER NOT NULL," +
                        "Category INTEGER NOT NULL," +
                        "Notes TEXT)");

                db.execSQL("CREATE TABLE IF NOT EXISTS tbl_payment_type(" +
                        "PaymentTypeId INTEGER PRIMARY KEY," +
                        "TypeName TEXT NOT NULL)");

                db.execSQL("CREATE TABLE IF NOT EXISTS tbl_category(" +
                        "CategoryId INTEGER PRIMARY KEY," +
                        "CategoryName TEXT NOT NULL," +
                        "CategoryGoal REAL NOT NULL," +
                        "Deleted INTEGER)");

                db.execSQL("CREATE TABLE IF NOT EXISTS tbl_records(" +
                        "RecordId INTEGER PRIMARY KEY," +
                        "RecordDate TEXT NOT NULL," +
                        "GoalName TEXT NOT NULL," +
                        "GoalAmount REAL NOT NULL," +
                        "AmountSpent REAL NOT NULL)");

//                List<List<String>> paymentTypes = getPaymentTypes();
//                if (paymentTypes == null ){
//                    addPaymentType("Debit");
//                    addPaymentType("Credit");
//                }

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // no-op
            }
        };


        super.onCreate();
    }

    //Assignment Table Functions

    public void addAssignment(String name, long dueDate, double duration,
                              int period, String desc)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_assignments(AssignmentName, DueDate, Duration, " +
                "Period, Description, Notified) VALUES ('" + name + "', '" + dueDate + "', '" +
                duration + "', '" + period +  "', '" + desc + "', 0)");

    }

    public void updateAssignment(int id, String name, long dueDate, double duration,
                                 int period, int complete, String desc)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("AssignmentName", name);
        cv.put("DueDate", dueDate);
        cv.put("Duration", duration);
        cv.put("Period", period);
        cv.put("Complete", complete);
        cv.put("Description", desc);

        db.update("tbl_assignments", cv, "AssignmentId = ?", new String[]{String.valueOf(id)});
    }

    public void updateNotified(int id, boolean notified) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int note;
        if (notified == false) {
            note = 0;
        } else {
            note = 1;
        }
        db.execSQL("UPDATE tbl_assignments SET Notified = '" + note + "' WHERE AssignmentId = " + id);
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
        a.setDesc(c.getString(5));
        int note = c.getInt(6);
        if (note == 1) {
            a.setNotified(true);
        }
        else {
            a.setNotified(false);
        }
        c.close();
        return a;
    }

    public List<Assignment> getAllAssignments() {
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
                a.setDesc(c.getString(5));
                int note = c.getInt(6);
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

    public Assignment getNextAssignment() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Assignment a = new Assignment();
        Cursor c = db.rawQuery("SELECT * FROM tbl_assignments ORDER BY dueDate",
                null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            a.setId(c.getInt(0));
            a.setName(c.getString(1));
            a.setDueDate(c.getLong(2));
            a.setDuration(c.getLong(3));
            a.setPeriod(c.getLong(4));
            a.setDesc(c.getString(5));
            int note = c.getInt(6);
            if (note == 1) {
                a.setNotified(true);
            } else {
                a.setNotified(false);
            }
        } else {
            a.setName("No Assignments Found");
            a.setDesc("Create an assignment");
        }
        return a;
    }

    public void deleteAllAssignments() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tbl_assignments");
    }

    //Transaction table functions
    public void addTransaction(Long date, double amount, int PT, int cat, String notes) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_transactions(" +
                "Date, Amount, PaymentType, Category, Notes) " +
                "VALUES ('" + date + "', '" + amount + "', '" + PT + "', '" + cat + "', '" +
                notes + "')");
    }

    public void updateTransaction(int id, Long date, double amount, int PT, int cat, String notes) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("Date", date);
        cv.put("Amount", amount);
        cv.put("PaymentType", PT);
        cv.put("Category", cat);
        cv.put("Notes", notes);

        db.update("tbl_transactions", cv, "TransactionId = ?", new String[]{String.valueOf(id)});
    }

    public void deleteTransaction(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("tbl_transactions", "TransactionId = ?", new String[]{String.valueOf(id)});
    }

    public Transaction getTransaction(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM tbl_transactions WHERE TransactionId = " + id, null);
        if (c != null) {
            c.moveToFirst();
        }
        Transaction t = new Transaction(c.getInt(0), c.getLong(1),
                c.getDouble(2), c.getInt(3), c.getInt(4),
                c.getString(5));
        c.close();
        return t;
    }

    public Transaction getLastTransaction() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM tbl_transactions", null);
        Transaction t = new Transaction();
        if (c.getCount() > 0) {
            c.moveToLast();
            t = new Transaction(c.getInt(0), c.getLong(1),
                    c.getDouble(2), c.getInt(3), c.getInt(4),
                    c.getString(5));
        } else {
            t.setAmount(0.00);
            t.setNotes("No Transactions");
        }
        c.close();
        return t;
    }

    public List<Transaction> getAllTransactions() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Transaction> transactions = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tbl_transactions ORDER BY Date DESC", null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            while (c.getPosition() < c.getCount()) {
                Transaction t = new Transaction(c.getInt(0), c.getLong(1),
                        c.getDouble(2), c.getInt(3), c.getInt(4),
                        c.getString(5));
                transactions.add(t);
                c.moveToNext();
            }
        }
        c.close();
        return transactions;
    }

    public void deleteAllTransactions() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tbl_transactions");
    }

    //Category Table functions
    public void createCategory(String name, double goal) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_category(CategoryName, CategoryGoal) Values('" +
                name + "', '" + goal + "')");
    }

    public void updateCategory(int id, String name, double goal) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CategoryName", name);
        cv.put("CategoryGoal", goal);
        db.update("tbl_category", cv, "CategoryId = ?", new String[]{String.valueOf(id)});
    }

    public void deleteCategory(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Deleted", 1);
        db.update("tbl_category", cv, "CategoryId = ?", new String[]{String.valueOf(id)});
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM tbl_category WHERE CategoryId = " + id, null);
        if (c != null) {
            c.moveToFirst();
        }
        Category cat = new Category(c.getInt(0), c.getString(1), c.getDouble(2));
        c.close();
        return cat;
    }

    public List<Category> getAllCategory() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Category> categories = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tbl_category WHERE Deleted = 0", null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            while (c.getPosition() < c.getCount()) {
                Category cat = new Category(c.getInt(0), c.getString(1),
                        c.getDouble(2));
                categories.add(cat);
                c.moveToNext();
            }
        }
        c.close();
        return categories;
    }

    public void deleteAllCategory() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tbl_category");
    }

    //Payment type table functions
    public void addPaymentType(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_payment_type(TypeName) Values('" + name + "')");
    }

    public void updatePaymentType(int id, String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TypeName", name);
        db.update("tbl_payment_type", cv, "PaymentTypeId = ?", new String[]{String.valueOf(id)});
    }

    public String getPaymentType(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM tbl_payment_type WHERE PaymentTypeId = " + id, null);
        c.moveToFirst();
        return c.getString(1);
    }

    public List<List<String>> getPaymentTypes() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<List<String>> array = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tbl_payment_type", null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            while (c.getPosition() < c.getCount()) {
                List<String> PT = new ArrayList<>();
                PT.add(c.getString(0));
                PT.add(c.getString(1));
                array.add(PT);
                c.moveToNext();
            }
        }
        c.close();
        return array;
    }

    public void deleteAllPaymentType () {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tbl_payment_type");
    }

    //Record Table functions
    public void addRecord(String date, String goalName, Double goalAmount, Double amountSpent){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_records (RecordDate, GoalName, GoalAmount, AmountSpent) " +
                "VALUES('" + date + "', '" + goalName + "', '" + goalAmount + "', '" + amountSpent +
                "')");
    }

    public List<Record> getMonthlyRecord(String date){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Record> r = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tbl_records", null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            while (c.getPosition() < c.getCount()) {
                String check = c.getString(1);
                if (check.equals(date)) {
                    Record rec = new Record(c.getString(1), c.getString(2),
                            c.getDouble(3), c.getDouble(4));
                    r.add(rec);

                }
                c.moveToNext();
            }
        }
        return r;
    }

    public List<Record> getAllRecords(){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Record> r = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tbl_records", null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            while (c.getPosition() < c.getCount()) {
                Record rec = new Record(c.getString(1), c.getString(2),
                        c.getDouble(3), c.getDouble(4));
                r.add(rec);
                c.moveToNext();
            }
        }
        return r;
    }

    public void deleteAllRecords(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tbl_records");
    }
}
