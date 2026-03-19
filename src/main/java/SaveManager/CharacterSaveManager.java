package SaveManager;

import com.google.gson.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CharacterSaveManager {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // Charakter save
    public static void save(Character character, String filePath) throws IOException {
        String json = gson.toJson(character);
        Files.writeString(Path.of(filePath), json);
    }

    // Charakter load
    public static Character load(String filePath) throws IOException {
        String json = Files.readString(Path.of(filePath));
        return gson.fromJson(json, Character.class);
    }

}
