import java.util.Random;

public class Shape {
	
	enum Tetronimos {
		NO_SHAPE (new int[][] {{0,0}, {0,0},{0,0},{0,0}}),
		Z_SHAPE ((new int[][] {{0,-1}, {0,0},{-1,0},{-1,1}})),
		S_SHAPE ((new int[][] {{0,-1}, {0,0},{1,0},{1,1}})),
		LINE_SHAPE ((new int[][] {{0,-1}, {0,0},{0,1},{0,2}})),
		T_SHAPE ((new int[][] {{-1,0}, {0,0},{1,0},{0,1}})),
		SQUARE_SHAPE ((new int[][] {{0,0}, {1,0},{0,1},{1,1}})),
		L_SHAPE ((new int[][] {{-1,-1}, {0,-1},{0,0},{0,1}})),
		MIRROR_L_SHAPE ((new int[][] {{1,-1}, {0,-1},{0,0},{0,1}}))
		;
		
		public int[][] coords;
		
		private Tetronimos (int[][] coords){
			this.coords = coords;
		}
	}
	
	private final int X=4;
	private final int Y = 2;
	private Tetronimos pieceShape;
	private int[][] coords;
	
	
	
	public Shape(){
		coords = new int[X][Y];
		setShape(Tetronimos.NO_SHAPE);
	}


	public void setShape(Tetronimos shape) {
		for(int i=0;i<X;i++)
			for(int j=0;j<Y;j++)
				coords[i][j] = shape.coords[i][j];
		
		pieceShape = shape;
		
	}
	
	private void setX(int index, int x){
		coords[index][0] = x;
	}
	
	private void setY(int index, int y){
		coords[index][1] = y;
	}
	
	private int x(int index){
		return coords[index][0];
	}
	
	private int y(int index){
		return coords[index][1];
	}
	
	public Tetronimos getShape(){
		return pieceShape;
	}
	
	public void setRandomShape(){
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 7 + 1;
		Tetronimos[] values = Tetronimos.values();
		setShape(values[x]);
		
	}
	
	public int minX(){
		int min = coords[0][0];
		
		for (int i =0; i < X; i++){
			min = Math.min(min, coords[i][0]);
		}
		
		return min;
		
	}
	
	public int minY(){
		int min = coords[0][1];
		
		for (int i =0; i < Y; i++){
			min = Math.min(min, coords[i][1]);
		}
		
		return min;
		
	}

}
