package ca.on.conestogac.alj.studentassistancemanagerandroid;

public class Record {
    private String date;
    private String goalName;
    private double goalAmount;
    private double amountSpent;
    private double difference;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(Double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(Double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public Record(String date, String gName, double gAmount, double amount){
        this.date = date;
        goalName = gName;
        goalAmount = gAmount;
        amountSpent = amount;
        difference = amount - gAmount;
    }
}
