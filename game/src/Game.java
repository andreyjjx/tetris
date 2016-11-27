import java.applet.Applet;
import java.awt.*;
import java.util.*;

/**
 * Created by andreyd on 27.11.2016.
 */
public class Game {
    private int maxLevel;
    private int level;
    private int timeShift;
    private int minTimeShift;

    private Field field;
    private Graphics graphEngine;

    public Game(Graphics graphEngine, Field field) {
        this.field = field;
        this.graphEngine = graphEngine;
    }

    public void init() {

       /* Level = 0;
        Score = 0;
        j = 0;
        k = 0;
        _gr.setColor(Color.black);
        requestFocus();*/
    }
}
