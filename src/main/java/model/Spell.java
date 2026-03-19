package model;

public class Spell {


    private int level;
    private Dice dice;
    private int diceAmount;
    private boolean isAffectedByATKBonus; // ATK bonus
    private String name;
    private int castingTime;
    private int range;
    private boolean verbal;
    private boolean ritual;
    private boolean material;
    private boolean concentration;
    private int concentrationRounds;
    private String description;

    public Spell(int level, Dice dice, int diceAmount, boolean isAffectedByATKBonus, String name, int castingTime, int range, boolean verbal, boolean ritual, boolean material,boolean concentration, String description) {
        this.level = level;
        this.dice = dice;
        this.diceAmount = diceAmount;
        this.isAffectedByATKBonus = isAffectedByATKBonus;
        this.name = name;
        this.castingTime = castingTime;
        this.range = range;
        this.verbal = verbal;
        this.ritual = ritual;
        this.material = material;
        this.concentration = concentration;
        this.concentrationRounds = 0;
        this.description = description;
    }

    public int getLevel() { return level; }
    public String getSpellName() { return name; }
    public int getCastingTime() { return castingTime; }
    public int getRange() { return range; }
    public boolean isVerbal() { return verbal; }
    public boolean isRitual() { return ritual; }
    public boolean isMaterial() { return material; }
    public String getDescription() { return description; }



}
