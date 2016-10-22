package Model.Data;

/**
 * Created by hyun on 2015-02-09.
 */
public class Coordinate {
    private boolean[][] coordinate;


    public Coordinate()
    {
        coordinate = new boolean[][] {
                           {false,false,false,true,false,false,false,true,false,false,false},
                           {false,false,false,true,false,false,false,true,false,false,false},
                           {false,true, false,true, false,true,false,true,false,true,false},
                           {true,true,true,true,true,true,true,true,true,true,true},
                           {false,false,false,true,false,false,false,true,false,false,false},
                           {false,false,true,true,true,false,false,true,true,false,false},
                           {false,false,false,true,false,false,false,true,false,false,false},
                           {true,true,true,true,true,true,true,true,true,true,true},
                           {false,true,false,true,false,true,false,true,false,true,false},
                           {false,false,false,true,false,false,false,true,false,false,false},
                           {false,false,false,true,false,false,false,true,false,false,false}
                        };


    }

    public boolean getCoordinate(int x, int y)
    {
        if(x<0 || x>10)
            return false;
        if(y<0 || y>10)
            return false;

        return coordinate[x][y];
    }
}
