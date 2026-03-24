import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.DNDCharacter;

public class SheetRenderer {

    private static int activeTab = 0;

    // ═══════════════════════════════════════════════════════════
    //  Layout Konstanten  (720 × 1280)
    //  HÖHEN
    //  ROW1 (Tabs):  1/10 = 128px
    //  ROW2:         1/5  = 256px  (Platzhalter)
    //  ROW3:         1/5  = 256px  (Platzhalter)
    //  ROW4 (Stats): 1/2  = 512px
    //
    //  Breiten
    //  ROW1 (Tabs):  9/10  = 648px
    //  ROW2:         8/10  = 576px  (Platzhalter)
    //  ROW3:         8/10  = 576px  (Platzhalter)
    //  ROW4 (Stats): 8/10  = 576px
    //
    // ═══════════════════════════════════════════════════════════


    private static int x = 10; //current x coordinate
    private static int y = 10; // current y coordinate


    private static final int distanceBetwennRows = 10;
    private static final int OFFSET = 10;
    //----------------------------------------TAB Constants-------------------------------------
    private static final int TAB_HEIGHT = Main.H /10;
    private static final int TAB_WIDTH = Main.W *9/10;
    private static final double TAB_SCALE_X = 2.5;
    private static final double TAB_SCALE_Y = 2;
    private static final int ROW1_Y = 10; // the starting y coordinate of the first row

    //----------------------------------------ROW2 Constants-------------------------------------
    private static final int ROW_2HEIGHT = Main.H /5;
    private static final int ROW_2WIDTH = Main.W * 8/10;
    private static final int ROW2_Y= ROW1_Y + TAB_HEIGHT + distanceBetwennRows; // the starting y coordinate of the second row

    //----------------------------------------ROW3 Constants-------------------------------------
    private static final int ROW_3HEIGHT = Main.H /5;
    private static final int ROW_3WIDTH = Main.W * 8/10;
    private static final int ROW3_Y= ROW2_Y + ROW_2HEIGHT + distanceBetwennRows; // the starting y coordinate of the third row

    //----------------------------------------STATS Constants-------------------------------------
    private static final int STATS_HEIGHT = Main.H /2;
    private static final int STATS_WIDTH = Main.W * 8/10;
    private static final int STATS_Y= ROW3_Y + ROW_3HEIGHT + distanceBetwennRows; // the starting y coordinate of the stats row

    /*
    * Der haupt Renderer, geht Stück für Stück alle anderen Renderer ab
    * */
    public static void render(GraphicsContext gc, DNDCharacter character) {
       x = 10;
       y = 10;
       renderTab(gc);
       handleInput();

    }

    /*
     * DER RENDERE Für die oberer Tab Reihe
     * startet bei x = 10 und y = 10
     * überprüfe manuell ob die Jweiligen Layout verhältnisse eingehalten wurden
     * */
    public static void renderTab(GraphicsContext gc) {
        for(int i = 0; i < 4; i++) {
            if(AssetManager.getHeight("tabActive") < TAB_HEIGHT || AssetManager.getWidth("tabActive") < TAB_WIDTH) {
                switch (i) {
                    case 0 -> gc.fillText("Stats", x + 10, y + 15 + AssetManager.getHeight("tabActive") / TAB_SCALE_Y);
                    case 1 -> gc.fillText("Inventory", x + 10 , y + 15 + AssetManager.getHeight("tabActive") / TAB_SCALE_Y);
                    case 2 -> gc.fillText("Spells", x + 10 , y + 15 + AssetManager.getHeight("tabActive") / TAB_SCALE_Y);
                    case 3 -> gc.fillText("CharacterInfo", x + 10 , y + 15 + AssetManager.getHeight("tabActive") / TAB_SCALE_Y);

                }
                if (i == activeTab) {
                    gc.drawImage(AssetManager.get("tabActive"), x, y, AssetManager.getWidth("tabActive") * TAB_SCALE_X, AssetManager.getHeight("tabActive") * TAB_SCALE_Y);
                    gc.setFont(AssetManager.getFontLarge());
                    x += (int) (AssetManager.getWidth("tabActive") * TAB_SCALE_X + 10);

                } else {
                    gc.drawImage(AssetManager.get("tabInactive"), x, y, AssetManager.getWidth("tabInactive") * TAB_SCALE_X, AssetManager.getHeight("tabInactive") * TAB_SCALE_Y);
                    x += (int) (AssetManager.getWidth("tabInactive") * TAB_SCALE_X + 10);
                }

            }
        }

    }
    //---------------------------------------------------------------------
    //---------------Arbeitet stück für Stück alle Inputs ab---------------
    //---------------------------------------------------------------------
    private static void handleInput() {
        int tabImgW = (int) (AssetManager.getWidth("tabActive") * TAB_SCALE_X);
        int tabImgH = (int) (AssetManager.getHeight("tabActive") * TAB_SCALE_X);
        int curX    = OFFSET;

        for (int i = 0; i < 4; i++) {
            // War dieser Tab geklickt?
            if (InputManager.wasClicked(curX, ROW1_Y, tabImgW, tabImgH)) {
                activeTab = i;   // ← Variable wird getauscht
            }
            curX += tabImgW + distanceBetwennRows;
        }
    }


}
