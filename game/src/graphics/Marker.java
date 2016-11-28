package graphics;

import components.Field;
import components.Figure;

import java.awt.*;

/**
 * Created by andreyd on 27.11.2016.
 */
public class Marker {
    private int SL = 25, LeftBorder = 2,
            TopBorder = 2;
    private Color MyStyles[] = {Color.black, Color.cyan, Color.blue, Color.red, Color.green,
            Color.yellow, Color.pink, Color.magenta, Color.black};

    private Graphics graphEngine;

    public Marker(Graphics graphEngine) {
        this.graphEngine = graphEngine;
    }

    public void drawBox(int x, int y, int c) {
        if (c == 0) {
            graphEngine.setColor(Color.black);
            graphEngine.fillRect(LeftBorder + x * SL - SL, TopBorder + y * SL - SL, SL, SL);
            graphEngine.drawRect(LeftBorder + x * SL - SL, TopBorder + y * SL - SL, SL, SL);
        } else if (c == 8) {
            graphEngine.setColor(Color.white);
            graphEngine.drawRect(LeftBorder + x * SL - SL + 1, TopBorder + y * SL - SL + 1, SL - 2, SL - 2);
            graphEngine.drawRect(LeftBorder + x * SL - SL + 2, TopBorder + y * SL - SL + 2, SL - 4, SL - 4);
            graphEngine.setColor(Color.black);
            graphEngine.fillRect(LeftBorder + x * SL - SL + 3, TopBorder + y * SL - SL + 3, SL - 6, SL - 6);
        } else {
            graphEngine.setColor(MyStyles[c]);
            graphEngine.fillRect(LeftBorder + x * SL - SL, TopBorder + y * SL - SL, SL, SL);
            graphEngine.setColor(Color.black);
            graphEngine.drawRect(LeftBorder + x * SL - SL, TopBorder + y * SL - SL, SL, SL);
        }
        //		graphEngine.setColor (Color.black);
    }

    public void drawField(Field field) {
        int i, j;
        for (i = 1; i <= field.getDepth(); i++) {
            for (j = 1; j <= field.getWidth(); j++) {
                drawBox(j, i, field.getNewState(j, i));
            }
        }
    }

    public void drawFigure(Figure f) {
        drawBox(f.x, f.y, f.colors[1]);
        drawBox(f.x, f.y + 1, f.colors[2]);
        drawBox(f.x, f.y + 2, f.colors[3]);
    }


    public void hideFigure(Figure f) {
        drawBox(f.x, f.y, 0);
        drawBox(f.x, f.y + 1, 0);
        drawBox(f.x, f.y + 2, 0);
    }

    public void showHelp() {
        graphEngine.setColor(Color.black);
        graphEngine.drawString(" Keys available:", 200 - LeftBorder, 102);
        graphEngine.drawString("Roll Box Up:     ", 200 - LeftBorder, 118);
        graphEngine.drawString("Roll Box Down:   ", 200 - LeftBorder, 128);
        graphEngine.drawString("components.Figure Left:     ", 200 - LeftBorder, 138);
        graphEngine.drawString("components.Figure Right:    ", 200 - LeftBorder, 148);
        graphEngine.drawString("Level High/Low: +/-", 200 - LeftBorder, 158);
        graphEngine.drawString("Drop components.Figure:   space", 200 - LeftBorder, 168);
        graphEngine.drawString("Pause:           P", 200 - LeftBorder, 180);
        graphEngine.drawString("Quit:     Esc or Q", 200 - LeftBorder, 190);
    }

    public void showLevel(int level) {
        graphEngine.setColor(Color.black);
        graphEngine.clearRect(LeftBorder + 100, 390, 100, 20);
        graphEngine.drawString("Level: " + level, LeftBorder + 100, 400);
    }

    public void showScore(long score) {
        graphEngine.setColor(Color.black);
        graphEngine.clearRect(LeftBorder, 390, 100, 20);
        graphEngine.drawString("Score: " + score, LeftBorder, 400);
    }

}
