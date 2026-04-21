package ui;

import Input.InputManager;
import draw.AssetManager;
import draw.SheetRenderer;
import javafx.scene.canvas.GraphicsContext;

import static draw.SheetRenderer.BASE_OFFSET;
import static draw.SheetRenderer.DISTANCE_BETWEEN_ROWS;

public class TabBlock extends Widget{

    public static String activeTab = "Stats";
    private static final double ASSET_SCALE_TAB_X = SheetRenderer.ASSET_SCALE_TAB_X;
    private static final double ASSET_SCALE_TAB_Y = SheetRenderer.ASSET_SCALE_TAB_Y;

    private String tabName;
    private boolean isActive;
    private String key;
    private double inputX;

    private double newX;

    public TabBlock(double x, double y, String tabName, boolean isActive){
        super(x,y);
        this.tabName = tabName;
        this.isActive = isActive;

        if(isActive){
            key = "tabActive";
        }else{
            key = "tabInactive";
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        double x = this.x;
        double y = this.y;

        double currentH;
        double currentW;

        if(isActive) {
            currentW = sx(AssetManager.getWidth("tabActive") * ASSET_SCALE_TAB_X);
            currentH = sy(AssetManager.getHeight("tabActive") * ASSET_SCALE_TAB_Y);
        }else {
            currentW = sx(AssetManager.getWidth("tabInactive") * ASSET_SCALE_TAB_X);
            currentH = sy(AssetManager.getHeight("tabInactive") * ASSET_SCALE_TAB_Y);
        }
        // Tab-Bild zeichnen //

        gc.drawImage(AssetManager.get(key), x, y, currentW, currentH);

        // Text zeichnen
        gc.setFont(isActive ? AssetManager.getFontLarge() : AssetManager.getFontMedium()); // Optional: Font anpassen

        // Textkoordinaten skalieren
        double textX = x + sx(10);
        double textY = y + sy(15) + (AssetManager.getHeight("tabActive") / ASSET_SCALE_TAB_Y);
        gc.fillText(tabName, textX, textY);

        // X-Koordinate für den nächsten Tab verschieben
        this.newX = x + currentW + sx(10);
    }

    @Override
    public void handleInput() {

        double tabImgW = sx(AssetManager.getWidth("tabActive") * ASSET_SCALE_TAB_X);
        double tabImgH = sy(AssetManager.getHeight("tabActive") * ASSET_SCALE_TAB_Y); // BUGFIX: Hier war vorher TAB_SCALE_X
        double curX = inputX;
        double startY = sy(BASE_OFFSET);

        if (InputManager.wasClicked((int)curX, (int)startY, (int)tabImgW, (int)tabImgH)) {
            activate();
        }

    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }

    public void setInputX(double x){
        this.inputX = x;
    }

    public double getInputX(){
        return this.inputX;
    }

    public double getNewX(){
        return this.newX;
    }

    public String getKey(){
        return this.key;
    }

    public String getTabName(){
        return this.tabName;
    }

    public void activate(){
        this.isActive = true;
        this.key = "tabActive";
    }

    public void deactivate(){
        this.isActive = false;
        this.key = "tabInactive";
    }
}