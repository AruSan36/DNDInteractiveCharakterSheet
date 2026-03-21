import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.DNDCharacter;

public class SheetRenderer {

    private static int activeTab = 0;

    // ═════════════════════════════════════════════════
    //  Layout Konstanten – alle relativ zu W/H
    // ═════════════════════════════════════════════════

    private static final int W   = Main.W;
    private static final int H   = Main.H;
    private static final int GAP = 10;

    // Hilfsmethoden für skalierbare Koordinaten
    private static int x(double ratio) { return (int)(ratio * W); }
    private static int y(double ratio) { return (int)(ratio * H); }

    // ═════════════════════════════════════════════════
    //  Haupt-Render
    // ═════════════════════════════════════════════════

    public static void render(GraphicsContext gc, DNDCharacter character, long now) {
        gc.setImageSmoothing(false);
        drawTabBar(gc);

        switch (activeTab) {
            case 0 -> drawStatsTab(gc, character, now);
            case 1 -> drawInventoryTab(gc, character);
            case 2 -> drawSpellsTab(gc, character);
        }
    }

    // ═════════════════════════════════════════════════
    //  Tab Bar – über dem gesamten Layout
    // ═════════════════════════════════════════════════

    private static void drawTabBar(GraphicsContext gc) {
        String[] tabNames  = {"STATS", "INVENTORY", "SPELLS"};
        // 3 Tabs nebeneinander, je 1/3 der Breite minus Gaps
        int tabW = (W - GAP * 4) / 3;  // ~230px
        int tabH = 40;
        int tabY = GAP;

        for (int i = 0; i < 3; i++) {
            int tabX = GAP + i * (tabW + GAP);
            Image tab = (i == activeTab)
                    ? AssetManager.get("tabActive")
                    : AssetManager.get("tabInactive");

            gc.drawImage(tab, tabX, tabY, tabW, tabH);

            gc.setFont(AssetManager.getFontMedium());
            gc.setFill(i == activeTab ? Color.WHITE : Color.GRAY);
            gc.fillText(tabNames[i], tabX + 10, tabY + tabH - 10);

            if (InputManager.wasClicked(tabX, tabY, tabW, tabH)) {
                activeTab = i;
            }
        }
    }

    // ═════════════════════════════════════════════════
    //  Stats Tab
    // ═════════════════════════════════════════════════

    private static void drawStatsTab(GraphicsContext gc, DNDCharacter character, long now) {
        drawInfoRow(gc, character);      // Obere 3 Boxen
        drawStatColumns(gc, character);  // Stat Widgets links
        drawRightPanels(gc, character);  // Panels rechts
        drawDeathSaves(gc, character);
        drawBottomRow(gc, character);
    }

    // ─────────────────────────────────────────────────
    //  Obere 3 Info-Boxen (Box 1, 2, 3)
    // ─────────────────────────────────────────────────

