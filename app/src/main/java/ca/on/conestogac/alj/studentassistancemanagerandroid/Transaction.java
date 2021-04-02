package ca.on.conestogac.alj.studentassistancemanagerandroid;

public class Transaction {
    private int id;
    private long date;
    private double amount;
    private int paymentType;
    private int category;
    private String notes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Transaction (int id, long date, double amount, int PT, int cat, String notes) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        paymentType = PT;
        category = cat;
        this.notes = notes;
    }

    public Transaction() {

    }
}
