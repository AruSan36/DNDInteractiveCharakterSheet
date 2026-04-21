package ui;

import javafx.scene.canvas.GraphicsContext;
import model.DNDCharacter;
import draw.AssetManager;

public class StatBlock extends Widget{

    private int statIndex;
    private DNDCharacter character;

    private static final double ASSET_SCALE_STATS_X = 3.0;
    private static final double ASSET_SCALE_STATS_Y = 3.0;

    public StatBlock(double x, double y, int statIndex, DNDCharacter character) {
        super(x, y);
        this.character = character;
        this.statIndex = statIndex;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void render(GraphicsContext gc) {
        double y = this.y;

        // 1. TOP ZEICHNEN
        double topW = sx(AssetManager.getWidth("statTop") * ASSET_SCALE_STATS_X);
        double topH = sy(AssetManager.getHeight("statTop") * ASSET_SCALE_STATS_Y);
        gc.drawImage(AssetManager.get("statTop"), x, y, topW, topH);

        // 2. WERTE ABRUFEN
        int statValue = getStatValue(character, statIndex);
        int modValue = getStatModifier(character, statIndex);
        String statName = getStatName(statIndex);
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
        DNDCharacter.Proficiency[] profs = getProficiencies(character, statIndex);
        drawStatProficiencies(gc, profs, x, y);

        // 5. VERBINDUNGSLINIEN ZEICHNEN & Y-KOORDINATE FÜR NÄCHSTES WIDGET BERECHNEN
        double nextY = y;
        if (statIndex < 3) {
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

    }

    private void drawStatProficiencies(GraphicsContext gc, DNDCharacter.Proficiency[] profs, double baseX, double baseY) {
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


    @Override
    public void handleInput() {

    }

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

    public int getStatIndex() {
        return statIndex;
    }
}
