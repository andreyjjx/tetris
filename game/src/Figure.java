/**
 * Created by andreyd on 27.11.2016.
 */

import java.applet.*;
import java.awt.*;
import java.util.*;


class Figure {
    static int x, y, c[] = new int[4];
    static Random r = new Random();

    Figure(int fieldWidth) {
        x = fieldWidth / 2 + 1;
        y = 1;
        c[0] = 0;
        c[1] = (int) (Math.abs(r.nextInt()) % 7 + 1);
        c[2] = (int) (Math.abs(r.nextInt()) % 7 + 1);
        c[3] = (int) (Math.abs(r.nextInt()) % 7 + 1);
    }
}