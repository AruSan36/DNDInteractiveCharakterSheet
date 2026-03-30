import javafx.scene.canvas.GraphicsContext;
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

    // ═══════════════════════════════════════════════════════════
    //  LAYOUT SYSTEM & SKALIERUNG
    // ═══════════════════════════════════════════════════════════

    // Die Auflösung, für die das UI ursprünglich konzipiert wurde
    private static final double BASE_WIDTH = 720.0;
    private static final double BASE_HEIGHT = 1280.0;

    // Aktuelle Skalierungsfaktoren für das Fenster
    private static double scaleX = 1.0;
    private static double scaleY = 1.0;
    private static double lastFontScale = -1.0;

    // Interne Asset-Skalierungen (da die Bilder wohl vergrößert werden müssen)
    private static final double ASSET_SCALE_TAB_X = 2.5;
    private static final double ASSET_SCALE_TAB_Y = 2.0;
    private static final double ASSET_SCALE_STATS_X = 3.0;
    private static final double ASSET_SCALE_STATS_Y = 3.0;

    // Globale Layout-Abstände (in Basis-Pixeln)
    private static final double DISTANCE_BETWEEN_ROWS = 10.0;
    private static final double BASE_OFFSET = 10.0;

    // Hilfsmethoden, um jeden Wert dynamisch an die Fenstergröße anzupassen
    private static double sx(double value) { return value * scaleX; }
    private static double sy(double value) { return value * scaleY; }

    /**
     * Aktualisiert die Skalierungsfaktoren basierend auf der aktuellen Canvas-Größe.
     */
    private static void updateScale(double currentWidth, double currentHeight) {
        scaleX = currentWidth / BASE_WIDTH;
        scaleY = currentHeight / BASE_HEIGHT;

        // Uniforme Skalierung für Schriftarten berechnen
        double currentFontScale = Math.min(scaleX, scaleY);

        // Nur updaten, wenn das Fenster seit dem letzten Frame skaliert wurde!
        if (currentFontScale != lastFontScale) {
            AssetManager.updateFonts(currentFontScale);
            lastFontScale = currentFontScale;
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  HAUPT-RENDERER
    // ═══════════════════════════════════════════════════════════

    /**
     * Der Haupt-Renderer. Hier musst du jetzt die aktuelle Breite und Höhe deines Canvas übergeben.
     */
    public static void render(GraphicsContext gc, DNDCharacter character, double canvasWidth, double canvasHeight) {
        updateScale(canvasWidth, canvasHeight);

        double currentX = sx(BASE_OFFSET);

        // ROW 1: Tabs (startet ganz oben mit 10px Offset)
        double tabsY = sy(BASE_OFFSET);
        renderTabs(gc, currentX, tabsY);

        if (activeTab == 0) {
            // ROW 2: startet bei exakt 1/10 der Basis-Höhe (128 skaliert)
            double row2Y = sy(BASE_HEIGHT * 0.1);
            renderRow2(gc, character, currentX, row2Y);

            // ROW 3: startet bei exakt 3/10 (1/10 + 1/5) der Basis-Höhe (384 skaliert)
            double row3Y = sy(BASE_HEIGHT * 0.3);
            renderRow3(gc, character, currentX, row3Y);

            // ROW 4 (Stats): startet bei exakt der Hälfte der Basis-Höhe (640 skaliert)
            double statsY = sy(BASE_HEIGHT * 0.5);
            renderStats(gc, character, currentX, statsY);
        }

        handleInput();

    }

    // ═══════════════════════════════════════════════════════════
    //  KOMPONENTEN-RENDERER
    // ═══════════════════════════════════════════════════════════

    /**
     * Rendert die obere Tab-Reihe.
     * @return Die neue Y-Koordinate unterhalb der Tabs.
     */
    private static double renderTabs(GraphicsContext gc, double startX, double startY) {
        double x = startX;
        double y = startY;

        double activeW = sx(AssetManager.getWidth("tabActive") * ASSET_SCALE_TAB_X);
        double activeH = sy(AssetManager.getHeight("tabActive") * ASSET_SCALE_TAB_Y);
        double inactiveW = sx(AssetManager.getWidth("tabInactive") * ASSET_SCALE_TAB_X);
        double inactiveH = sy(AssetManager.getHeight("tabInactive") * ASSET_SCALE_TAB_Y);

        String[] tabNames = {"Stats", "Inventory", "Spells", "CharacterInfo"};

        for (int i = 0; i < 4; i++) {
            boolean isActive = (i == activeTab);
            double currentW = isActive ? activeW : inactiveW;
            double currentH = isActive ? activeH : inactiveH;
            String key = isActive ? "tabActive" : "tabInactive";

            // Tab-Bild zeichnen
            gc.drawImage(AssetManager.get(key), x, y, currentW, currentH);

            // Text zeichnen
            gc.setFont(isActive ? AssetManager.getFontLarge() : AssetManager.getFontMedium()); // Optional: Font anpassen

            // Textkoordinaten skalieren
            double textX = x + sx(10);
            double textY = y + sy(15) + (AssetManager.getHeight("tabActive") / ASSET_SCALE_TAB_Y);
            gc.fillText(tabNames[i], textX, textY);

            // X-Koordinate für den nächsten Tab verschieben
            x += currentW + sx(10);
        }

        // Rückgabe der Y-Koordinate für die nächste Zeile
        return y + activeH + sy(DISTANCE_BETWEEN_ROWS);
    }

    private static double renderRow2(GraphicsContext gc, DNDCharacter character, double x, double y) {
        // TODO: Assets implementieren
        double rowHeight = sy(BASE_HEIGHT / 5);
        return y + rowHeight + sy(DISTANCE_BETWEEN_ROWS);
    }

    private static double renderRow3(GraphicsContext gc, DNDCharacter character, double x, double y) {
        // TODO: Assets implementieren
        double rowHeight = sy(BASE_HEIGHT / 5);
        return y + rowHeight + sy(DISTANCE_BETWEEN_ROWS);
    }

    // ═══════════════════════════════════════════════════════════
    //  STATS RENDERING
    // ═══════════════════════════════════════════════════════════

    private static void renderStats(GraphicsContext gc, DNDCharacter character, double startX, double startY) {
        double currentX = startX;
        double currentY = startY;

        // Berechne die Breite eines Widgets für den horizontalen Abstand zur nächsten Spalte
        double widgetWidth = sx(AssetManager.getWidth("statBottom") * ASSET_SCALE_STATS_X);
        double horizontalSpacing = sx(10); // 10 Pixel Abstand zwischen den Spalten (wie im Original)

        for (int i = 0; i < 6; i++) {
            // Nach 3 Stats (also bei i == 3) machen wir einen Zeilenumbruch für die untere Reihe
            if (i == 3) {
                currentX = startX; // X wieder ganz nach links setzen

                // Y nach unten verschieben für die zweite Reihe.
                // Wir nehmen als Platzhalter den maximalen Platz, den die Proficiencies brauchen (9 Reihen)
                double topH = sy(AssetManager.getHeight("statTop") * ASSET_SCALE_STATS_Y);
                double profH = sy(AssetManager.getHeight("profRow") * 5 * ASSET_SCALE_STATS_Y + 10);
                double botH = sy(AssetManager.getHeight("statBottom") * ASSET_SCALE_STATS_Y);

                currentY += topH + profH + botH;
            }

            // Zeichne das aktuelle Widget an (currentX, currentY)
            drawStatWidget(gc, character, i, currentX, currentY);

            // Verschiebe die X-Koordinate nach rechts für das nächste Widget in derselben Zeile
            currentX += widgetWidth + horizontalSpacing;
        }
    }

    private static double drawStatWidget(GraphicsContext gc, DNDCharacter character, int i, double x, double startY) {
        double y = startY;

        // 1. TOP ZEICHNEN
        double topW = sx(AssetManager.getWidth("statTop") * ASSET_SCALE_STATS_X);
        double topH = sy(AssetManager.getHeight("statTop") * ASSET_SCALE_STATS_Y);
        gc.drawImage(AssetManager.get("statTop"), x, y, topW, topH);

        // 2. WERTE ABRUFEN
        int statValue = getStatValue(character, i);
        int modValue = getStatModifier(character, i);
        String statName = getStatName(i);
        String statNum = String.valueOf(statValue);
        String statModifier = modValue >= 0 ? "+" + modValue : String.valueOf(modValue);

        // 3. TEXTE ZEICHNEN
        double numX = x + sx(27 * ASSET_SCALE_STATS_X);
        double numY = y + sy(30 * ASSET_SCALE_STATS_Y);
        double modX = x + sx(10 * ASSET_SCALE_STATS_X);
        double modY = numY;

        gc.setFont(AssetManager.getFontExtraLarge());
        gc.fillText(statNum, numX, numY);
        gc.fillText(statModifier, modX, modY);

        gc.setFont(AssetManager.getFontLarge());
        gc.fillText(statName, x + sx(12 * ASSET_SCALE_STATS_X), y + sy(8 * ASSET_SCALE_STATS_Y));

        // 4. PROFICIENCIES ZEICHNEN
        DNDCharacter.Proficiency[] profs = getProficiencies(character, i);
        drawStatProficiencies(gc, profs, x, y);

        // 5. VERBINDUNGSLINIEN ZEICHNEN & Y-KOORDINATE FÜR NÄCHSTES WIDGET BERECHNEN
        double nextY = y;
        if (i < 3) {
            double lineW = sx(AssetManager.getWidth("ConLine4") * ASSET_SCALE_STATS_X);
            double lineH = sy(AssetManager.getHeight("ConLine4") * ASSET_SCALE_STATS_Y);
            gc.drawImage(AssetManager.get("ConLine4"), x, y + topH, lineW, lineH);
            gc.drawImage(AssetManager.get("ConLine4"), x + topW - sx(1 * ASSET_SCALE_STATS_X), y + topH, lineW, lineH);
            nextY += topH + sy(AssetManager.getHeight("profRow") * 4.5 * ASSET_SCALE_STATS_Y + 10);
        } else {
            double lineW = sx(AssetManager.getWidth("ConLine6") * ASSET_SCALE_STATS_X);
            double lineH = sy(AssetManager.getHeight("ConLine6") * ASSET_SCALE_STATS_Y);
            gc.drawImage(AssetManager.get("ConLine6"), x, y + topH, lineW, lineH);
            gc.drawImage(AssetManager.get("ConLine6"), x + topW - sx(1 * ASSET_SCALE_STATS_X), y + topH, lineW, lineH);
            nextY += topH + sy(AssetManager.getHeight("profRow") * 6.5 * ASSET_SCALE_STATS_Y + 10);
        }

        // 6. BOTTOM ZEICHNEN
        double botW = sx(AssetManager.getWidth("statBottom") * ASSET_SCALE_STATS_X);
        double botH = sy(AssetManager.getHeight("statBottom") * ASSET_SCALE_STATS_Y);
        gc.drawImage(AssetManager.get("statBottom"), x, nextY, botW, botH);

        return nextY;
    }

    private static void drawStatProficiencies(GraphicsContext gc, DNDCharacter.Proficiency[] profs, double baseX, double baseY) {
        double sPosProfX = sx(9 * ASSET_SCALE_STATS_X);
        double sPosProfY = sy(42 * ASSET_SCALE_STATS_Y);
        double sPosProfTxtX = sx(19 * ASSET_SCALE_STATS_X);
        double sPosProfTxtY = sy(42 * ASSET_SCALE_STATS_Y);

        gc.setFont(AssetManager.getFontLarge());

        for (DNDCharacter.Proficiency prof : profs) {
            String key = prof.isExpertise() ? "expertise" : prof.isProficient() ? "profRowFilled" : "profRow";

            double imgW = sx(AssetManager.getWidth(key) * ASSET_SCALE_STATS_X);
            double imgH = sy(AssetManager.getHeight(key) * ASSET_SCALE_STATS_Y);

            gc.drawImage(AssetManager.get(key), baseX + sPosProfX, baseY + sPosProfY, imgW, imgH);

            // Text zeichnen
            String bonusTxt = (prof.getBonus() > 0 ? "+" : "") + prof.getBonus();
            gc.fillText(prof.getName(), baseX + sPosProfTxtX, baseY + sPosProfTxtY + sy(15));
            gc.fillText(bonusTxt, baseX + sPosProfTxtX - sx(50), baseY + sPosProfTxtY + sy(15));

            sPosProfY += sy(30);
            sPosProfTxtY += sy(30);
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  INPUT HANDLING
    // ═══════════════════════════════════════════════════════════

    private static void handleInput() {
        double tabImgW = sx(AssetManager.getWidth("tabActive") * ASSET_SCALE_TAB_X);
        double tabImgH = sy(AssetManager.getHeight("tabActive") * ASSET_SCALE_TAB_Y); // BUGFIX: Hier war vorher TAB_SCALE_X
        double curX = sx(BASE_OFFSET);
        double startY = sy(BASE_OFFSET);

        for (int i = 0; i < 4; i++) {
            // Die Koordinaten müssen wieder auf Integer gecastet werden, falls InputManager int erwartet
            if (InputManager.wasClicked((int)curX, (int)startY, (int)tabImgW, (int)tabImgH)) {
                activeTab = i;
            }
            curX += tabImgW + sx(DISTANCE_BETWEEN_ROWS);
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  HILFSMETHODEN FÜR DATENABRUF
    // ═══════════════════════════════════════════════════════════

    private static int getStatValue(DNDCharacter character, int i) {
        return switch (i) {
            case 0 -> character.getConstitution();
            case 1 -> character.getStrength();
            case 2 -> character.getDexterity();
            case 3 -> character.getWisdom();
            case 4 -> character.getIntelligence();
            case 5 -> character.getCharisma();
            default -> 0;
        };
    }

    private static int getStatModifier(DNDCharacter character, int i) {
        return switch (i) {
            case 0 -> character.getConstitutionModifier();
            case 1 -> character.getStrengthModifier();
            case 2 -> character.getDexterityModifier();
            case 3 -> character.getWisdomModifier();
            case 4 -> character.getIntelligenceModifier();
            case 5 -> character.getCharismaModifier();
            default -> 0;
        };
    }

    private static String getStatName(int i) {
        return switch (i) {
            case 0 -> "Constitution";
            case 1 -> "Strength";
            case 2 -> "Dexterity";
            case 3 -> "Wisdom";
            case 4 -> "Intelligence";
            case 5 -> "Charisma";
            default -> "";
        };
    }

    private static DNDCharacter.Proficiency[] getProficiencies(DNDCharacter character, int i) {
        return switch (i) {
            case 0 -> character.getConstitutionProficiencies();
            case 1 -> character.getStrengthProficiencies();
            case 2 -> character.getDexterityProficiencies();
            case 3 -> character.getWisdomProficiencies();
            case 4 -> character.getIntelligenceProficiencies();
            case 5 -> character.getCharismaProficiencies();
            default -> new DNDCharacter.Proficiency[0];
        };
    }
}
