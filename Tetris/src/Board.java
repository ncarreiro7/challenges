import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener{

	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_WEIGHT = 22;
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
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
