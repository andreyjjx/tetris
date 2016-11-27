import java.applet.*;
import java.awt.*;
import java.util.*;


public class Columns extends Applet implements Runnable {
    static final int
            MaxLevel = 7,
            TimeShift = 250,
            FigToDrop = 33,
            MinTimeShift = 200;

    int Level, i, j, k, ch;
    long Score, DScore, tc;
    Figure Fig;
    boolean NoChanges = true, KeyPressed = false;
    Graphics _gr;

    Thread thr = null;
    Field field = new Field(7, 15);
    DrawMarker dMarker;

    void CheckNeighbours(int a, int b, int c, int d, int i, int j) {
        if (field.getNewState(j, i) == field.getNewState(a, b) &&
                field.getNewState(j, i) == field.getNewState(c, d)) {

            field.setOldState(a, b, 0);
            dMarker.DrawBox(a, b, 8);

            field.setOldState(j, i, 0);
            dMarker.DrawBox(j, i, 8);

            field.setOldState(c, d, 0);
            dMarker.DrawBox(c, d, 8);

            NoChanges = false;
            Score += (Level + 1) * 10;
            k++;
        }
    }

    void Delay(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
        }
    }

    public void init() {
        /*Fnew = new int[field.getWidth() + 2][field.getDepth() + 2];
        Fold = new int[field.getWidth() + 2][field.getDepth() + 2];*/
        _gr = getGraphics();
        dMarker = new DrawMarker(_gr);
    }

    public boolean keyDown(Event e, int k) {
        KeyPressed = true;
        ch = e.key;
        return true;
    }

    public boolean lostFocus(Event e, Object w) {
        KeyPressed = true;
        ch = 'P';
        return true;
    }

    public void paint(Graphics g) {
        //		ShowHelp(g);

        g.setColor(Color.black);

        dMarker.ShowLevel(Level);
        dMarker.ShowScore(Score);
        dMarker.DrawField(field);
        dMarker.DrawFigure(Fig);
        requestFocus();
    }

    public void run() {
        Level = 0;
        Score = 0;
        j = 0;
        k = 0;
        _gr.setColor(Color.black);
        requestFocus();

        do {
            tc = System.currentTimeMillis();
            new Figure(field.getWidth());
            dMarker.DrawFigure(Fig);
            while ((Fig.y < field.getDepth() - 2) && (field.getNewState(Fig.x, Fig.y + 3) == 0)) {
                if ((int) (System.currentTimeMillis() - tc) > (MaxLevel - Level) * TimeShift + MinTimeShift) {
                    tc = System.currentTimeMillis();
                    dMarker.HideFigure(Fig);
                    Fig.y++;
                    dMarker.DrawFigure(Fig);
                }
                DScore = 0;
                do {
                    Delay(50);
                    if (KeyPressed) {
                        KeyPressed = false;
                        switch (ch) {
                            case Event.LEFT:
                                if ((Fig.x > 1) && (field.getNewState(Fig.x - 1, Fig.y + 2) == 0)) {
                                    dMarker.HideFigure(Fig);
                                    Fig.x--;
                                    dMarker.DrawFigure(Fig);
                                }
                                break;
                            case Event.RIGHT:
                                if ((Fig.x < field.getWidth()) && (field.getNewState(Fig.x + 1, Fig.y + 2) == 0)) {
                                    dMarker.HideFigure(Fig);
                                    Fig.x++;
                                    dMarker.DrawFigure(Fig);
                                }
                                break;
                            case Event.UP:
                                i = Fig.c[1];
                                Fig.c[1] = Fig.c[2];
                                Fig.c[2] = Fig.c[3];
                                Fig.c[3] = i;
                                dMarker.DrawFigure(Fig);
                                break;
                            case Event.DOWN:
                                i = Fig.c[1];
                                Fig.c[1] = Fig.c[3];
                                Fig.c[3] = Fig.c[2];
                                Fig.c[2] = i;
                                dMarker.DrawFigure(Fig);
                                break;
                            case ' ':
                                dMarker.HideFigure(Fig);
                                DScore = dMarker.DropFigure(Fig,field,Level,DScore);
                                dMarker.DrawFigure(Fig);
                                //tc = 0;
                                break;
                            case 'P':
                            case 'p':
                                while (!KeyPressed) {
                                    dMarker.HideFigure(Fig);
                                    Delay(500);
                                    dMarker.DrawFigure(Fig);
                                    Delay(500);
                                }
                                tc = System.currentTimeMillis();
                                break;
                            case '-':
                                if (Level > 0) Level--;
                                k = 0;
                                dMarker.ShowLevel(Level);
                                break;
                            case '+':
                                if (Level < MaxLevel) Level++;
                                k = 0;
                                dMarker.ShowLevel(Level);
                                break;
                        }
                    }
                } while ((int) (System.currentTimeMillis() - tc) <= (MaxLevel - Level) * TimeShift + MinTimeShift);
            }
            dMarker.PasteFigure(Fig,field);
            do {
                NoChanges = true;
                TestField();
                if (!NoChanges) {
                    Delay(500);
                    field.pack();
                    dMarker.DrawField(field);
                    Score += DScore;
                    dMarker.ShowScore(Score);
                    if (k >= FigToDrop) {
                        k = 0;
                        if (Level < MaxLevel) Level++;
                        dMarker.ShowLevel(Level);
                    }
                }
            } while (!NoChanges);
        } while (!field.ifFull());
    }

    public void start() {
        _gr.setColor(Color.black);

        //		setBackground (new Color(180,180,180));

        if (thr == null) {
            thr = new Thread(this);
            thr.start();
        }
    }

    public void stop() {
        if (thr != null) {
            thr.stop();
            thr = null;
        }
    }

    void TestField() {
        int i, j;
        for (i = 1; i <= field.getDepth(); i++) {
            for (j = 1; j <= field.getWidth(); j++) {
                field.setOldState(j, i, field.getNewState(j, i));
            }
        }
        for (i = 1; i <= field.getDepth(); i++) {
            for (j = 1; j <= field.getWidth(); j++) {
                if (field.getNewState(j, i) > 0) {
                    CheckNeighbours(j, i - 1, j, i + 1, i, j);
                    CheckNeighbours(j - 1, i, j + 1, i, i, j);
                    CheckNeighbours(j - 1, i - 1, j + 1, i + 1, i, j);
                    CheckNeighbours(j + 1, i - 1, j - 1, i + 1, i, j);
                }
            }
        }
    }
}