package app.components;

public class Game implements EventManager {

	public static final int DEPTH = 15;
	public static final int WIDTH = 7;
	public static final int MAX_LEVEL = 7;
	public static final int FIG_TO_DROP = 33;
	private Figure fig;
	private Field field;
	private int level;
	private long overallScore;
	private long deltaScore;
	private int tripleBoxesCount;
	private Columns columns;

	public Game(Columns columns)  {
		this.columns = columns;
	}
	
	
	public int getTripleBoxesCount() {
		return tripleBoxesCount;
	}

	public void setTripleBoxesCount(int tripleBoxesCount) {
		this.tripleBoxesCount = tripleBoxesCount;
	}

	public long getDeltaScore() {
		return deltaScore;
	}

	public void setDeltaScore(long deltaScore) {
		this.deltaScore = deltaScore;
	}

	public long getOverallScore() {
		return overallScore;
	}

	public void setOverallScore(long overallScore) {
		this.overallScore = overallScore;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Figure getFigure() {
		return fig;
	}

	public void setFigure(Figure fig) {
		this.fig = fig;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Field getField() {
		return field;
	}

	private boolean checkNeighbours(int a, int b, int c, int d, int i, int j) {
		if ((field.getNewState(j, i) != field.getNewState(a, b))
				|| (field.getNewState(j, i) != field.getNewState(c, d))) {
			return false;
		}
		field.setOldState(a, b, 0);
		field.setOldState(j, i, 0);
		field.setOldState(c, d, 0);
		setOverallScore(getOverallScore() + (getLevel() + 1) * 10);
		setTripleBoxesCount(getTripleBoxesCount() + 1);
		columns.checkTripleBoxes(a, b, c, d, i, j);
		return true;
	}

	public boolean testField() {
		boolean wasChanges = false;
		int i, j;
		for (i = 1; i <= Game.DEPTH; i++) {
			for (j = 1; j <= Game.WIDTH; j++) {
				field.setOldState(j, i, field.getNewState(j, i));
			}
		}
		for (i = 1; i <= Game.DEPTH; i++) {
			for (j = 1; j <= Game.WIDTH; j++) {
				if (field.getNewState(j, i) > 0) {
					wasChanges |= checkNeighbours(j, i - 1, j, i + 1, i, j);
					wasChanges |= checkNeighbours(j - 1, i, j + 1, i, i, j);
					wasChanges |= checkNeighbours(j - 1, i - 1, j + 1, i + 1, i, j);
					wasChanges |= checkNeighbours(j + 1, i - 1, j - 1, i + 1, i, j);
				}
			}
		}
		return wasChanges;
	}


	public void setup() {
		field.init();
		setLevel(0);
		setOverallScore((long) 0);
	}


	public void increaseLevel() {
		if (level < Game.MAX_LEVEL)
			level++;
		tripleBoxesCount = 0;
		columns.update(this);
	}


	public void descreaseLevel() {
		if (level > 0)
			level--;
		tripleBoxesCount = 0;
		columns.update(this);
	}


	@Override
	public void turnFigureLeft() {
		if (getFigure().canTurnLeft(field)) {
			getFigure().x--;
			columns.update(this);
		}
	}


	@Override
	public void turnFigureRight() {
		if (getFigure().canTurnRight(field)) {
			getFigure().x++;
			columns.update(this);
		}
	}


	@Override
	public void figureColorsUp() {
		int i = getFigure().c[1];
		getFigure().c[1] = getFigure().c[2];
		getFigure().c[2] = getFigure().c[3];
		getFigure().c[3] = i;
		columns.update(this);
	}


	@Override
	public void figureColorsDown() {
		int i = getFigure().c[1];
		getFigure().c[1] = getFigure().c[3];
		getFigure().c[3] = getFigure().c[2];
		getFigure().c[2] = i;
		columns.update(this);
	}

	@Override
	public void dropFigure() {
		int zz;
		if (fig.y < Game.DEPTH - 2) {
			zz = Game.DEPTH;
			while (field.getNewState(fig.x,zz) > 0)
				zz--;
			setDeltaScore((long) ((((getLevel() + 1)
					* (Game.DEPTH * 2 - fig.y - zz) * 2) % 5) * 5));
			fig.y = zz - 2;
			columns.update(this);
		}
	}


}