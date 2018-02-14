package com.nnc.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		addKeyListener(new TetrisAdapter());
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
		for (int i = 0; i < currentPiece.getX(); i++) {
			int x = currentX + currentPiece.x(i);
			int y = currentY + currentPiece.y(i);
			board[y * BOARD_WIDTH + x] = currentPiece.getShape();
		}
		
		removeFullLines();

		if (!isFallingFinished) 
			newPiece();
	}

	public void newPiece() {
		currentPiece.setRandomShape();
		currentX = BOARD_WIDTH / 2 + 1;
		currentY = BOARD_HEIGHT - 1 + currentPiece.minY();

		if (!tryMove(currentPiece, currentX, currentY -1)) {
			currentPiece.setShape(Tetronimos.NO_SHAPE);
			timer.stop();
			isStarted = false;
			statusBar.setText("Game Over");
		}
	}

	private void drawSquare(Graphics g, int x, int y, Tetronimos shape) {
		Color color = COLORS[shape.ordinal()];
		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);
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
			for (int j = 0; j < BOARD_WIDTH; ++j) {
				Tetronimos shape = shapeAt(j, BOARD_HEIGHT - i - 1);

				if (shape != Tetronimos.NO_SHAPE) {
					drawSquare(g, j * squareWidth(), boardTop + i * squareHeight(), shape);

				}
			}
		}

		if (currentPiece.getShape() != Tetronimos.NO_SHAPE) {
			
			for (int i = 0; i < currentPiece.getX(); ++i) {
				int x = currentX + currentPiece.x(i);
				int y = currentY + currentPiece.y(i);
				drawSquare(g, x * squareWidth(), boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
						currentPiece.getShape());
			}
		}

	}
	
	public void start(){
		if(isPaused)
			return;
		
		isStarted = true;
		isFallingFinished = false;
		numLinesRemoved = 0;
		clearBoard();
		newPiece();
		timer.start();
		
	}
	
	public void pause(){
		if(!isStarted)
			return;
		
		isPaused = !isPaused;
		
		if(isPaused) {
			timer.stop();
			statusBar.setText("Paused");
			
		}
		else{
			timer.start();
			statusBar.setText(String.valueOf(numLinesRemoved));
			
		}
		
		repaint();
	}
	
	private boolean tryMove(Shape newPiece, int newX,int newY){
		for (int i =0;i < newPiece.getX(); ++i){
			int x = newX + newPiece.x(i);
			int y = newY - newPiece.y(i);
			
			if(x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT)
				return false;
			
			if(shapeAt(x, y) != Tetronimos.NO_SHAPE)
				return false;	
		}
		
		currentPiece = newPiece;
		currentX = newX;
		currentY = newY;
		repaint();
		
		return true;
	}
	
	private void removeFullLines(){
		int numFullLines = 0;
		
		for (int i = BOARD_HEIGHT - 1; i >= 0; i--){
			boolean lineIsFull = true;
			
			for (int j =0; j < BOARD_WIDTH ; j++){
				if (shapeAt(j, i) == Tetronimos.NO_SHAPE) {
					lineIsFull = false;
					break;
					
				}
			}
			
			if (lineIsFull) {
				numFullLines++;
				
				for(int k = i; k  < BOARD_HEIGHT - 1;k++){
					for( int j = 0; j < BOARD_WIDTH; j++)
						board [ k * BOARD_WIDTH + j] = shapeAt(j, k + 1);
				}
			}
			
			if (numFullLines > 0) {
				numLinesRemoved += numFullLines;
				statusBar.setText(String.valueOf(numLinesRemoved));
				isFallingFinished = true;
				currentPiece.setShape(Tetronimos.NO_SHAPE);
				repaint();
			}
		}
	}
	
	
	private void dropDown() {
		int newY = currentY;
		
		while (newY > 0){
			
			if(!tryMove (currentPiece,currentX,newY -1))
				break;
			
			--newY;
		}
		
		pieceDrop();
	}
	
	private void oneLineDown(){
		if (!tryMove(currentPiece, currentX, currentY - 1))
			pieceDrop();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (isFallingFinished) {
		      isFallingFinished = false;
		      newPiece();
		    } else {
		      oneLineDown();
		    }
	}
	
	class TetrisAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent ke){
			
			if(!isStarted || currentPiece.getShape() == Tetronimos.NO_SHAPE)
				return;
			
			int keyCode = ke.getKeyCode();
			
			if (keyCode == 'p' || keyCode == 'P')
				pause();
			
			if (isPaused)
				return;
			
			switch(keyCode) {
			case KeyEvent.VK_LEFT:
				tryMove(currentPiece, currentX -1, currentY);
				break;
			case KeyEvent.VK_RIGHT:
				tryMove(currentPiece, currentX +1, currentY);
				break;
			case KeyEvent.VK_UP:
				tryMove(currentPiece.rotateLeft(), currentX, currentY);
				break;
			case KeyEvent.VK_DOWN:
				oneLineDown();
				break;
			case KeyEvent.VK_SPACE:
				dropDown();
				break;
			}
			
		}
	}

}
