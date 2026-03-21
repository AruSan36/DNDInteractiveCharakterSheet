// Main.java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import model.DNDCharacter;
import model.Dice;

import java.io.File;
import java.util.Optional;

public class Main extends Application {

    static final int W = 720;
    static final int H = 1280;

    private GraphicsContext gc;
    private DNDCharacter character;

    @Override
    public void start(Stage stage) {
        AssetManager.loadAll();

        Canvas canvas = new Canvas(W, H);
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        character = loadOrCreateCharacter(stage);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, W, H);

        stage.setTitle("D&D Character Sheet");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(e  -> InputManager.keyPressed(e));
        scene.setOnKeyTyped(e    -> InputManager.keyTyped(e));
        canvas.setOnMouseClicked(e  -> InputManager.mouseClicked(e));
        canvas.setOnMouseMoved(e    -> InputManager.mouseMoved(e));
        canvas.setOnMousePressed(e  -> InputManager.mousePressed(e));
        canvas.setOnMouseReleased(e -> InputManager.mouseReleased(e));

        AnimationTimer loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
                render(now);
            }
        };
        loop.start();
    }

    private DNDCharacter loadOrCreateCharacter(Stage stage) {
        File saveFile = new File("save/character.json");
        if (saveFile.exists()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Charakter laden");
            alert.setHeaderText("Gespeicherter Charakter gefunden");
            alert.setContentText("Möchtest du den gespeicherten Charakter laden?");
            ButtonType btnLoad = new ButtonType("Laden");
            ButtonType btnNew  = new ButtonType("Neuer Charakter");
            alert.getButtonTypes().setAll(btnLoad, btnNew);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == btnLoad) {
                return createTestCharacter(); // Platzhalter
            }
        }
        return createTestCharacter();
    }

    private DNDCharacter createTestCharacter() {
        DNDCharacter c = new DNDCharacter(
                "Thorin", "Dwarf", "Fighter", 5, "Soldier", Dice.D10
        );
        c.setStrength(16);
        c.setDexterity(12);
        c.setConstitution(14);
        c.setIntelligence(10);
        c.setWisdom(13);
        c.setCharisma(8);
        return c;
    }

    private void update(long now) { }

    private void render(long now) {
        gc.clearRect(0, 0, W, H);
        // Background 4x skaliert
        gc.drawImage(AssetManager.get("background"), 0, 0, W, H);
        SheetRenderer.render(gc, character, now);
        InputManager.reset();
    }

    public static void main(String[] args) { launch(args); }
}


