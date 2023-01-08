package numerical;

import boardgame.Grid;
import java.util.Iterator;

public class NumericalGrid extends boardgame.Grid{
    private String line = null;
    private Character cellWall = '|';
    private static int cellWidth = 4;

    public NumericalGrid(int wide, int tall){
            super(wide,tall);
            makeGridLine();
    }

    private void makeGridLine(){
        line = "";
        for(int j=0; j<getWidth()*cellWidth; j++){
            line +="-";
            }
    }
  @Override
    public String getStringGrid(){
        if(line == null){
            makeGridLine();
        }
        Iterator<String> iter = iterator();
        String toPrint ="";
        int i=0;
        String cell;
        while(iter.hasNext()){
            cell = iter.next();
            toPrint = toPrint + " "+ cell+ cellWall;
            i++;
            if(i == getWidth()){
                toPrint = toPrint + "\n" + line + "\n";
                i = 0;
            }

        }
        return toPrint;
}

/**
 * this is a no-op method right now that is just a
 * placeholder for something that could be useful in 
 * saving/loading games
 * **/
 
    public void parseStringIntoBoard(String toParse){
        int i = toParse.length();
    }
}