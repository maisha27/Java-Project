package com.example.testcase4;

public class Police_Judges {

    private String Judge_Id;
    private String Judge_Name;
    private String  Expertise;
    private String Case_Id;

    public Police_Judges(String judge_Id, String judge_Name, String expertise, String case_Id) {
        Judge_Id = judge_Id;
        Judge_Name = judge_Name;
        Expertise = expertise;
        Case_Id = case_Id;
    }

    public  String getJudge_Id() {
        return Judge_Id;
    }

    public void setJudge_Id(String judge_Id) {
        Judge_Id = judge_Id;
    }

    public String getJudge_Name() {
        return Judge_Name;
    }

    public void setJudge_Name(String judge_Name) {
        Judge_Name = judge_Name;
    }

    public String getExpertise() {
        return Expertise;
    }

    public void setExpertise(String expertise) {
        Expertise = expertise;
    }

    public String getCase_Id() {
        return Case_Id;
    }

    public void setCase_Id(String case_Id) {
        Case_Id = case_Id;
    }
}
