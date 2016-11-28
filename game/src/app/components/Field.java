package app.components;

/**
 * Created by andreyd on 28.11.2016.
 */
public class Field {
    private int newState[][];
    private int oldState[][];


    public Field() {
        newState = new int[Game.WIDTH + 2][Game.DEPTH + 2];
        oldState = new int[Game.WIDTH + 2][Game.DEPTH + 2];
    }

    public int getOldState(int i, int j) {
        return oldState[i][j];
    }

    public int getNewState(int i, int j) {
        return newState[i][j];
    }

    void setOldState(int i, int j, int value) {
        oldState[i][j] = value;
    }

    public void setNewState(int i, int j, int value) {
        newState[i][j] = value;
    }

    public void pack() {
        int i, j, n;
        for (i = 1; i <= Game.WIDTH; i++) {
            n = Game.DEPTH;
            for (j = Game.DEPTH; j > 0; j--) {
                if (oldState[i][j] > 0) {
                    newState[i][n] = oldState[i][j];
                    n--;
                }
            }
            for (j = n; j > 0; j--)
                newState[i][j] = 0;
        }
    }

    public boolean isFull() {
        for (int i = 1; i <= Game.WIDTH; i++) {
            if (newState[i][3] > 0)
                return true;
        }
        return false;
    }

     void init() {
        for (int i = 0; i < Game.WIDTH + 1; i++) {
            for (int j = 0; j < Game.DEPTH + 1; j++) {
                oldState[i][j] = 0;
                newState[i][j] = 0;
            }
        }
    }

    public void pasteFigure(Figure f) {
        newState[f.x][f.y] = f.c[1];
        newState[f.x][f.y + 1] = f.c[2];
        newState[f.x][f.y + 2] = f.c[3];
    }
}
