import javax.swing.*;

public class Window_main extends JFrame {
    Window_main(){
        this.add(new GameScene());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    public static void main(String[] args) {

        new Window_main();}
}
