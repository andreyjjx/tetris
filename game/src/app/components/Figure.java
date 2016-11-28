package app.components;

import java.util.Random;

public class Figure {

	public int x = Game.WIDTH / 2 + 1;
	public int y = 1;
	public int c[] = new int[4];
	final Random r;

	public Figure(Random r) {
		this.r = r;
		c[1] = Math.abs(r.nextInt()) % 7 + 1;
		c[2] = Math.abs(r.nextInt()) % 7 + 1;
		c[3] = Math.abs(r.nextInt()) % 7 + 1;
	}


	public boolean canMoveDown(Field field) {
		return (y < Game.DEPTH - 2)
				&& (field.getNewState(x, y + 3) == 0);
	}


	boolean canTurnLeft(Field field) {
		return (x > 1)
				&& (field.getNewState(x-1, y + 2) == 0);
	}


	boolean canTurnRight(Field field) {
		return (x < Game.WIDTH)
				&& (field.getNewState(x + 1,y + 2) == 0);
	}
}