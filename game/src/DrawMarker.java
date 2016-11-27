import java.awt.*;

/**
 * Created by andreyd on 27.11.2016.
 */
public class DrawMarker {
    private int SL = 25, LeftBorder = 2,
            TopBorder = 2;
    private Color MyStyles[] = {Color.black, Color.cyan, Color.blue, Color.red, Color.green,
            Color.yellow, Color.pink, Color.magenta, Color.black};

    private Graphics graphEngine;

    public DrawMarker(Graphics graphEngine) {
        this.graphEngine = graphEngine;
    }

    void DrawBox(int x, int y, int c) {
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

    void DrawField(Field field) {
        int i, j;
        for (i = 1; i <= field.getDepth(); i++) {
            for (j = 1; j <= field.getWidth(); j++) {
                DrawBox(j, i, field.getNewState(j, i));
            }
        }
    }

    void DrawFigure(Figure f) {
        DrawBox(f.x, f.y, f.c[1]);
        DrawBox(f.x, f.y + 1, f.c[2]);
        DrawBox(f.x, f.y + 2, f.c[3]);
    }

    long  DropFigure(Figure f, Field field, int level, long dscore) {
        int zz;
        if (f.y < field.getDepth() - 2) {
            zz = field.getDepth();
            while (field.getNewState(f.x, zz) > 0) zz--;
            dscore = (((level + 1) * (field.getDepth() * 2 - f.y - zz) * 2) % 5) * 5;
            f.y = zz - 2;
        }
        return dscore;
    }

    void HideFigure(Figure f) {
        DrawBox(f.x, f.y, 0);
        DrawBox(f.x, f.y + 1, 0);
        DrawBox(f.x, f.y + 2, 0);
    }

    void ShowHelp() {
        graphEngine.setColor(Color.black);
        graphEngine.drawString(" Keys available:", 200 - LeftBorder, 102);
        graphEngine.drawString("Roll Box Up:     ", 200 - LeftBorder, 118);
        graphEngine.drawString("Roll Box Down:   ", 200 - LeftBorder, 128);
        graphEngine.drawString("Figure Left:     ", 200 - LeftBorder, 138);
        graphEngine.drawString("Figure Right:    ", 200 - LeftBorder, 148);
        graphEngine.drawString("Level High/Low: +/-", 200 - LeftBorder, 158);
        graphEngine.drawString("Drop Figure:   space", 200 - LeftBorder, 168);
        graphEngine.drawString("Pause:           P", 200 - LeftBorder, 180);
        graphEngine.drawString("Quit:     Esc or Q", 200 - LeftBorder, 190);
    }

    void ShowLevel(int level) {
        graphEngine.setColor(Color.black);
        graphEngine.clearRect(LeftBorder + 100, 390, 100, 20);
        graphEngine.drawString("Level: " + level, LeftBorder + 100, 400);
    }

    void ShowScore(long score) {
        graphEngine.setColor(Color.black);
        graphEngine.clearRect(LeftBorder, 390, 100, 20);
        graphEngine.drawString("Score: " + score, LeftBorder, 400);
    }

    void PasteFigure(Figure f, Field field) {
        field.setNewState(f.x, f.y, f.c[1]);
        field.setNewState(f.x, f.y + 1, f.c[2]);
        field.setNewState(f.x, f.y + 2, f.c[3]);
    }

}