    private static void drawInfoRow(GraphicsContext gc, DNDCharacter character) {
        // Gemeinsame Y-Position: unter TabBar
        int rowY = GAP + 40 + GAP; // tabH=40 + gap
        int rowH = 190;

        // Box 1: x=10, w=200
        int b1x = GAP, b1w = 200;
        gc.drawImage(AssetManager.get("sectionPanel"), b1x, rowY, b1w, rowH);
        gc.setFont(AssetManager.getFontSmall());
        gc.setFill(Color.BLACK);
        gc.fillText("Name",       b1x + 8, rowY + 20);
        gc.fillText(character.getName(), b1x + 8, rowY + 35);
        gc.fillText("Background", b1x + 8, rowY + 55);
        gc.fillText(character.getBackground() != null ? character.getBackground() : "-", b1x + 8, rowY + 70);

        // Box 2: x=220, w=108
        int b2x = b1x + b1w + GAP, b2w = 108;
        gc.drawImage(AssetManager.get("sectionPanel"), b2x, rowY, b2w, rowH);
        gc.fillText("Race",  b2x + 8, rowY + 20);
        gc.fillText(character.getRace() != null ? character.getRace() : "-", b2x + 8, rowY + 35);
        gc.fillText("Align.", b2x + 8, rowY + 55);
        gc.fillText(character.getAlignment() != null ? character.getAlignment() : "-", b2x + 8, rowY + 70);

        // Box 3: x=338, w=372
        int b3x = b2x + b2w + GAP, b3w = W - b3x - GAP;
        gc.drawImage(AssetManager.get("sectionPanel"), b3x, rowY, b3w, rowH);

        // Klassen dynamisch
        int offsetY = 20;
        gc.fillText("Class: " + character.getClasses().getFirst().getName()
                        + " Lv." + character.getClasses().getFirst().getLevel(),
                b3x + 8, rowY + offsetY);
        offsetY += 15;
        for (DNDCharacter.Class c : character.getClasses()) {
            if (c == character.getClasses().getFirst()) continue;
            gc.fillText("  + " + c.getName() + " Lv." + c.getLevel(), b3x + 8, rowY + offsetY);
            offsetY += 15;
        }
        gc.fillText("Level: " + character.getLevel(), b3x + 8, rowY + offsetY);
        gc.fillText("EXP:   " + character.getExperience(), b3x + 8, rowY + offsetY + 15);
    }

    // ─────────────────────────────────────────────────
    //  Stat Widgets – 2 Spalten links (Box 4-9, 17-18)
    // ─────────────────────────────────────────────────

    private static void drawStatColumns(GraphicsContext gc, DNDCharacter character) {
        int profBonus = 2 + (character.getLevel() - 1) / 4;

        String[] names = {"STR", "DEX", "CON", "INT", "WIS", "CHA"};
        int[] values = {
                character.getStrength(),     character.getDexterity(),
                character.getConstitution(), character.getIntelligence(),
                character.getWisdom(),       character.getCharisma()
        };
        int[] modifiers = {
                character.getStrengthModifier(),     character.getDexterityModifier(),
                character.getConstitutionModifier(), character.getIntelligenceModifier(),
                character.getWisdomModifier(),       character.getCharismaModifier()
        };
        DNDCharacter.Proficiency[][] profs = {
                character.getStrengthProficiencies(),
                character.getDexterityProficiencies(),
                character.getConstitutionProficiencies(),
                character.getIntelligenceProficiencies(),
                character.getWisdomProficiencies(),
                character.getCharismaProficiencies()
        };

        // Box 4 startet bei x=10, y=210
        int startX = GAP;
        int startY = 210;
        int colW   = 88 + GAP; // 98px pro Spalte
        int rowH   = 160;

        for (int i = 0; i < 6; i++) {
            int col = i % 2;
            int row = i / 2;
            int wx  = startX + col * colW;
            int wy  = startY + row * rowH;
            drawStatWidget(gc, wx, wy, names[i], values[i], modifiers[i], profs[i], profBonus);
        }
    }

    // ─────────────────────────────────────────────────
    //  Einzelnes Stat Widget
    // ─────────────────────────────────────────────────

