/**
 * Created by andreyd on 27.11.2016.
 */
public class Field {

    private int[][] oldState;
    private int[][] newState;
    private int width;
    private int depth;

    public Field(int width, int depth) {
        this.width = width;
        this.depth = depth;
        this.oldState = new int[width + 2][depth + 2];
        this.newState = new int[width + 2][depth + 2];
    }

    public int getOldState(int i, int j) {
        return oldState[i][j];
    }

    public int getNewState(int i, int j) {
        return newState[i][j];
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public void setOldState(int i, int j, int value) {
        this.oldState[i][j] = value;
    }

    public void setNewState(int i, int j, int value) {
        this.newState[i][j] = value;
    }

    public boolean ifFull() {
        int i;
        for (i = 1; i <= this.width; i++) {
            if (this.newState[i][3] > 0)
                return true;
        }
        return false;
    }

    public void pack() {
        int i, j, n;
        for (i = 1; i <= this.width; i++) {
            n = this.depth;
            for (j = this.depth; j > 0; j--) {
                if (this.oldState[i][j] > 0) {
                    this.newState[i][n] = this.oldState[i][j];
                    n--;
                }
            }
            ;
            for (j = n; j > 0; j--)
                this.newState[i][j] = 0;
        }
    }
}
