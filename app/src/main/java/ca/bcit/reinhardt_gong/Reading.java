package ca.bcit.reinhardt_gong;

import android.content.Context;

import androidx.constraintlayout.motion.utils.HyperSpline;

public class Reading {
    String serial_number;
    String readingDate;
    String readingTime;
    float systolicReading;
    float diastolicReading;
    String readingId;
    String condition;

//    public enum Condition {
//        NORMAL,
//        ELEVATED,
//        STAGE1,
//        STAGE2,
//        HYPERTENSIVE
//    }

    public Reading() {}

    public Reading(String readingId, String serial_number,
                   String readingDate, String readingTime, float systolicReading, float diastolicReading) {
        this.readingId = readingId;
        this.serial_number = serial_number;
        this.systolicReading = systolicReading;
        this.readingDate = readingDate;
        this.readingTime = readingTime;
        this.diastolicReading = diastolicReading;
        if ((systolicReading < 120.0) && (diastolicReading < 80.0) ) {
//            this.condition = Condition.NORMAL;
            this.condition = "Normal";
        } else if ((systolicReading <= 129.0 && systolicReading>= 120) && (diastolicReading < 80.0) ) {
//            this.condition = Condition.ELEVATED;
            this.condition = "Elevated";
        } else if ((systolicReading <= 139.0) && (diastolicReading >= 80.0 && diastolicReading<=89)){
//            this.condition = Condition.STAGE1;
            this.condition = "Stage 1";
        } else if ((systolicReading <= 140.0) && (diastolicReading <= 90.0)) {
//            this.condition = Condition.STAGE2;
            this.condition = "Stage 2";
        } else {
//            this.condition = Condition.HYPERTENSIVE;
            this.condition="Stage 3";
        }
    }

    public String getReadingId() {
        return readingId;
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


    public float getSystolicReading() {
        return systolicReading;
    }

    public float getDiastolicReading() {
        return diastolicReading;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public void setSystolicReading(float systolicReading) {
        this.systolicReading = systolicReading;
    }

    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }
    public void setReadingTime(String readingDate) {
        this.readingTime = readingDate;
    }

    public void setDiastolicReading(float diastolicReading) {
        this.diastolicReading = diastolicReading;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
    public String getCondition() {
        return condition;
    }
}
