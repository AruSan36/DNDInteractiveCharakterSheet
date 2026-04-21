package ui;

import draw.SheetRenderer;
import javafx.scene.canvas.GraphicsContext;
import static draw.SheetRenderer.scaleX;
import static draw.SheetRenderer.scaleY;

public abstract class Widget {

    // Position & Größe in BASIS-Pixeln (720×1280)
    protected double x, y;

    public Widget(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Jedes Widget MUSS diese zwei Methoden implementieren
    public abstract void render(GraphicsContext gc);
    public abstract void handleInput();

    // Hilfsmethoden die alle Widgets nutzen können
    protected double sx(double value) { return value * scaleX; }
    protected double sy(double value) { return value * scaleY; }
}