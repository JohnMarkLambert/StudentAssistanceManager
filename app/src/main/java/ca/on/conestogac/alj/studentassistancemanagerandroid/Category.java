package ca.on.conestogac.alj.studentassistancemanagerandroid;

public class Category {
    private int id;
    private String name;
    private double goal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public Category (int id, String name, double goal) {
        this.id = id;
        this.name = name;
        this.goal = goal;
    }
}
