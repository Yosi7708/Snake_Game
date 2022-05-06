import javax.swing.*;

public class Window_main extends JFrame {
    Window_main() {
        // הגדרת חלון בסיסית
        this.add(new GameScene());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        //פונקצייה מובנית הבונה את גודל החלון בהתאם לגודל הפנלים
        this.pack();
        //נראות ומיקום החלון
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // יצירת חלון משחק חדש
        new Window_main();
    }
}