    private static void drawStatWidget(GraphicsContext gc, int x, int y,
                                       String name, int value, int modifier,
                                       DNDCharacter.Proficiency[] proficiencies,
                                       int profBonus) {
        // StatTop (48x29)
        gc.drawImage(AssetManager.get("statTop"), x, y);

        // StatModifier (21x24) zentriert
        gc.drawImage(AssetManager.get("statModifier"), x + 4, y + 3+10);
        gc.setFont(AssetManager.getFontSmall());
        gc.setFill(Color.BLACK);
        String modText = (modifier >= 0 ? "+" : "") + modifier;
        gc.fillText(modText, x + 9, y + 18+10);

        // Verbindungslinien
        gc.setFill(Color.BLACK);
        gc.fillRect(x,      y + 29, 1, 6);
        gc.fillRect(x + 47, y + 29, 1, 6);

        // StatNum (21x14) zentriert
        gc.drawImage(AssetManager.get("statNum"), x + 4 + 21, y + 3+5+10);
        gc.setFill(Color.BLACK);
        gc.fillText(String.valueOf(value), x + 7+ 21, y +14+5+10);

        // StatBottom (48x7)
        gc.drawImage(AssetManager.get("statBottom"), x, y + 49);

        // Name über der Box
        gc.fillText(name, x + 16, y - 2);

        // Proficiencies
        int profY = y + + 3+5+10+26;
        for (DNDCharacter.Proficiency prof : proficiencies) {
            Image rowImg;
            if (prof.isExpertise())       rowImg = AssetManager.get("expertise");
            else if (prof.isProficient()) rowImg = AssetManager.get("profRowFilled");
            else                          rowImg = AssetManager.get("profRow");

            gc.drawImage(rowImg, x, profY);

            int skillMod = modifier
                    + (prof.isProficient() ? profBonus : 0)
                    + (prof.isExpertise()  ? profBonus : 0);
            String skillModText = (skillMod >= 0 ? "+" : "") + skillMod;

            gc.setFont(AssetManager.getFontSmall());
            gc.setFill(Color.BLACK);
            gc.fillText(skillModText + " " + prof.getName(), x + 8, profY + 6);
            profY += 10;
        }
    }

    // ─────────────────────────────────────────────────
    //  Rechte Panels (Box 6-10, 13, 16)
    // ─────────────────────────────────────────────────

    private static void drawRightPanels(GraphicsContext gc, DNDCharacter character) {
        int rightX  = 220;           // nach den 2 Stat-Spalten
        int panelW  = W - rightX - GAP; // 490px

        // ── 4 kleine Boxen nebeneinander (Box 6-9) ──
        int smallY = 210, smallH = 44;
        int smallW = (panelW - GAP * 3) / 4; // ~118px

        String[] smallLabels = {"AC", "Initiative", "Speed", "HP"};
        String[] smallValues = {
                String.valueOf(character.getArmorClass()),
                String.valueOf(character.getInitiative()),
                String.valueOf(character.getSpeed()),
                character.getCurrentHitPoints() + "/" + character.getMaxHitPoints()
        };

        for (int i = 0; i < 4; i++) {
            int bx = rightX + i * (smallW + GAP);
            gc.drawImage(AssetManager.get("sectionPanel"), bx, smallY, smallW, smallH);
            gc.setFont(AssetManager.getFontSmall());
            gc.setFill(Color.BLACK);
            gc.fillText(smallLabels[i], bx + 5, smallY + 14);
            gc.fillText(smallValues[i], bx + 5, smallY + 34);
        }

        // ── LargePanel 1 (Box 10) ──
        int lp1Y = smallY + smallH + GAP; // 264
        int lp1H = 96;
        gc.drawImage(AssetManager.get("sectionPanel"), rightX, lp1Y, panelW, lp1H);
        gc.setFont(AssetManager.getFontSmall());
        gc.setFill(Color.BLACK);
        gc.fillText("Passive Perception: " + character.getPassivePerception(), rightX + 8, lp1Y + 20);
        gc.fillText("Inspiration: " + (character.isHeroicInspiration() ? "YES" : "NO"), rightX + 8, lp1Y + 40);
        gc.fillText("Proficiency Bonus: +" + (2 + (character.getLevel()-1)/4), rightX + 8, lp1Y + 60);

        // ── LargePanel 2 (Box 13) – y=370 ──
        int lp2Y = 370, lp2H = 130;
        gc.drawImage(AssetManager.get("sectionPanel"), rightX, lp2Y, panelW, lp2H);
        gc.fillText("Hit Points: " + character.getCurrentHitPoints() + "/" + character.getMaxHitPoints(), rightX + 8, lp2Y + 20);
        gc.fillText("Temp HP:    " + character.getTempHitPoints(),   rightX + 8, lp2Y + 38);
        gc.fillText("Hit Dice:   " + character.getHitDiceTotal() + "d" + (character.getHitDie() != null ? character.getHitDie().getSides() : "?"), rightX + 8, lp2Y + 56);

        // ── LargePanel 3 (Box 16) – Death Saves – y=510 ──
        int lp3Y = 510, lp3H = 130;
        gc.drawImage(AssetManager.get("sectionPanel"), rightX, lp3Y, panelW, lp3H);
        drawDeathSaves(gc, character, rightX + 8, lp3Y + 10);
    }

