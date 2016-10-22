package Model.Data;

/**
 * Created by hyun on 2015-02-06.
 */
public class CustomTitle {

    private String title;
    private String num;


    public CustomTitle(String title, String num) {
        this.title = title;
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
