import components.EventManager;
import components.Field;
import components.Figure;
import graphics.Marker;

/**
 * Created by andreyd on 28.11.2016.
 */
public class Game implements EventManager {

    private long score = 0, dScore;
    public static final int MAX_LEVEL = 7;
    public static final int TIME_SHIFT = 250;
    public static final int FIG_TO_DROP = 33;
    public static final int MIN_TIME_SHIFT = 200;
    private int level = 0, neighboursCounter = 0;
    private boolean noChanges;
    private Figure fig;
    private Field field;
    private Marker dMarker;

    public Game(Marker dMarker) {
        this.dMarker = dMarker;
        this.field = new Field(7, 15);
        this.fig = new Figure(this.field);
    }

    public long getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        if (level < MAX_LEVEL) level++;
    }

    public void levelDown() {
        if (level > 0) level--;
    }

    public Field getField() {
        return field;
    }


    public Figure getFig() {
        return fig;
    }

    public void setdScore(long dScore) {
        this.dScore = dScore;
    }

    public void checkNeighbours(int a, int b, int c, int d, int i, int j) {
        if (field.getNewState(j, i) == field.getNewState(a, b) &&
                field.getNewState(j, i) == field.getNewState(c, d)) {

            field.setOldState(a, b, 0);
            dMarker.drawBox(a, b, 8);

            field.setOldState(j, i, 0);
            dMarker.drawBox(j, i, 8);

            field.setOldState(c, d, 0);
            dMarker.drawBox(c, d, 8);

            noChanges = false;
            score += (level + 1) * 10;
            neighboursCounter++;
        }
    }

    @Override
    public void turnFigureLeft() {
        if (fig.canMoveLeft()) {
            dMarker.hideFigure(fig);
            fig.x--;
            dMarker.drawFigure(fig);
        }
    }

    @Override
    public void turnFigureRight() {
        if (fig.canMoveRight()) {
            dMarker.hideFigure(fig);
            fig.x++;
            dMarker.drawFigure(fig);
        }
    }

    @Override
    public void figureColorsUp() {
        int i = fig.colors[1];
        fig.colors[1] = fig.colors[2];
        fig.colors[2] = fig.colors[3];
        fig.colors[3] = i;
        dMarker.drawFigure(fig);
    }

    @Override
    public void figureColorsDown() {
        int i = fig.colors[1];
        fig.colors[1] = fig.colors[3];
        fig.colors[3] = fig.colors[2];
        fig.colors[2] = i;
        dMarker.drawFigure(fig);
    }

    @Override
    public void dropFigure() {
        dMarker.hideFigure(fig);
        int zz;
        if (fig.y < field.getDepth() - 2) {
            zz = field.getDepth();
            while (field.getNewState(fig.x, zz) > 0) zz--;
            dScore = (((level + 1) * (field.getDepth() * 2 - fig.y - zz) * 2) % 5) * 5;
            fig.y = zz - 2;
        }
        dMarker.drawFigure(fig);
    }

    public void setNeighboursCounter(int neighboursCounter) {
        this.neighboursCounter = neighboursCounter;
    }

    public int getNeighboursCounter() {

        return neighboursCounter;
    }

    public void testField() {
        int i, j;
        for (i = 1; i <= field.getDepth(); i++) {
            for (j = 1; j <= field.getWidth(); j++) {
                field.setOldState(j, i, field.getNewState(j, i));
            }
        }
        for (i = 1; i <= field.getDepth(); i++) {
            for (j = 1; j <= field.getWidth(); j++) {
                if (field.getNewState(j, i) > 0) {
                    checkNeighbours(j, i - 1, j, i + 1, i, j);
                    checkNeighbours(j - 1, i, j + 1, i, i, j);
                    checkNeighbours(j - 1, i - 1, j + 1, i + 1, i, j);
                    checkNeighbours(j + 1, i - 1, j - 1, i + 1, i, j);
                }
            }
        }
    }
    public void someWaits() {
        do {
            noChanges = true;
            testField();
            if (!noChanges) {
                field.pack();
                dMarker.drawField(field);
                score += dScore;
                dMarker.showScore(score);
                if (neighboursCounter >= FIG_TO_DROP) {
                    neighboursCounter = 0;
                    if (level < MAX_LEVEL)
                        levelDown();
                    dMarker.showLevel(level);
                }
            }
        } while (!noChanges);
    }
}
