package draw;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.util.HashMap;

public class AssetManager {

    // HashMap = Wörterbuch: Name → Bild
    // "checkbox" → Image(checkbox.png)
    private static final HashMap<String, Image> assets = new HashMap<>();

    private static Font pixellariSmall;
    private static double smallFontSize = 8;

    private static Font pixellariMedium;
    private static double mediumFontSize = 12;

    private static Font pixellariLarge;
    private static double largeFontSize = 16;

    private static Font pixellariExtraLarge;
    private static double extraLargeFontSize = 32;

    private static String fontFamilyName;

    // ═════════════════════════════════════════════════
    //  Laden – einmal beim Start aufrufen!
    // ═════════════════════════════════════════════════

    public static void loadAll() {
        System.out.println(
                AssetManager.class.getResource("/Pixellari.ttf")
        );
        // ── Fonts ──
        Font baseFont = Font.loadFont(
                AssetManager.class.getResource("/Pixellari.ttf").toExternalForm(), 10
        );
        fontFamilyName = baseFont.getFamily();
        if (baseFont == null)
            System.err.println("FEHLER: Pixellari.ttf nicht gefunden!");

        updateFonts(1.0);

        // ── Hintergrund & Panels ──
        load("background",    "backGround.png");
        load("sectionPanel",  "sectionPanel.png");
        load("tooltip",       "ToolTip.png");
        load("PlaceholderPortrait",      "PortraitPlaceholder.png");
        load("Portrait",                            "RealPortrait.png");

        // ── Input Fields ──
        load("inputLarge",    "Input/InpuFieldLarge.png");
        load("inputMedium",   "Input/InputMedium.png");
        load("inputSmall",    "Input/InputSmall.png");

        // ── Buttons ──
        load("btnSave",       "Buttons/SaveButton.png");
        load("btnLoad",       "Buttons/LoadButton.png");
        load("btnAdd",        "Buttons/AddRow.png");
        load("btnDelete",     "Buttons/DeleteRow.png");

        // ── Checkboxen & Death Saves ──
        load("checkbox",         "CheckBoxes/CheckBox.png");
        load("checkboxFilled",   "CheckBoxes/CheckBoxFilled.png");
        load("deathSave",        "CheckBoxes/DeathSave.png");
        load("deathSaveFilled",  "CheckBoxes/DeatSaveFilled.png");

        // ── Tab Bar ──
        load("tabActive",     "TabBar/TabBarActive.png");
        load("tabInactive",   "TabBar/TabBarInactive.png");

        // ── Stat Field ──
        load("statTop",       "StatField/StatFieldTop.png");
        load("statBottom",    "StatField/StatFieldBottom.png");
        load("profRow",       "StatField/StatFieldProficiencyDisplay.png");
        load("profRowFilled", "StatField/StatFieldProficiencyDisplayProficient.png");
        load("expertise",     "StatField/StatFieldExpertise.png");
        load("ConLine4",      "StatField/ConnectionLine4.png");
        load("ConLine6",      "StatField/ConnectionLine6.png");

        System.out.println("Assets geladen: " + assets.size());
    }

    // ═════════════════════════════════════════════════
    //  Privater Helfer – lädt ein einzelnes Bild
    // ═════════════════════════════════════════════════

    private static void load(String key, String filename) {
        try {
            // "file:assets/" bedeutet: Ordner "assets" neben der .jar
            Image img = new Image(AssetManager.class
                .getResourceAsStream("/" + filename));

            if (img.isError()) {
                System.err.println("FEHLER beim Laden: " + filename);
            } else {
                assets.put(key, img);
            }

        } catch (Exception e) {
            System.err.println("FEHLER: " + filename + " → " + e.getMessage());
        }
    }

    public static void updateFonts(double scale) {
        // 2. Nutze Font.font(name, size), das greift auf die bereits registrierte Schrift zu (sehr schnell!)
        pixellariSmall = Font.font(fontFamilyName, smallFontSize * scale);
        pixellariMedium = Font.font(fontFamilyName, mediumFontSize * scale);
        pixellariLarge = Font.font(fontFamilyName, largeFontSize * scale);
        pixellariExtraLarge = Font.font(fontFamilyName, extraLargeFontSize * scale);
    }

    // ═════════════════════════════════════════════════
    //  Getter – wird überall im Programm aufgerufen
    // ═════════════════════════════════════════════════

    public static Image get(String key) {
        Image img = assets.get(key);

        if (img == null) {
            System.err.println("Asset nicht gefunden: " + key);
        }

        return img;
    }

    // Breite & Höhe direkt abfragen
    public static int getWidth(String key) {
        return (int) get(key).getWidth();
    }

    public static int getHeight(String key) {
        return (int) get(key).getHeight();
    }

    public static Font getFontSmall()  { return pixellariSmall; }
    public static Font getFontMedium() { return pixellariMedium; }
    public static Font getFontLarge()  { return pixellariLarge; }
    public static Font getFontExtraLarge()  { return pixellariExtraLarge; }
}
