package app.components;

/**
 * Created by andreyd on 28.11.2016.
 */
public interface Columns extends Runnable {

    void checkTripleBoxes(int a, int b, int c, int d, int i, int j);

    void update(Game game);
}
