package com.lopez.collegeassistant.SchoolSchedule;

import java.util.Date;

/**
 * Created by JacoboAdrian on 1/31/2016.
 */
public class SchoolSubject {
    private String mSubjectName, mColorCode, mClassDays;
    private int mSubjectID;
    private Date mStartDate, mEndDate, mStartHour, mEndHour;

    public void setmSubjectID(int id){
        mSubjectID = id;
    }

    public int getmSubjectID(){
        return mSubjectID;
    }

    public void setmSubjectName(String name){
        mSubjectName = name;
    }

    public String getmSubjectName(){
        return mSubjectName;
    }

    public void setmColorCode(String colorCode){
        mColorCode = colorCode;
    }

    public String getmColorCode(){
        return mColorCode;
    }

    public void setmClassDays(String classDays){
        mClassDays = classDays;
    }

    public String getmClassDays(){
        return mClassDays;
    }

    public void setmStartDate(Date startDate){
        mStartDate = startDate;
    }

    public Date getmStartDate(){
        return mStartDate;
    }

    public void setmEndDate(Date endDate){
        mEndDate = endDate;
    }

    public Date getmEndDate(){
        return mEndDate;
    }

    public void setmStartHour(Date startHour){
        mStartHour = startHour;
    }

    public Date getmStartHour(){
        return mStartHour;
    }

    public void setmEndHour(Date endHour){
        mEndHour = endHour;
    }

    public Date getmEndHour(){
        return mEndHour;
    }

}
