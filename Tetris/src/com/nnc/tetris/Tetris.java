package com.nnc.tetris;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tetris extends JFrame{
	
	private JLabel statusBar;
	
	public Tetris(){
		//to display lines number
		statusBar = new JLabel ("0");
		add(statusBar, BorderLayout.SOUTH);
		Board board = new Board(this);
		add(board);
		
		//add one piece
		board.newPiece();
		board.repaint();
		
		setSize(200, 400);
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public JLabel getStatusBar(){
		return statusBar;
	}
	
	public void main(String[] args){
		Tetris tetris = new Tetris();
		tetris.setLocationRelativeTo(null);
		tetris.setVisible(true);
	}

}
