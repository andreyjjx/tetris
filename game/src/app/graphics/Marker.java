package app.graphics;

import app.Application;
import app.components.Figure;
import app.components.Game;

import java.awt.*;

public class Marker {
	private Game game;
	private Graphics gr;

	public Marker(Game game, Graphics gr) {
		this.game = game;
		this.gr = gr;
	}

	private void drawBox(int x, int y, int c) {
		if (c == 0) {
			gr.setColor(Color.black);
			gr.fillRect(Application.LEFT_BORDER + x * Application.SL - Application.SL, Application.TOP_BORDER + y * Application.SL - Application.SL, Application.SL,
					Application.SL);
			gr.drawRect(Application.LEFT_BORDER + x * Application.SL - Application.SL, Application.TOP_BORDER + y * Application.SL - Application.SL, Application.SL,
					Application.SL);
		} else if (c == 8) {
			gr.setColor(Color.white);
			gr.drawRect(Application.LEFT_BORDER + x * Application.SL - Application.SL + 1, Application.TOP_BORDER + y * Application.SL - Application.SL
					+ 1, Application.SL - 2, Application.SL - 2);
			gr.drawRect(Application.LEFT_BORDER + x * Application.SL - Application.SL + 2, Application.TOP_BORDER + y * Application.SL - Application.SL
					+ 2, Application.SL - 4, Application.SL - 4);
			gr.setColor(Color.black);
			gr.fillRect(Application.LEFT_BORDER + x * Application.SL - Application.SL + 3, Application.TOP_BORDER + y * Application.SL - Application.SL
					+ 3, Application.SL - 6, Application.SL - 6);
		} else {
			gr.setColor(Application.COLOR_STYLES[c]);
			gr.fillRect(Application.LEFT_BORDER + x * Application.SL - Application.SL, Application.TOP_BORDER + y * Application.SL - Application.SL, Application.SL,
					Application.SL);
			gr.setColor(Color.black);
			gr.drawRect(Application.LEFT_BORDER + x * Application.SL - Application.SL, Application.TOP_BORDER + y * Application.SL - Application.SL, Application.SL,
					Application.SL);
		}
	}

	public void drawField() {
		for (int i = 1; i <= Game.DEPTH; i++) {
			for (int j = 1; j <= Game.WIDTH; j++) {
				drawBox(j, i, game.getField().getNewState(j,i));
			}
		}
	}

	public void drawFigure(Figure f) {
		drawBox(f.x, f.y, f.c[1]);
		drawBox(f.x, f.y + 1, f.c[2]);
		drawBox(f.x, f.y + 2, f.c[3]);
	}

	public void hideFigure(Figure f) {
		drawBox(f.x, f.y, 0);
		drawBox(f.x, f.y + 1, 0);
		drawBox(f.x, f.y + 2, 0);
	}

	public void makeTripleBoxesLight(int a, int b, int c, int d, int i, int j) {
		drawBox(a, b, 8);
		drawBox(j, i, 8);
		drawBox(c, d, 8);
	}

	public void drawAllElements(Graphics gr) {
		showLevel(gr);
		showScore(gr);
		drawField();
		if (game.getFigure() != null)
			drawFigure(game.getFigure());
	}

	public void showHelp(Graphics gr) {
		gr.setColor(Color.black);
	
		gr.drawString(" Keys:", 200 - Application.LEFT_BORDER, 102);
		gr.drawString("Move Up:    ^ ", 200 - Application.LEFT_BORDER, 118);
		gr.drawString("Move Down:  v ", 200 - Application.LEFT_BORDER, 128);
		gr.drawString("Turn Left:    <- ", 200 - Application.LEFT_BORDER, 138);
		gr.drawString("Turn Right:   -> ", 200 - Application.LEFT_BORDER, 148);
		gr.drawString("Level High/Low: +/-", 200 - Application.LEFT_BORDER, 158);
		gr.drawString("Drop Figure:   space", 200 - Application.LEFT_BORDER, 168);
		gr.drawString("Pause:           P", 200 - Application.LEFT_BORDER, 180);
	}

	public void showLevel(Graphics gr) {
		gr.setColor(Color.black);
		gr.clearRect(Application.LEFT_BORDER + 100, 390, 100, 20);
		gr.drawString("Level: " + game.getLevel(), Application.LEFT_BORDER + 100, 400);
	}

	public void showScore(Graphics gr) {
		gr.setColor(Color.black);
		gr.clearRect(Application.LEFT_BORDER, 390, 100, 20);
		gr.drawString("Score: " + game.getOverallScore(), Application.LEFT_BORDER, 400);
	}
}