    // ─────────────────────────────────────────────────
    //  Death Saves
    // ─────────────────────────────────────────────────

    private static void drawDeathSaves(GraphicsContext gc, DNDCharacter character) {
        // Wird intern über drawRightPanels aufgerufen
    }

    private static void drawDeathSaves(GraphicsContext gc, DNDCharacter character, int x, int y) {
        gc.setFont(AssetManager.getFontSmall());
        gc.setFill(Color.BLACK);
        gc.fillText("DEATH SAVES", x, y + 14);

        gc.fillText("Successes", x, y + 30);
        for (int i = 0; i < 3; i++) {
            Image save = (i < character.getDeathSaveSuccesses())
                    ? AssetManager.get("deathSaveFilled")
                    : AssetManager.get("deathSave");
            gc.drawImage(save, x + 80 + i * 14, y + 22);
        }

        gc.fillText("Failures", x, y + 50);
        for (int i = 0; i < 3; i++) {
            Image save = (i < character.getDeathSaveMiss())
                    ? AssetManager.get("deathSaveFilled")
                    : AssetManager.get("deathSave");
            gc.drawImage(save, x + 80 + i * 14, y + 42);
        }
    }

    // ─────────────────────────────────────────────────
    //  Untere Reihe (Box 19, 20, 21) – y=850
    // ─────────────────────────────────────────────────

    private static void drawBottomRow(GraphicsContext gc, DNDCharacter character) {
        int bottomY = 850;
        int bottomH = H - bottomY - GAP; // ~420px

        // Box 19 – Notizen/Features x=10, w=186
        int b19w = 186;
        gc.drawImage(AssetManager.get("sectionPanel"), GAP, bottomY, b19w, bottomH);
        gc.setFont(AssetManager.getFontSmall());
        gc.setFill(Color.BLACK);
        gc.fillText("FEATURES", GAP + 8, bottomY + 18);

        // Box 20 – Mitte x=206, w=186
        int b20x = GAP + b19w + GAP, b20w = 186, b20h = 200;
        gc.drawImage(AssetManager.get("sectionPanel"), b20x, bottomY, b20w, b20h);
        gc.fillText("LANGUAGES", b20x + 8, bottomY + 18);

        // Box 21 – Rechts x=402, w=308
        int b21x = b20x + b20w + GAP, b21w = W - b21x - GAP;
        gc.drawImage(AssetManager.get("sectionPanel"), b21x, bottomY, b21w, bottomH);
        gc.fillText("EQUIPMENT", b21x + 8, bottomY + 18);
    }

    // ═════════════════════════════════════════════════
    //  Platzhalter Tabs
    // ═════════════════════════════════════════════════

    private static void drawInventoryTab(GraphicsContext gc, DNDCharacter character) {
        gc.setFont(AssetManager.getFontMedium());
        gc.setFill(Color.BLACK);
        gc.fillText("INVENTORY – coming soon", 40, 100);
    }

    private static void drawSpellsTab(GraphicsContext gc, DNDCharacter character) {
        gc.setFont(AssetManager.getFontMedium());
        gc.setFill(Color.BLACK);
        gc.fillText("SPELLS – coming soon", 40, 100);
    }

    public static int  getActiveTab()        { return activeTab; }
    public static void setActiveTab(int tab) { activeTab = tab; }
}
