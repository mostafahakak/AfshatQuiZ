package com.theapp.afshatquiz.Model;

public class ChallengeInfo {
    private String HisName,MyName,HisScore,MyScore,Wait,HisImage;

    public ChallengeInfo() {
    }

    public ChallengeInfo(String hisName, String myName, String hisScore, String myScore, String wait, String hisImage) {
        HisName = hisName;
        MyName = myName;
        HisScore = hisScore;
        MyScore = myScore;
        Wait = wait;
        HisImage = hisImage;
    }

    public String getHisName() {
        return HisName;
    }

    public void setHisName(String hisName) {
        HisName = hisName;
    }

    public String getMyName() {
        return MyName;
    }

    public void setMyName(String myName) {
        MyName = myName;
    }

    public String getHisScore() {
        return HisScore;
    }

    public void setHisScore(String hisScore) {
        HisScore = hisScore;
    }

    public String getMyScore() {
        return MyScore;
    }

    public void setMyScore(String myScore) {
        MyScore = myScore;
    }

    public String getWait() {
        return Wait;
    }

    public void setWait(String wait) {
        Wait = wait;
    }

    public String getHisImage() {
        return HisImage;
    }

    public void setHisImage(String hisImage) {
        HisImage = hisImage;
    }
}
