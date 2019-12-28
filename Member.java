package com.example.mygame;

public class Member {
    private String name, password;
    private long highscore;

    public Member(String name, String password){
        this.name = name;
        this.password = password;
        this.highscore = 0;
    }

    public Member(String name, String password, long highscore){
        this.name = name;
        this.password = password;
        this.highscore = highscore;
    }

    public int Highscore2(){return ((int)highscore);}

    public long getHighscore(){return highscore;}

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setHighscore(long highscore) {
        this.highscore = highscore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Member)
            return ((Member)obj).getName().equals(this.name);
        return false;
    }
}
