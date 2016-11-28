import components.Figure;
import graphics.Marker;

import java.applet.*;
import java.awt.*;


public class Columns extends Applet implements Runnable {

    int ch;
    long tc;
    boolean KeyPressed = false;
    Graphics _gr;
    Thread thr = null;
    Marker dMarker;
    Game game;

    void Delay(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
        }
    }

    public void init() {
        _gr = getGraphics();
        _gr.setColor(Color.black);
        dMarker = new Marker(_gr);
        game = new Game(dMarker);
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
        //		showHelp(g);

        g.setColor(Color.black);

        dMarker.showLevel(game.getLevel());
        dMarker.showScore(game.getScore());
        dMarker.drawField(game.getField());
        dMarker.drawFigure(game.getFig());
        requestFocus();
    }

    public void run() {
        requestFocus();

        do {
            tc = System.currentTimeMillis();
            game.getFig().reset();
            dMarker.drawFigure(game.getFig());
            while (game.getFig().canMoveDown()) {
                if ((int) (System.currentTimeMillis() - tc) > (Game.MAX_LEVEL - Game.MAX_LEVEL) * Game.TIME_SHIFT + Game.MIN_TIME_SHIFT) {
                    tc = System.currentTimeMillis();
                    dMarker.hideFigure(game.getFig());
                    game.getFig().y++;
                    dMarker.drawFigure(game.getFig());
                }
                game.setdScore(0);
                do {
                    Delay(50);
                    if (KeyPressed) {
                        KeyPressed = false;
                        switch (ch) {
                            case Event.LEFT:
                                game.turnFigureLeft();
                                break;
                            case Event.RIGHT:
                                game.turnFigureRight();
                                break;
                            case Event.UP:
                                game.figureColorsUp();
                                break;
                            case Event.DOWN:
                                game.figureColorsDown();
                                break;
                            case ' ':
                                game.dropFigure();
                                break;
                            case 'P':
                            case 'p':
                                while (!KeyPressed) {
                                    dMarker.hideFigure(game.getFig());
                                    Delay(500);
                                    dMarker.drawFigure(game.getFig());
                                    Delay(500);
                                }
                                tc = System.currentTimeMillis();
                                break;
                            case '-':
                                game.levelDown();
                                game.setNeighboursCounter(0);
                                dMarker.showLevel(game.getLevel());
                                break;
                            case '+':
                                game.levelUp();
                                game.setNeighboursCounter(0);
                                dMarker.showLevel(game.getLevel());
                                break;
                        }
                    }
                }
                while ((int) (System.currentTimeMillis() - tc) <= (Game.MAX_LEVEL - Game.MAX_LEVEL) * Game.TIME_SHIFT + Game.MIN_TIME_SHIFT);
            }
            game.getFig().paste();
            Delay(500);
            game.someWaits();
        } while (!game.getField().ifFull());
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
}