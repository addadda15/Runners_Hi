package com.example.runnershi_final;

import java.util.ArrayList;

public class Course {
    ArrayList<Double> latList;
    ArrayList<Double> lngList;

    Course(){}

    public Course(ArrayList<Double> latList, ArrayList<Double> lngList) {
        this.latList = latList;
        this.lngList = lngList;
    }
    public Course(ArrayList<Double> latList) {
        this.latList = latList;
        this.lngList = new ArrayList<>();
    }

    public ArrayList<Double> getLatList() {
        return latList;
    }

    public void setLatList(ArrayList<Double> latList) {
        this.latList = latList;
    }

    public ArrayList<Double> getLngList() {
        return lngList;
    }

    public void setLngList(ArrayList<Double> lngList) {
        this.lngList = lngList;
    }
}
