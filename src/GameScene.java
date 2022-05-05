



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GameScene extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 1300;//גודל הפאנל (וגודל החלון יחושב אוטומטית ע"י פונקציית pack )
    static final int SCREEN_HEIGHT = 750;
    
    static final int UNIT_SIZE =50; //גודל של גל יחידת משחק - 50 פיקסלים
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; //כמות מקסימאלית של יחידות משחק
    static int DELAY = 175; // מהירות הריצה
    // 2מערכי קוארדינטות
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];




    int bodyParts ;
    int Score;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean welcome= false;
    boolean running = false;
    Timer timer;
    Random random;
    JButton play;
    JButton Exit;
    ImageIcon backgrond_welcome = new ImageIcon("1350welcome.jpg");
    ImageIcon backgrond_Game = new ImageIcon("background.jpg");
    ImageIcon backgrond_Gameover = new ImageIcon("1350.jpg");
    ImageIcon apple= new ImageIcon("apple.png");
    ImageIcon GoldApple= new ImageIcon("goldapple.png");
    Font myDefaultFont= new Font("Arial", Font.BOLD,100);


    GameScene(){

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());



    }
    public void ResetGame(){
        Exit.setVisible(false);
        play.setVisible(false);
        welcome=false;
        DELAY=175;
        Score =0;
        running=true;
        bodyParts = 5;
        direction = 'R';

        for (int i=0; i<x.length; i++){
            x[i]=50;
        }
        for (int i=0; i<y.length; i++){
            y[i]=50;
        }
        welcome=true;
        newApple();
        running = true;

        timer = new Timer(DELAY,this);
        timer.start();

        repaint();


    }

    public void Welcome(Graphics g){


        play = new JButton("PLAY");
        play.setBounds(470, SCREEN_HEIGHT/2, 300, 50);

        this.add(play);
        play.addActionListener((event) -> {
            ResetGame();

        });
        Exit = new JButton("Exit");
        Exit.setBounds(470, (SCREEN_HEIGHT/2)+100, 300, 50);

        this.add(Exit);
        Exit.addActionListener((event) -> {
            System.exit(0);

        });



//   startGame();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(!welcome){
            Welcome(g);
            g.drawImage(backgrond_welcome.getImage(),0,0,null);

        }

        if(running) {
            g.drawImage(backgrond_Game.getImage(),0,0,null);
            g.setColor(new Color(139,69,2));
            g.fillRect(0,0,50,720);//שמאל
            g.fillRect(0,0,1280,50);//למעלה
            g.fillRect(1250,0,50,750);//ימין
            g.fillRect(0,700,1280,50);//למטה

            g.setColor(Color.gray);
            for(int i=0 ; i<SCREEN_WIDTH;i++){
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);

            }
            for(int i=0 ; i<SCREEN_HEIGHT;i++){
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);

            }


            if(Score %5==0 && Score !=0) {

                g.drawImage(GoldApple.getImage(),appleX,appleY,null);
            }else {

                g.drawImage(apple.getImage(),appleX,appleY,null);
            }



            for(int i = 0; i< bodyParts;i++) {
                if(i == 0) {
                    g.setColor(Color.blue);
                    g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);


                }
                else {
                    g.setColor(new Color(51,204,255));
                    g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.GREEN);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+ Score, (SCREEN_WIDTH - metrics.stringWidth("Score: "+ Score))/2, g.getFont().getSize());

            if((Score -3)% 5 ==0 &&  Score !=3 ){
                g.setColor(new Color(255,215,0));
                g.setFont( new Font("Ink Free",Font.BOLD, 40));
                g.drawString("Bonus!!", ((SCREEN_WIDTH - metrics.stringWidth("Score: "+ Score))/2)+200, g.getFont().getSize());
            }



        }
        if(!running&& welcome){
            gameOver(g);
        }

    }
    public void newApple(){
        appleX = random.nextInt((int)((SCREEN_WIDTH)/UNIT_SIZE)-2)*UNIT_SIZE+50;
        appleY = random.nextInt((int)((SCREEN_HEIGHT)/UNIT_SIZE)-2)*UNIT_SIZE+50;



//
    }

    public void move(){
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            if(Score %5==0&& Score !=0)
                Score +=3;
            else {
                Score++;
            }

            newApple();
        }
    }
    public void checkCollisions() {

        for(int i = bodyParts;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;
            }
        }

        if(x[0] < 50) {
            running = false;
        }

        if(x[0] >= SCREEN_WIDTH-50) {
            running = false;
        }

        if(y[0] < 50) {
            running = false;
        }

        if(y[0] >= SCREEN_HEIGHT-50) {
            running = false;
        }

        if(!running) {
            timer.stop();

        }
    }
    public void gameOver(Graphics g) {
        g.drawImage(backgrond_Gameover.getImage(),0,0,null);
        //Score
        g.setColor(Color.red);
        g.setFont( new Font("David",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+ Score, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+ Score))/2, g.getFont().getSize());
        //Game Over
        g.setColor(Color.red);
        g.setFont( new Font("Arial",Font.BOLD, 100));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over" , (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        g.setFont( new Font("Arial",Font.BOLD, 45));
        g.drawString("Press Enter To Restart",(SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, (SCREEN_HEIGHT/2)+70);



    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }

            if(e.getKeyCode()== KeyEvent.VK_ENTER){
                if(!running){

                    ResetGame();


                }

            }

        }
    }

}
