package Model.Data;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by hyun on 2015-01-26.
 */
public class User implements Serializable {

    private String id;

    private int score;
    private String grade;
    private int rank;

    public User(String id,int score,int rank)
    {
        this.id = id;
        this.score = score;
        this.rank = rank;
        Log.d("rank", String.valueOf(rank));
        if(0<=score && score <100)
        {
          this.grade = "9급 탐정";
        }
        else if(100<=score && score < 300)
        {
          this.grade = "8급 탐정";
        }
        else if(300<=score && score < 500)
        {
            this.grade = "7급 탐정";
        }
        else if(500<=score && score < 800)
        {
            this.grade = "6급 탐정";
        }
        else if(800<=score && score < 1300)
        {
            this.grade = "5급 탐정";
        }
        else if(1300<=score && score < 1900)
        {
            this.grade = "4급 탐정";
        }
        else if(1900<=score && score < 2800)
        {
            this.grade = "3급 탐정";
        }
        else if(2800<=score && score < 3800)
        {
            this.grade = "2급 탐정";
        }
        else if(3800<=score && score < 5000)
        {
            this.grade = "1급 탐정";
        }
        else if(5000<=score)
        {
            this.grade = "명탐정 삼성이";
        }

    }

    public int getRank() {
        Log.d("getRank", String.valueOf(rank));
        return rank;}
    public void setRank(int rank) {this.rank = rank;}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
