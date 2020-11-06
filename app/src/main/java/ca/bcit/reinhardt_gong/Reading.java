package ca.bcit.reinhardt_gong;

public class Reading {
    String serial_number;
    String readingDate;
    String readingTime;
    int systolicReading;
    int diastolicReading;

    public enum condition {
        NORMAL,
        ELEVATED,
        STAGE1,
        STAGE2,
        HYPERTENSIVE
    }

    public Reading() {}

    public Reading(String serial_number,
                   String readingDate, String readingTime, int systolicReading, int diastolicReading) {
        this.serial_number = serial_number;
        this.systolicReading = systolicReading;
        this.readingDate = readingDate;
        this.readingTime = readingTime;
        this.diastolicReading = diastolicReading;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public String getReadingDate() {
        return readingDate;
    }

    public String getReadingTime() {
        return readingTime;
    }


    public int getSystolicReading() {
        return systolicReading;
    }

    public int getDiastolicReading() {
        return diastolicReading;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public void setSystolicReading(int systolicReading) {
        this.systolicReading = systolicReading;
    }

    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }
    public void setReadingTime(String readingDate) {
        this.readingTime = readingDate;
    }

    public void setDiastolicReading(int diastolicReading) {
        this.diastolicReading = diastolicReading;
    }
}
