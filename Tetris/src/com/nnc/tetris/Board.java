package com.nnc.tetris;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.nnc.tetris.Shape.Tetronimos;

public class Board extends JPanel implements ActionListener{

	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_HEIGHT = 22;
	private static final int TIME = 400;
	private static final Color[] COLORS = {};
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
	
	public Board(Tetris parent){
		setFocusable(true);
		currentPiece = new Shape();
		timer = new Timer(TIME, this);
		statusBar = parent.getStatusBar();
		board = new Tetronimos [BOARD_WIDTH * BOARD_HEIGHT];
		clearBoard();
		
	}
	
	public int squareWidth(){
		return (int) getSize().getWidth() / BOARD_WIDTH;
	}
	
	public int squareWeight(){
		return (int) getSize().getHeight() / BOARD_HEIGHT;
	}
	
	public Tetronimos shapeAt (int x, int y){
		return board[y * BOARD_WIDTH + x];
	}
	
	private void clearBoard(){
		
		for(int i=0; i< BOARD_HEIGHT * BOARD_WIDTH; i++)
			board[i] = Tetronimos.NO_SHAPE;
		
	}
	
	private void pieceDrop(){
		int x;
		int y;
		for(int i=0; i < currentPiece.getX();i++){
			x = currentX + currentPiece.x(i);
			y = currentY + currentPiece.y(i);
			board[y * BOARD_WIDTH + x] = currentPiece.getShape();
		}
		
		if (!isFallingFinished){
			newPiece();
		}
		
	}
	
	
	private void newPiece() {
		currentPiece.setRandomShape();
		currentX = BOARD_WIDTH / 2 + 1;
		currentY = BOARD_HEIGHT -1 + currentPiece.minY(); 
		
	}
	
	private void oneLineDown(){
		pieceDrop();
	}

	private void drawSquare(Graphics g, int x,int y, Tetronimos shape){
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

}
