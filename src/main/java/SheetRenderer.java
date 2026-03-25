import javafx.scene.canvas.GraphicsContext;
import model.DNDCharacter;

import static javafx.scene.text.Font.font;

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
    private static final double STATS_SCALE_X = 3;
    private static final double STATS_SCALE_Y = 3;

    /*
    * Der haupt Renderer, geht Stück für Stück alle anderen Renderer ab
    * */
    public static void render(GraphicsContext gc, DNDCharacter character) {
       x = 10;
       y = 10;
       renderTab(gc);
       if(activeTab == 0) {
           renderRow2(gc, character);
           renderRow3(gc, character);
           renderStats(gc, character);
       }
       handleInput();

    }

    /*
     * DER RENDERE Für die oberer Tab Reihe
     * startet bei x = 10 und y = 10
     * überprüfe manuell ob die Jweiligen Layout verhältnisse eingehalten wurden
     * */
    private static void renderTab(GraphicsContext gc) {
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
                    x += (int) (AssetManager.getWidth("tabActive") * TAB_SCALE_X + 10); //Veränderre die X Koordinate um den Tab 10 px neben dem andern zu rendern

                } else {
                    gc.drawImage(AssetManager.get("tabInactive"), x, y, AssetManager.getWidth("tabInactive") * TAB_SCALE_X, AssetManager.getHeight("tabInactive") * TAB_SCALE_Y);
                    x += (int) (AssetManager.getWidth("tabInactive") * TAB_SCALE_X + 10);//Veränderre die X Koordinate um den Tab 10 px neben dem andern zu rendern
                }

            }
        }
        //die Koordinaten so verändern das der nächste rendere dort anfängt wo er soll
        x = 10;
        y += TAB_HEIGHT + distanceBetwennRows;

    }

    /*
    * Rendere für die 2 Reihe
    * */
    private static void renderRow2(GraphicsContext gc, DNDCharacter character) {
        //TODO die Assest hierfür machen das das ganze so noch nicht fertig ist
        x = 10;
        y+= ROW_2HEIGHT + distanceBetwennRows;
    }

    /*
    * Renderer für die 3 Reihe
    * */
    private static void renderRow3(GraphicsContext gc, DNDCharacter character) {
        x = 10;
        y+= ROW_3HEIGHT + distanceBetwennRows;
    }

    //*
    // -----------------------------------------------
    // -----------------------------------------------
    //  STATS RENDERING UND ALLE HILFSMETHODEN--------
    // -----------------------------------------------
    // -----------------------------------------------
    // */

    /*
    *Renderer für die Stats Reihe
    * */
    private static void renderStats(GraphicsContext gc, DNDCharacter character) {

        int saveY = y;


        //x += (int) (AssetManager.getWidth("statTop") * STATS_SCALE_X + 10);
        //y += (int) (AssetManager.getHeight("statTop") * STATS_SCALE_Y + 10);

        for(int i = 0 ; i < 8; i++){
            if(i < 2) {
                drawStatWidget(gc, character ,i);
            }



        }

    }

    //*
    // Hilfsmethode für das StatRendering
    // */
    private static void drawStatWidget(GraphicsContext gc, DNDCharacter character, int i) {

        int countOfProf = 1; // um später zu verechnen um wie viele Pixel y verschoben werden muss um das nächste Widget zu rendern
        //Der TOP
        gc.drawImage(AssetManager.get("statTop"), x, y, AssetManager.getWidth("statTop") * STATS_SCALE_X, AssetManager.getHeight("statTop") * STATS_SCALE_Y);

        //Die Nummern zu dem Stat und der Header
        int [] statModifierCords = new int[]{(int) (x + (10* STATS_SCALE_X)),(int) (y + (30 * STATS_SCALE_Y))};
        int [] statCords = new int[]{(int) (x + (27 * STATS_SCALE_X)),(int) (y + (30 * STATS_SCALE_Y))};

        String statNum = "";
        String statModifier ="";

        int statValue = switch (i) {
            case 0 -> character.getIntelligence();
            case 1 -> character.getCharisma();
            case 2 -> character.getWisdom();
            case 3 -> character.getStrength();
            case 4 -> character.getDexterity();
            case 5 -> character.getConstitution();
            default -> 0;
        };
        int modValue = switch (i) {
            case 0 -> character.getIntelligenceModifier();
            case 1 -> character.getCharismaModifier();
            case 2 -> character.getWisdomModifier();
            case 3 -> character.getStrengthModifier();
            case 4 -> character.getDexterityModifier();
            case 5 -> character.getConstitutionModifier();
            default -> 0;
        };
        statNum      = statValue + "";
        statModifier = modValue >= 0 ? "+" + modValue : modValue + "";

        gc.setFont(AssetManager.getFontExtraLarge());
        gc.fillText(statNum, statCords[0], statCords[1]);
        gc.fillText(statModifier, statModifierCords[0], statModifierCords[1]);

         //hier wird die Proficinecies gezeichnet
        countOfProf = drawStatProficiencies(gc, character, i, countOfProf);

        y += (int) ((AssetManager.getHeight("statTop") + (AssetManager.getHeight("profRow") * countOfProf)) * STATS_SCALE_Y + 10);
        gc.drawImage(AssetManager.get("statBottom"), x, y, AssetManager.getWidth("statBottom") * STATS_SCALE_X, AssetManager.getHeight("statBottom") * STATS_SCALE_Y);
        y += (int) (AssetManager.getHeight("statBottom") * STATS_SCALE_Y + 10);
    }
    /*
    *   Übernimmt das Rendern der Proficiency Zeilen
    * */
    private static int drawStatProficiencies(GraphicsContext gc, DNDCharacter character, int i, int countOfProf) {
        //Array Wird ausgewählt je nach Proficiency
        DNDCharacter.Proficiency[] profs = switch (i) {
            case 0 -> character.getIntelligenceProficiencies();
            case 1 -> character.getWisdomProficiencies();
            case 2 -> character.getCharismaProficiencies();
            case 3 -> character.getStrengthProficiencies();
            case 4 -> character.getDexterityProficiencies();
            case 5 -> character.getConstitutionProficiencies();
            default -> new DNDCharacter.Proficiency[0];
        };
        //Startpostiionen der Proficiency Zeilen und des Textes der jweiligen Zeilen
        int sPosProfX   = 9;
        int sPosProfY   = 42;
        int sPosProfTxtX = 19;
        int sPosProfTxtY = 42;

        for (DNDCharacter.Proficiency prof : profs) {
            String key = prof.isExpertise()  ? "expertise"
                    : prof.isProficient() ? "profRowFilled"
                    : "profRow";

            gc.drawImage(AssetManager.get(key),
                    x + (sPosProfX   * STATS_SCALE_X),
                    y + (sPosProfY   * STATS_SCALE_Y),
                    AssetManager.getWidth(key)  * STATS_SCALE_X,
                    AssetManager.getHeight(key) * STATS_SCALE_Y);

            sPosProfTxtY = drawProfTxt(gc, prof, sPosProfTxtX, sPosProfTxtY);
            sPosProfY += 10;
            countOfProf++;
        }

        return countOfProf;
    }

    private static int drawProfTxt(GraphicsContext gc, DNDCharacter.Proficiency prof, int sPosProfTxtX, int sPosProfTxtY) {
        gc.setFont(AssetManager.getFontLarge());
        gc.fillText(prof.getName(), x + (sPosProfTxtX * STATS_SCALE_X), y + (sPosProfTxtY * STATS_SCALE_Y)+15);
        gc.fillText(prof.getBonus() > 0 ? "+" + prof.getBonus() : "" + prof.getBonus(), x -50 + (sPosProfTxtX * STATS_SCALE_X), y + (sPosProfTxtY * STATS_SCALE_Y)+15);
        return sPosProfTxtY+10;

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
