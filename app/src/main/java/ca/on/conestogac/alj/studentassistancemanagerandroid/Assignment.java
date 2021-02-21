package ca.on.conestogac.alj.studentassistancemanagerandroid;

public class Assignment {
    private int id;
    private String name;
    private long dueDate;
    private long duration;
    private long period;
    private boolean complete;
    private String desc;


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

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Assignment (int id, String name, long dueDate, long period, boolean complete, String desc)
    {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.period = period;
        this.complete = complete;
        this.desc = desc;
    }

    public Assignment() {

    }
}
