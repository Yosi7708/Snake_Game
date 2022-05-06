public class Snake_Body {
    // גודל מירבי של איברי משחק (כולל מסגרת ותפוח
    static final int Snake_Max_Body = (GameScene.SCREEN_WIDTH * GameScene.SCREEN_HEIGHT) / GameScene.UNIT_SIZE;
    // 2מערכי קוארדינטות המאותחלים על הערך הגבוה ביותר
    final int x[] = new int[Snake_Max_Body];
    final int y[] = new int[Snake_Max_Body];
    // חלקי הנחש ביחידות
    private int bodyParts;
    // כיוון תזוזת הנחש
    char direction ;
    // גודל כל חלק במשחק ת מאותחל במחלקת הסצנות ובגל זאת ייבאתי לפה לצורך הנוחות
    private int UNIT_SIZE = GameScene.UNIT_SIZE;

    // getters לקוארדינטות
    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }
    // קבלת אורך הנחש בזמן אמת
    public int getBodyParts() {
        return bodyParts;
    }
     // הוספה \ קביעת גודל הנחש
    public void setBodyParts(int bodyParts) {
        this.bodyParts = bodyParts;
    }
    // אלגוריתם תזוזת הנחש
    public void move() {
        // פעולת התקדמות הגוף ביחס לתזוזת הראש
        for (int i = getBodyParts(); i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        // פעולת התקדמות הראש ע"י האזנה למקשי המקלדת
        switch (direction) {
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
}
