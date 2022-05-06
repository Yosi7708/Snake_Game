import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GameScene extends JPanel implements ActionListener {
    // אתחול משתנה תפוח ונחש
    Apple apple = new Apple();
    Snake_Body snake = new Snake_Body();

    static final int SCREEN_WIDTH = 1300;//גודל הפאנל (וגודל החלון יחושב אוטומטית ע"י פונקציית pack )
    static final int SCREEN_HEIGHT = 750;

    static final int UNIT_SIZE = 50; //גודל של גל יחידת משחק - 50 פיקסלים
    static int DELAY = 175; // מהירות הריצה


    int Score;
    //בוליאני להפעלת מסך הפתיחה
    boolean welcome = false;
    // בוליאני לריצת המשחק
    static boolean running = false;
    //מנגון הטיימר
    Timer timer;
    //ייבוא הרנדום לצורך הגרלת מקום התפוח
    Random random;
    // כפתורי הפעלה ויציאה
    JButton play;
    JButton Exit;
    //תמונות ופונטים
    ImageIcon backgrond_welcome = new ImageIcon("1350welcome.jpg");
    ImageIcon backgrond_Game = new ImageIcon("background.jpg");
    ImageIcon backgrond_Gameover = new ImageIcon("1350.jpg");
    Font myDefaultFont = new Font("Arial", Font.BOLD, 100);


    GameScene() {
        KeyActions keyActions = new KeyActions(snake,this);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        random = new Random();
    }
    //אתחול המשתנים בתחילת המשחק ובמקרה של פסילה
    public void ResetGame() {
        Exit.setVisible(false);
        play.setVisible(false);
        welcome = false;
        DELAY = 175;
        Score = 0;
        running = true;
        snake.setBodyParts(5);
        snake.direction = 'R';

        for (int i = 0; i < snake.x.length; i++) {
            snake.x[i] = 50;
        }
        for (int i = 0; i < snake.y.length; i++) {
            snake.y[i] = 50;
        }
        welcome = true;
        newApple();
        running = true;

        timer = new Timer(DELAY, this);
        timer.start();

        repaint();


    }
    // מסך פתיחה

    public void Welcome(Graphics g) {


        play = new JButton("PLAY");
        play.setBounds(470, SCREEN_HEIGHT / 2, 300, 50);
        this.add(play);
        play.addActionListener((event) -> {
            ResetGame();

        });

        Exit = new JButton("Exit");
        Exit.setBounds(470, (SCREEN_HEIGHT / 2) + 100, 300, 50);
        this.add(Exit);
        Exit.addActionListener((event) -> {
            System.exit(0);

        });


    }
    // פונקציות הציור למסך

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (!welcome) {
            //מסך פתיחה
            Welcome(g);
            g.drawImage(backgrond_welcome.getImage(), 0, 0, null);

        }

        if (running) {
            // רקע ומסגרת
            g.drawImage(backgrond_Game.getImage(), 0, 0, null);
            g.setColor(new Color(139, 69, 2));
            g.fillRect(0, 0, 50, 720);//שמאל
            g.fillRect(0, 0, 1280, 50);//למעלה
            g.fillRect(1250, 0, 50, 750);//ימין
            g.fillRect(0, 700, 1280, 50);//למטה

            // קווי רשת
            g.setColor(Color.gray);
            for (int i = 0; i < SCREEN_WIDTH; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);

            }
            for (int i = 0; i < SCREEN_HEIGHT; i++) {
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);

            }

            // סוג התפוח שוצג ומיקומו
            if (Score % 5 == 0 && Score != 0) {

                g.drawImage(apple.GoldApple.getImage(), apple.getAppleX(), apple.getAppleY(), null);
            } else {

                g.drawImage(apple.RedApple.getImage(), apple.getAppleX(), apple.getAppleY(), null);
            }

            // ציור הנחש והפרדת צבע בין הראש לגוף
            for (int i = 0; i < snake.getBodyParts(); i++) {
                if (i == 0) {
                    g.setColor(Color.blue);
                    g.fillOval(snake.x[i], snake.y[i], UNIT_SIZE, UNIT_SIZE);


                } else {
                    g.setColor(new Color(51, 204, 255));
                    g.fillOval(snake.x[i], snake.y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            // ציור הנקודות למשתמש
            g.setColor(Color.GREEN);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + Score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + Score)) / 2, g.getFont().getSize());
             // הודעת נקודות בונוס
            if ((Score - 3) % 5 == 0 && Score != 3) {
                g.setColor(new Color(255, 215, 0));
                g.setFont(new Font("Ink Free", Font.BOLD, 40));
                g.drawString("Bonus!!", ((SCREEN_WIDTH - metrics.stringWidth("Score: " + Score)) / 2) + 200, g.getFont().getSize());
            }


        }
        //פמסך פסילה
        if (!running && welcome) {
            gameOver(g);
        }

    }

    public void newApple() {
        // חישוב המיקומים האפריים ליצירת תפוח כך שלא יפול על המסגרת
        int XrandomLocationPossible= random.nextInt((int) ((SCREEN_WIDTH) / UNIT_SIZE) - 2) * UNIT_SIZE + 50;
        int YrandomLocationPossible = random.nextInt((int) ((SCREEN_HEIGHT) / UNIT_SIZE) - 2) * UNIT_SIZE + 50;

        apple.setAppleX(XrandomLocationPossible);
        apple.setAppleY(YrandomLocationPossible);

    }


    public void checkApple() {
        // אם הנחש וראש התפוח בקוארידנטה מדוייקת הנחש יגדל
        if ((snake.x[0] == apple.getAppleX()) && (snake.y[0] == apple.getAppleY())) {
            snake.setBodyParts(snake.getBodyParts() + 1);
            // תוספת נקודות / בונוס
            if (Score % 5 == 0 && Score != 0) Score += 3;
            else {
                Score++;
            }

            newApple();
        }
    }

    public void checkCollisions() {
        //בדיקת התנגשות עם עצמו
        for (int i = snake.getBodyParts(); i > 0; i--) {
            if ((snake.x[0] == snake.x[i]) && (snake.y[0] == snake.y[i])) {
                running = false;
            }
        }
        // בדיקת התנגשות במסגרת שמאל
        if (snake.x[0] < 50) {
            running = false;
        }
        // בדיקת התנגשות במסגרת ימין
        if (snake.x[0] >= SCREEN_WIDTH - 50) {
            running = false;
        }
        // בדיקת התנגשות למעלה
        if (snake.y[0] < 50) {
            running = false;
        }
        //בדיקת התנגשות למטה
        if (snake.y[0] >= SCREEN_HEIGHT - 50) {
            running = false;
        }
        //פסילה
        if (!running) {
            timer.stop();

        }
    }
      // מסך הפסילה
    public void gameOver(Graphics g) {
        g.drawImage(backgrond_Gameover.getImage(), 0, 0, null);
        // סך הנקודות
        g.setColor(Color.red);
        g.setFont(new Font("David", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Your Score is: " + Score, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + Score)) / 2, g.getFont().getSize());
        //הודעת הפסילה
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        g.setFont(new Font("Arial", Font.BOLD, 45));
        g.drawString("Press Enter To Restart", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, (SCREEN_HEIGHT / 2) + 70);


    }
    // פונקציית ההרצה הנורשת מ ActionEvent
    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            snake.move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }





}
