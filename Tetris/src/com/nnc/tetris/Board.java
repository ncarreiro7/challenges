package com.nnc.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.nnc.tetris.Shape.Tetronimos;

public class Board extends JPanel implements ActionListener {

	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_HEIGHT = 22;
	private static final int TIME = 400;
	private static final Color[] COLORS = { new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102),
			new Color(102, 102, 204), new Color(102, 204, 102), new Color(102, 102, 204), new Color(204, 204, 102),
			new Color(204, 102, 204), new Color(102, 204, 204), new Color(218, 170, 0) };
	private Timer timer;
	private boolean isFallingFinished = false;
	private boolean isStarted = false;
	private boolean isPaused = false;
	private int numLinesRemoved = 0;
	private int currentX = 0;
	private int currentY = 0;
	private JLabel statusBar;
	private Shape currentPiece;
	private Tetronimos[] board;

	public Board(Tetris parent) {
		setFocusable(true);
		currentPiece = new Shape();
		timer = new Timer(TIME, this);
		statusBar = parent.getStatusBar();
		board = new Tetronimos[BOARD_WIDTH * BOARD_HEIGHT];
		clearBoard();

	}

	public int squareWidth() {
		return (int) getSize().getWidth() / BOARD_WIDTH;
	}

	public int squareHeight() {
		return (int) getSize().getHeight() / BOARD_HEIGHT;
	}

	public Tetronimos shapeAt(int x, int y) {
		return board[y * BOARD_WIDTH + x];
	}

	private void clearBoard() {

		for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++)
			board[i] = Tetronimos.NO_SHAPE;

	}

	private void pieceDrop() {
		int x;
		int y;
		for (int i = 0; i < currentPiece.getX(); i++) {
			x = currentX + currentPiece.x(i);
			y = currentY + currentPiece.y(i);
			board[y * BOARD_WIDTH + x] = currentPiece.getShape();
		}

		if (!isFallingFinished) {
			newPiece();
		}

	}

	public void newPiece() {
		currentPiece.setRandomShape();
		currentX = BOARD_WIDTH / 2 + 1;
		currentY = BOARD_HEIGHT - 1 + currentPiece.minY();

	}

	private void oneLineDown() {
		pieceDrop();
	}

	private void drawSquare(Graphics g, int x, int y, Tetronimos shape) {
		Color color = COLORS[shape.ordinal()];
		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, x + squareWidth() - 1, x, y);
		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Dimension size = getSize();

		int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();

		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				Tetronimos shape = shapeAt(j, BOARD_HEIGHT - i - 1);

				if (shape != Tetronimos.NO_SHAPE) {
					drawSquare(g, j * squareWidth(), boardTop + i + squareHeight(), shape);

				}
			}

		}

		if (currentPiece.getShape() != Tetronimos.NO_SHAPE) {
			int x;
			int y;
			for (int i = 0; i < currentPiece.getX(); i++) {
				x = currentX + currentPiece.x(i);
				y = currentY + currentPiece.y(i);
				drawSquare(g, x * squareWidth(), boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
						currentPiece.getShape());
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

}
