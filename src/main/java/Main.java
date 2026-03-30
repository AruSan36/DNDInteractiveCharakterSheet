// Main.java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import model.DNDCharacter;
import model.Dice;

import java.io.File;
import java.util.Optional;

public class Main extends Application {

    static int W = 720;
    static int H = 1280;

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
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        Scene scene = new Scene(root, W, H);

        stage.setTitle("D&D Character Sheet");
        stage.setResizable(true);
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
                update();
                render(canvas);
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
        c.setProficiencyBonus(2);
        c.setIntelligenceProficiency(0, true);
        c.setIntelligenceExpertise(1, true);
        return c;
    }

    private void update() {
    }

    private void render(Canvas canvas) {
        double currentW = canvas.getWidth();
        double currentH = canvas.getHeight();

        gc.clearRect(0, 0, currentW, currentH);
        // Background 4x skaliert
        gc.drawImage(AssetManager.get("background"), 0, 0, currentW, currentH);
        SheetRenderer.render(gc, character,currentW,currentH);
        InputManager.reset();
    }

}


