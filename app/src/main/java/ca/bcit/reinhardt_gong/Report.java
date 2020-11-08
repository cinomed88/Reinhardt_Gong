package ca.bcit.reinhardt_gong;

public class Report {
    private String DSN;
    private String systolic;
    private String diastolic;
    private String avgCondition;
    private String month;

    public Report(){}

    public String getDSN() {
        return DSN;
    }

    public void setDSN(String DSN) {
        this.DSN = DSN;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getAvgCondition() {
        return avgCondition;
    }

    public void setAvgCondition(String avgCondition) {
        this.avgCondition = avgCondition;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
