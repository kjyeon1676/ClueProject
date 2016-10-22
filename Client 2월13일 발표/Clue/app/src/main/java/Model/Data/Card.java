package Model.Data;

import java.io.Serializable;

/**
 * Created by hyun on 2015-01-26.
 */
public class Card implements Serializable {

    private String name;

    public Card(String name)
    {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
