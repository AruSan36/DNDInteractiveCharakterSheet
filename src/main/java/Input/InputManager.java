package Input;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InputManager {

    // ── Maus ──────────────────────────────────────────
    private static double mouseX = 0;
    private static double mouseY = 0;
    private static boolean mouseClicked   = false;
    private static boolean mousePressed   = false;
    private static boolean mouseReleased  = false;

    // ── Tastatur ──────────────────────────────────────
    private static String  lastTypedChar  = "";
    private static boolean backspace      = false;
    private static boolean enter          = false;

    // ═════════════════════════════════════════════════
    //  Methoden die Main.java aufruft (Event-Callbacks)
    // ═════════════════════════════════════════════════

    public static void mouseClicked(MouseEvent e) {
        mouseX       = e.getX();
        mouseY       = e.getY();
        mouseClicked = true;
    }

    public static void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public static void mousePressed(MouseEvent e) {
        mouseX       = e.getX();
        mouseY       = e.getY();
        mousePressed = true;
    }

    public static void mouseReleased(MouseEvent e) {
        mouseReleased = true;
    }

    public static void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case BACK_SPACE -> backspace = true;
            case ENTER      -> enter     = true;
            default         -> {}
        }
    }

    public static void keyTyped(KeyEvent e) {
        String c = e.getCharacter();
        if (!c.isEmpty() && c.charAt(0) >= 32) {
            lastTypedChar = c;
        }
    }

    // ═════════════════════════════════════════════════
    //  Getter – werden von Widgets aufgerufen
    // ═════════════════════════════════════════════════

    public static double getMouseX() { return mouseX; }
    public static double getMouseY() { return mouseY; }

    public static boolean isOver(double x, double y, double w, double h) {
        return mouseX >= x && mouseX <= x + w
                && mouseY >= y && mouseY <= y + h;
    }

    public static boolean wasClicked(double x, double y, double w, double h) {
        return mouseClicked && isOver(x, y, w, h);
    }

    public static boolean wasPressed(double x, double y, double w, double h) {
        return mousePressed && isOver(x, y, w, h);
    }

    // ← NEU: prüft ob irgendwo geklickt wurde (für InputField Deaktivierung)
    public static boolean wasAnyClick() { return mouseClicked; }

    public static String  getLastTypedChar() { return lastTypedChar; }
    public static boolean wasBackspace()     { return backspace; }
    public static boolean wasEnter()         { return enter; }

    // ═════════════════════════════════════════════════
    //  Reset – MUSS am Ende jedes Frames aufgerufen werden!
    // ═════════════════════════════════════════════════

    public static void reset() {
        mouseClicked  = false;
        mousePressed  = false;
        mouseReleased = false;
        lastTypedChar = "";
        backspace     = false;
        enter         = false;
    }
}

