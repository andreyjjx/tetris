package app;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.util.Random;

import app.components.Columns;
import app.components.Field;
import app.components.Figure;
import app.components.Game;
import app.graphics.Marker;


public class Application extends Applet implements Columns {

    public static final int SL = 25;

    private static final int TIME_SHIFT = 250;

    private static final int MIN_TIME_SHIFT = 200;

    public static final int LEFT_BORDER = 2;

    public static final int TOP_BORDER = 2;

    private static final Random Random = new Random();

    public static final Color COLOR_STYLES[] = {Color.black, Color.cyan, Color.blue, Color.red,
            Color.green, Color.yellow, Color.pink, Color.magenta, Color.black};

    private long timestamp;
    private boolean keyPressed = false;
    private Graphics gr;
    private int ch;

    private Thread thr = null;
    private final Field field = new Field();
    private Game game = new Game(this);
    private Marker marker;

    private void delay(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        gr = getGraphics();
        game.setField(field);
        game.setup();
        marker = new Marker(game, gr);
    }

    @Override
    public boolean keyDown(Event e, int k) {
        keyPressed = true;
        ch = e.key;
        return true;
    }

    @Override
    public boolean lostFocus(Event e, Object w) {
        keyPressed = true;
        ch = 'P';
        return true;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        marker.drawAllElements(g);
        requestFocus();
    }

    private void createNewFigure() {
        game.setFigure(new Figure(Random));
        marker.drawFigure(game.getFigure());
    }

    private void moveFigureNextLine() {
        marker.hideFigure(game.getFigure());
        game.getFigure().y++;
        marker.drawFigure(game.getFigure());
    }


    private boolean canMoveFigureNextLine() {
        return (int) (System.currentTimeMillis() - timestamp) > getTimeForDropping();
    }

    private boolean isFigureOnLastLine() {
        return (int) (System.currentTimeMillis() - timestamp) <= getTimeForDropping();
    }

    private long getTimeForDropping() {
        return (Game.MAX_LEVEL - game.getLevel()) * TIME_SHIFT + MIN_TIME_SHIFT;
    }

    @Override
    public void run() {
        gr.setColor(Color.black);
        requestFocus();
        do {
            timestamp = System.currentTimeMillis();
            createNewFigure();
            while (game.getFigure().canMoveDown(field)) {
                if (canMoveFigureNextLine()) {
                    timestamp = System.currentTimeMillis();
                    moveFigureNextLine();
                }
                game.setDeltaScore((long) 0);
                do {
                    delay(50);
                    if (keyPressed) {
                        keyPressed = false;
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
                                while (!keyPressed) {
                                    marker.hideFigure(game.getFigure());
                                    delay(500);
                                    marker.drawFigure(game.getFigure());
                                    delay(500);
                                }
                                timestamp = System.currentTimeMillis();
                                break;
                            case '-':
                                game.descreaseLevel();
                                marker.showLevel(gr);
                                break;
                            case '+':
                                game.increaseLevel();
                                marker.showLevel(gr);
                                break;
                        }
                    }
                } while (isFigureOnLastLine());
            }
            field.pasteFigure(game.getFigure());
            boolean changes;
            do {
                changes = game.testField();
                if (changes) {
                    delay(500);
                    field.pack();
                    marker.drawField();
                    game.setOverallScore(game.getOverallScore() + game.getDeltaScore());
                    marker.showScore(gr);
                    if (game.getTripleBoxesCount() >= Game.FIG_TO_DROP) {
                        game.setTripleBoxesCount(0);
                        if (game.getLevel() < Game.MAX_LEVEL)
                            game.setLevel(game.getLevel() + 1);
                        marker.showLevel(gr);
                    }
                }
            } while (changes);
        } while (!game.getField().isFull());

    }

    public void start() {

        if (thr == null) {
            thr = new Thread(this);
            thr.start();
        }
    }

    @Override
    public void checkTripleBoxes(int a, int b, int c, int d, int i, int j) {
        marker.makeTripleBoxesLight(a, b, c, d, i, j);
    }

    public void update(Game game) {
        marker.drawAllElements(gr);
    }

}