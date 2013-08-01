//Christina Chung
//Last Modified: Feb 23/2013 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


//TicTacToe: runs a two-player Tic Tac Toe game 
public class TicTacToe extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//variables 
	static final char UNCLAIMED= ' ', O= 'O', X='X', DRAW='-';
	char winner= ' ';
	int counter=0; //counter: used to keep track of whose turn it is 
	String result=""; //stores the string notifying the winner to display on frame 
	char marker; 
	private char tiles[]= { //keep track of claimed tiles 
			UNCLAIMED, UNCLAIMED, UNCLAIMED,
			UNCLAIMED,UNCLAIMED,UNCLAIMED,
			UNCLAIMED,UNCLAIMED,UNCLAIMED,
	};

	//this array contains all the possible winning combinations
    private int winningCombo[][]={
    		{0,3,6},//vertical
    		{1,4,7},
    		{2,5,8},
    		
    		{0,1,2}, //horizontal
    		{3,4,5},
    		{6,7,8},
    		
    		{0,4,8}, //diagonal
    		{2,4,6}};

	 Font font= new Font ("Calibri",Font.BOLD,100);
	 

	 
	public static void main(String[] args) {
		new TicTacToe(); 

	}
	
	//TicTacToe(): constructor for the TicTacToe class, constructs the JFrame 
	public TicTacToe (){ 
		super ("Tic Tac Toe");
		setResizable(false); 
		setSize(500,552);
		JPanel pane= (JPanel)getContentPane();
		pane.setLayout(new BorderLayout());
		JButton restart= new JButton ("Restart"); 
		restart.addActionListener(this); 
		pane.add(new TicTacToeBoard(),BorderLayout.CENTER);
		pane.add(restart,BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible (true);	
		pane.addMouseListener(new ClickHandler()); 
	}
	

	//Parameters: ActionEvent e
	//Returns: void
	//Method Description: actionPerformed(): called when user clicks the "reset" button
	//and starts a new game
	public void actionPerformed (ActionEvent e){
		 for (int i=0; i<9; ++i){
	          tiles[i]=UNCLAIMED; //clear tiles 
	      }
		 winner=' '; 
		 result="";
		 repaint();
		}
 
	//Parameters: char holder (the current player)
	//Returns: boolean 
	//Method Description: isWinner(): determines if the given player has won the game and 
	//if so, the boolean value "true" is returned, otherwise,
	//the boolean value "false" is returned. 
	   boolean isWinner(char holder) {
		      for (int i=0; i<8; ++i){ //compares all the winning row combinations to see if player has won
		        if (winCombo(holder, winningCombo[i][0], winningCombo[i][1], winningCombo[i][2])){
		        	return true;
		          }
		      }
		      return false;
		    }
	   
	  //Parameters: char holder, int a, int b, int c (char holder= current player & a,b,c= integers represeting the winning row combinations) 
	  //Returns: boolean 
	  //Method Description: winCombo(): determines if the holder matches the given winning combination
	   boolean winCombo(char holder, int a, int b,int c) {
		      return (tiles[a]==holder && tiles[b]==holder && tiles[c]==holder);
		    }
	   
	   //Parameters: none
	   //Returns: boolean
	   //Method Description: isDraw(): determines if the game end result is a draw by
	   //checking if any tiles are currently unclaimed. If there are unclaimed tiles,
	   //then the game has not ended the boolean expression "false" is returned. Otherwise,
	   //if all tiles have been claimed, then the game is over and "true" is returned. 
	    boolean isDraw() {
	      for (int i=0; i<9; ++i){  
	        if (tiles[i]==UNCLAIMED){
	          return false;
	        }
	      }
	      return true;
	    }
	 
	    //Parameters: none
	    //Returns: void
	    //Method Description: checkForWin (): determines if a player won or if there's a draw
		void checkForWin(){
			  if (isWinner(X)){
		    	  winner=X; 
		    	  result="X WON!";
		      }else if (isDraw()){
		    	  winner=DRAW; 
		          result="TIE!";
		      }else if (isWinner(O)){
		          winner=O;
		          result= "O WON!";
		      }
		}
	   
		

	//ClickHandler: handles user clicks 
	class ClickHandler extends MouseAdapter{
		
		
		    int size=getWidth(); //length of board

		    int a= size/3;
			int c=size;
			int f=size/3*2;	
			
			Rectangle A1= new Rectangle (0,0,a,a); //these rectangles represent each tile on the board
			Rectangle A2= new Rectangle (a,0,f,a); 
			Rectangle A3= new Rectangle (f,0,c,a); 
			Rectangle A4= new Rectangle (0,a,a,f); 
			Rectangle A5= new Rectangle (a,a,f,f); 
			Rectangle A6= new Rectangle (f,a,c,f); 
			Rectangle A7= new Rectangle (0,f,a,c); 
			Rectangle A8= new Rectangle (a,f,f,c); 
			Rectangle A9= new Rectangle (f,f,c,c);
		
		//Parameters: MouseEvent E
		//Returns: void
		//Method Description: mouseReleased(): this method gets called whenever a user
		//released the mouse on the JFrame. 
		public void mouseReleased(MouseEvent e){ 
			marker= counter%2==0 ? X:O; //determines whose turn it is and sets the marker equal to the player's marker
			                            //. If counter%/2!=0 (=odd number), then it's O's turn. 
			                            //If counter%/2==0 (=even number), then it's X's turn. 
			int x=e.getX();
			int y=e.getY();
			
			int pos=whichTile(x,y,size);//establish which tile mouse is on 
		    
		    //conditions for executing preceding "if" statement: mouse is in the area of a tile, 
		    //the tile is unclaimed, and no one has won yet 
		    if (pos>=0 && tiles[pos]==UNCLAIMED && winner== ' ') { 
		      tiles[pos]=marker; //set the current tile equal to the player's marker
		
		      checkForWin();     //check if theres a winner
		      repaint();         //update board 
		      counter++;   //increase counter by one 
		}
		   
	  }
		//Parameters: int x, int y, (x,y= mouse coordinate), int size (length of board)
		//Returns: and integer (representing a tile)
		//Method Description: whichTile(): determines which tile the mouse is on and
		//returns that tile number. If the mouse
		//is not on a tile, the value -1 is returned. 
		int whichTile(int x, int y, int size){ 
			if (inArea(A1,x,y)){
				return 0; 
			}else if (inArea(A2,x,y)){
				return 1; 
			}else if (inArea(A3,x,y)){
				return 2; 
			}else if (inArea(A4,x,y)){
				return 3;
			}else if (inArea(A5,x,y)){
				return 4;
			}else if (inArea(A6,x,y)){
				return 5;
			}else if (inArea(A7,x,y)){
				return 6;
			}else if (inArea(A8,x,y)){
				return 7;
			}else if (inArea(A9,x,y)){
				return 8;
			}else {
				return -1; 
			}
			
		}
		
		//Parameters: Rectangle A, int x, int y (x,y= mouse cooordinates, A=the given tile)
		//Returns: boolean
		//Method Description: inArea(): determines if the mouse coordinates are within
		//the given tile. 
		public boolean inArea (Rectangle A, int x, int y){
    		return x>A.x && x<A.width && y> A.y && y< A.height;  
    		
    	}
	}

	//TicTacToeBoard: creates the board for the Tic Tac Toe game
	private class TicTacToeBoard extends JPanel{

	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	public TicTacToeBoard(){//constructor 
		repaint();  
	}
	//Parameters: Graphics g
	//Returns: void
	//MethodDescription: paint(): paints the board of the TicTacToe game 
	public void paint(Graphics g){
		
		int size=getWidth();
		int h=getHeight();
		
		
		if (g instanceof Graphics2D){//anti-aliasing 
				Graphics2D g2= (Graphics2D)g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		}
				
	
		 
		 g.setColor(Color.black);
		 g.fillRect(0, 0, size,h); //background 
		 
		 //DRAW TILE SEPARATION 
		 g.setColor(Color.white); 
		 g.drawLine(size/3, 0, size/3, size); 
		 g.drawLine (size/3*2,0,size/3*2,size);
		 g.drawLine(0, size/3, size, size/3);
		 g.drawLine (0,size/3*2,size,size/3*2);
		
		 ((Graphics2D) g).setStroke(new BasicStroke(10)); //adjust thickness in line
		
		 //DRAW MARKERS 
		for (int i=0; i<9;i++){
			int x= (int) ((i%3+0.5)*size/3.0); //set position of markers (x and o's)
	        int y= (int) ((i/3+0.5)*size/3.0);
	        int x2= (int) (size/10.0);
	        int y2= (int) (size/8.0);
	        
	        if (tiles[i]==O) { //if the current tile[i] is O, then an O marker is drawn 
	          g.drawOval(x-x2, y-y2, x2*2, y2*2); 
	        }
	        else if (tiles[i]==X) {//if the current tile[i] is X, then an X marker is drawn
	          g.drawLine(x-x2, y-y2, x+x2, y+y2);
	          g.drawLine(x-x2, y+y2, x+x2, y-y2);
	        }
		}
		
		//DRAW STRING NOTIFTY A WIN/DRAW
		g.setFont(font);
		g.setColor(Color.GREEN);
		g.drawString (result, 10,size); //draws the string notifying the end result of the game. 
	}
}
		
	}
