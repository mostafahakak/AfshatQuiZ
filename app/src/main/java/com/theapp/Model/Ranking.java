package com.theapp.afshatquiz.Model;

public class Ranking {

    private String Image;
    private String Name;
    private int Score;

    public Ranking() {
    }

    public Ranking(String image, String name, int score) {
        Image = image;
        Name = name;
        Score = score;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
