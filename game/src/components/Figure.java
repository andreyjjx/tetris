package components; /**
 * Created by andreyd on 27.11.2016.
 */

import java.util.*;


public class Figure {
    public static int x, y,
            colors[] = new int[4];
    private Random r = new Random();
    private Field field;

    public Figure(Field field) {
        this.field = field;
        reset();
    }

    public void reset() {
        x = field.getWidth() / 2 + 1;
        y = 1;
        colors[0] = 0;
        colors[1] = (int) (Math.abs(r.nextInt()) % 7 + 1);
        colors[2] = (int) (Math.abs(r.nextInt()) % 7 + 1);
        colors[3] = (int) (Math.abs(r.nextInt()) % 7 + 1);
    }

    public void paste() {
        field.setNewState(x, y, colors[1]);
        field.setNewState(x, y + 1, colors[2]);
        field.setNewState(x, y + 2, colors[3]);
    }

    public boolean canMoveDown() {
        return (y < field.getDepth() - 2)
                && (field.getNewState(x, y + 3) == 0);
    }


    public boolean canMoveLeft() {
        return (x > 1)
                && (field.getNewState(x - 1, y + 2) == 0);
    }


    public boolean canMoveRight() {
        return (x < field.getWidth())
                && (field.getNewState(x + 1, y + 2) == 0);
    }

}