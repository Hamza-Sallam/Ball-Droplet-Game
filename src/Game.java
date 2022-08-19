import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, MouseListener{
    // create objects
	private JFrame Over; // the game over frame
	private int width,height,xb,yb,xv,yv,yd1,yd2,yd3,yv1,yv2,yv3; //coordinates of each object and velocity
	private  int lifecount;
	private JButton restart;
	private JMenu Gamee, Debug;
	private JMenuBar menu;
	private JMenuItem start,pause,exit,speedball,speeddrops;
	private Image cloud;
	private Icon ib,drop;
	private Timer timer;
	private int xd1,xd2,xd3;
	private Random ran;
	private String lifeis;
	private Font font;
	private Rectangle b,d1,d2,d3 ; // make new rectangles to detect collision for ball and drops
	private JLabel ball,ldrop1,ldrop2,ldrop3; // jlabels for the images 
	//constructor
	//*********************************************************************************************
	public Game() {
		//initialize positions and velocities
		yd1= 0;                                 yv1=1;
		yd2= 0; /* y positions for each drop */ yv2=2;  //y velocities for each drop
		yd3= 0;                                 yv3=3;
		ran = new Random();
		xd1= ran.nextInt(660); //random x positions for each drop
		xd2= ran.nextInt(650);
		xd3= ran.nextInt(650);
		xv=3; yv=3; // velocity for ball
		xb=250; yb=300; // for ball
		//
	//CREATE IMAGES
 ib = new ImageIcon("ball.png");
 drop = new ImageIcon("droplet.png");
 cloud = new ImageIcon("cloud.png").getImage();
		ball = new JLabel(ib);
		ldrop1 = new JLabel(drop);
		ldrop2 = new JLabel(drop);
		ldrop3 = new JLabel(drop);
		//initialize
		Over= new JFrame();
		lifeis="Life is: ";
		font= new Font("serif", Font.BOLD,20);
		setLayout(new BorderLayout());
		restart = new JButton("restart");
		menu = new JMenuBar();
		Gamee= new JMenu("Game");
		Debug= new JMenu("Debug");
		menu.add(Gamee);
		menu.add(Debug);
		start = new JMenuItem("Start");
		pause = new JMenuItem("Pause");
		exit = new JMenuItem("Exit");
		speedball = new JMenuItem("change the ball speed");
		speeddrops = new JMenuItem("change the droplet speed");
		Gamee.add(start);
		Gamee.add(pause);
		Gamee.add(exit);
		Debug.add(speedball);
		Debug.add(speeddrops);
		lifecount=25; // default life is 25
		add(menu, BorderLayout.NORTH); // make the menu bar at top of the frame
		//action and mouse listener
		restart.addActionListener(this);
		speedball.addActionListener(this);
		speeddrops.addActionListener(this);
		start.addActionListener(this);
		pause.addActionListener(this);
		exit.addActionListener(this);
		ball.addMouseListener(this);
		ldrop1.addMouseListener(this);
		ldrop2.addMouseListener(this);
		ldrop3.addMouseListener(this);
		//timer
		timer = new Timer(10,this);
		timer.start();
		//width and height of the game frame
		width = 700; height=500;
		//
		setSize(width,height);
		setVisible(true);
		setBackground(new Color(102,178,255));
		add(ball);
		add(ldrop1);
		add(ldrop2);
		add(ldrop3);
		} //END OF THE CONSTRUCTOR
	//**************************************************************************************************
	
//main method
	public static void main(String[] args) {
		//start the game by creating a new frame and run it for our game panel
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.setSize(game.getSize());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
	}
	
	@Override
	public void paint(Graphics g)  { //paint the clouds and the life count
		// TODO Auto-generated method stub
		super.paint(g);
        g.drawImage(cloud,0,10,null);
        g.drawImage(cloud,100,10,null);
        g.drawImage(cloud,200,10,null);
        g.drawImage(cloud,300,10,null);
        g.drawImage(cloud,400,10,null);
        g.drawImage(cloud,500,10,null);
        g.drawImage(cloud,600,10,null);
		g.setFont(font);
		g.drawString(lifeis+lifecount,0,450);
		}
		//
	
	public void gameover() {  // create a new frame when game is over
        Icon go = new ImageIcon("gameover.png"); 
	    JLabel gameover = new JLabel(go);
	    JLabel tryagain = new JLabel("press restart to try again");
		JPanel oo = new JPanel();
	   oo.setLayout(null); // set it to null to freely adjust locations
	   //set locations and colors
	   tryagain.setBounds(130,300,150,20);
	   tryagain.setForeground(Color.white);
	    restart.setBounds(140,340,100,20);
	    restart.setBackground(new Color(162,162,162));
		gameover.setBounds(0,0,400,300);
		oo.setBackground(new Color(32,32,32));
       // add components to the panel
	    oo.add(gameover);
	    oo.add(tryagain);
	    oo.add(restart);
        // call the static frame and add our panel
		Over.setSize(400,400);
		Over.setVisible(true);
		Over.setResizable(false);
		Over.setDefaultCloseOperation(Over.EXIT_ON_CLOSE);
		Over.add(oo);
		lifecount =0;
	    //stop the timer when game is over
		timer.stop();
	} // end of gameover
	
	
	//*********************************************************************************************************
	//method for droplets animation and collision detection
	public void droplets() {
		//set new locations for each drop
		ldrop1.setBounds(xd1,yd1,65,65); 
		ldrop2.setBounds(xd2,yd2,65,65);
		ldrop3.setBounds(xd3,yd3,65,65);
		//set the rectangles for drops and ball and set its boundries to the label boundries by getBounds()
		b= new Rectangle(ball.getBounds()); 
		d1 = new Rectangle(ldrop1.getBounds());
		d2 = new Rectangle(ldrop2.getBounds());
		d3 = new Rectangle(ldrop3.getBounds());
		//move each drop downwards y adding the y coordinates with the velocity
		yd1+=yv1; //yd : stands for y drop and yv: stands for y velocity
		yd2+=yv2; // each drop have diffrent velocity because i want each drop to have diffrent speeds
		yd3+=yv3;
	 if(yd1>= height && lifecount<=0) { //if drop reaches the bottom and the life <= zero, call gameover
	gameover();
	}
	else if (yd1>= height && lifecount >0)  { // if it reaches the bottom but life is still more than 0 , print it back from up and loose 2 life
			  lifecount-=2;
			  xd1 = ran.nextInt(650); // when it come back it comes at random x positions
			  yd1=1;
		   }
	 // same thing for drop 2
		 if(yd2>= height && lifecount<=0) {
		gameover();
		}
		else if (yd2>= height && lifecount >0) { 
	           lifecount-=2;
	           xd2 = ran.nextInt(450);  
	           yd2=1;
			   }
		 //for drop 3:  
		 if(yd3>= height && lifecount <=0) {
			gameover();
			}
			else if (yd3>= height && lifecount >0) { 
					  lifecount-=2;
					  xd3 = ran.nextInt(450);
					  yd3=1;
				   }
	 // detect collision using the rectangles method intersects() using our modified rectangles above d1,d2,d3 and b

		 // for drop 1:
		if(b.intersects(d1) && lifecount <=0) { // if the ball and drop intersects, and life was 0, call gameover
			gameover();
		}
		else if(b.intersects(d1) && lifecount > 0) { // if ball and drops collide and life was still above 0, loose 5 lives and print the drop back from up
			 lifecount-=5;
			  xd1 = ran.nextInt(650); // random x position within the frame width
			  yd1=1;
		}
    // same for drop2 :
		if(b.intersects(d2) && lifecount <=0) { 
			gameover();
		}
		else if(b.intersects(d2) && lifecount > 0) {
			 lifecount-=5;
			  xd2 = ran.nextInt(450);
			  yd2=1;
		}
		//for drop  3: 
		if(b.intersects(d3) && lifecount <=0) {
			gameover();
		}
		else if(b.intersects(d3) && lifecount > 0) {
			 lifecount-=5;
			  xd3 = ran.nextInt(450);
			  yd3=1;
		}
		repaint(); // for the life to count properly
	 }// end of droplets

	//*********************************************************************************************************************************

	// method for the final animation
	public void animate () {
		//for ball
		ball.setBounds(xb,yb,100,100); // set the location of the ball
		if(xb>=width-50 || xb<0) { //if the ball reaches up or down it bounce back by inversing the velocity
			xv =xv*-1;
		}
		xb = xb + xv; //otherwise keep moving
		if(yb>=height-50 || yb<0) { // if it touch the left and right borders , bounce back
			yv = yv * -1;
		}
		 yb= yb+yv;
		 //for droplets
		droplets(); // call the droplet method
	} // end of animate
	
	//
	public void ballspeed()  { //method for getting the xvelocity and yvelocity of the ball from the user
		try {

		String s1= JOptionPane.showInputDialog("enter x speed: "); 
		String s2= JOptionPane.showInputDialog("enter y speed");
		xv= Integer.parseInt(s1);
		yv= Integer.parseInt(s2);
	}
		catch(Exception e) { //exception
			JOptionPane.showMessageDialog(this, "enter x and y properly ", "error", JOptionPane.ERROR_MESSAGE);
		}}
	public void dropspeed() { // method for getting the speed the of the droplets from the user
		try {
		String y= JOptionPane.showInputDialog("enter y speed");
		yv1= Integer.parseInt(y);
		yv2= Integer.parseInt(y);
		yv3= Integer.parseInt(y);
	}
		catch(Exception e) { //exception if user didnt input properly
			JOptionPane.showMessageDialog(this, "enter y speed properly ", "error", JOptionPane.ERROR_MESSAGE);
		}}
	//
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(ball)) { // if user clicked the ball , gain 1 life
			lifecount+=1;
		}
		//if user click any droplet, gain 3 lives and print it again from top
		 if(e.getSource().equals(ldrop1)) {
			yd1=0;
			xd1= ran.nextInt(450);
			lifecount+=3;
		}
		 if( e.getSource().equals(ldrop2)) {
			yd2=0;
			xd2= ran.nextInt(450);
		    lifecount+=3;
		}
		 if( e.getSource().equals(ldrop3)) {
				yd3=0;
				xd3= ran.nextInt(450);
			    lifecount+=3;
			}
	}// end of mouse clicked
	
	
	// method for resetting the game if restart button is pressed
	public void reset() { 
		 // set everything to default
		  Over.dispose(); // close the frame when restart button is pressed
	      timer.start();
	      xd1= ran.nextInt(650);
	      xd2= ran.nextInt(650);;
	      xd3= ran.nextInt(650);
	      yd1=0;
          yd2=0;
          yd3=0;
		  xv=3;
		  yv=3;
		  yv1=1;
		  yv2=2;
		  yv3=3;
          lifecount=25;
	}
	//method for exiting the game when pressing the exit jmenueitem
	public void exit() { 
		System.exit(1);	
	}
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(timer))
		animate(); 
		 if (e.getSource().equals(start)) // if user choose start from game menu, start the game
			timer.start();
		else if(e.getSource().equals(pause)) // if user choose pause , pause the game
			timer.stop();
		else if(e.getSource().equals(exit)) // if user choose exit , exit the game
			exit();
		else if(e.getSource().equals(speedball)) // if user choose speed1 from debug menu, increase the speed of the ball
			ballspeed();
		else if(e.getSource().equals(speeddrops)) // if user choose speeddrops , increase the droplets speed
			dropspeed();
		else if(e.getSource().equals(restart)) {//if user choose restart, close the gameover frame and reset all 
			reset();
		}
	} // end of action preformed
	
	// unimplemented methods
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
		}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
		}

} // end of Game