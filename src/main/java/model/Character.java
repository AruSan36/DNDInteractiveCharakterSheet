package model;

import model.Item.InventoryItem;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Character {

    /*----------------------------------------Header Information-------------------------------------------------*/

    private String name;
    private String race;
    public static class Class{
        int level = 1;
        String name;

        public Class(String name){
            this.name = name;
        }
        public void levelUp(){
            level++;
        }
    }
    private ArrayList<Class> classes = new ArrayList<>();
    private ArrayList<Class> subClasses = new ArrayList<>();

    private int level;
    private int experience = 0;

    private int armorClass = 10;

    private int maxHitPoints = 5;
    private int currentHitPoints = 5;
    private int tempHitPoints = 5;


    private Dice hitDie;
    private int hitDiceSpent = 0;
    private int hitDiceTotal = 0;

    private int deathSaveMiss = 0;
    private int deathSaveSuccesses = 0;

    private int initiative = 0;
    private int speed = 9; // in meters per round
    private float size = 1.60f;
    private int passivePerception = 0;

    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getRace(){return race;}
    public void setRace(String race){this.race = race;}
    public ArrayList<Class> getClasses() { return classes; }
    public void addClass(String name) { classes.add(new Class(name)); }
    public void removeClass(String name) { classes.removeIf(c -> c.name.equals(name)); }
    public ArrayList<Class> getSubClasses() { return subClasses; }
    public void addSubClass(String name) { subClasses.add(new Class(name)); }
    public void removeSubClass(String name) { subClasses.removeIf(c -> c.name.equals(name)); }
    public int getLevel(){return level;}
    public void setLevel(int level){this.level = level;}
    public int getExperience(){return experience;}
    public void setExperience(int experience){this.experience = experience;}
    public int getArmorClass(){return armorClass;}
    public void setArmorClass(int armorClass){this.armorClass = armorClass;}
    public int getMaxHitPoints(){return maxHitPoints;}
    public void setMaxHitPoints(int maxHitPoints){this.maxHitPoints = maxHitPoints;}
    public int getCurrentHitPoints(){return currentHitPoints;}
    public void setCurrentHitPoints(int currentHitPoints){this.currentHitPoints = currentHitPoints;}
    public int getTempHitPoints(){return tempHitPoints;}
    public void setTempHitPoints(int tempHitPoints){this.tempHitPoints = tempHitPoints;}
    public Dice getHitDie(){return this.hitDie;}
    public void setHitDie (Dice newDice){this.hitDie = newDice;}
    public int getHitDiceSpent(){return hitDiceSpent;}
    public void setHitDiceSpent(int hitDiceSpent){this.hitDiceSpent = hitDiceSpent;}
    public int getHitDiceTotal(){return hitDiceTotal;}
    public void setHitDiceTotal(int hitDiceTotal){this.hitDiceTotal = hitDiceTotal;}
    public int getDeathSaveMiss(){return deathSaveMiss;}
    public void setDeathSaveMiss(int deathSaveMiss){this.deathSaveMiss = deathSaveMiss;}
    public int getDeathSaveSuccesses(){return deathSaveSuccesses;}
    public void setDeathSaveSuccesses(int deathSaveSuccesses){this.deathSaveSuccesses = deathSaveSuccesses;}
    public int getInitiative(){return initiative;}
    public void setInitiative(int initiative){this.initiative = initiative;}
    public int getSpeed(){return speed;}
    public void setSpeed(int speed){this.speed = speed;}
    public float getSize(){return size;}
    public void setSize(float size){this.size = size;}
    public int getPassivePerception(){return passivePerception;}
    public void setPassivePerception(int passivePerception){this.passivePerception = passivePerception;}

    /*----------------------------------Stats and their Modifiers----------------------------------------*/
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    public int getStrengthModifier()     { return (strength - 10) / 2; }
    public int getDexterityModifier()    { return (dexterity - 10) / 2; }
    public int getConstitutionModifier() { return (constitution - 10) / 2; }
    public int getIntelligenceModifier() { return (intelligence - 10) / 2; }
    public int getWisdomModifier()       { return (wisdom - 10) / 2; }
    public int getCharismaModifier()     { return (charisma - 10) / 2; }

    public int getStrength(){return strength;}
    public void setStrength(int strength){this.strength = strength;}
    public int getDexterity(){return dexterity;}
    public void setDexterity(int dexterity){this.dexterity = dexterity;}
    public int getConstitution(){return constitution;}
    public void setConstitution(int constitution){this.constitution = constitution;}
    public int getIntelligence(){return intelligence;}
    public void setIntelligence(int intelligence){this.intelligence = intelligence;}
    public int getWisdom(){return wisdom;}
    public void setWisdom(int wisdom){this.wisdom = wisdom;}
    public int getCharisma(){return charisma;}
    public void setCharisma(int charisma){this.charisma = charisma;}

    /*-------------------------------------Proficiencies-------------------------------------------------*/
    static class Proficiency {
        String name;
        boolean proficient;
        boolean expertise;

        public Proficiency(String name) {
            this.name = name;
            this.proficient = false;
            this.expertise = false;
        }
        public void setProficient(boolean proficient)   { this.proficient = proficient; }
        public void disableProficiency()                { this.proficient = false; }
        public void setExpertise(boolean expertise)     { this.expertise = expertise; }
        public void disableExpertise()                  { this.expertise = false; }
    }

    private Proficiency[] strengthProficiencies     = { new Proficiency("STR Saving Throw"), new Proficiency("Athletics") };
    private Proficiency[] dexterityProficiencies    = { new Proficiency("DEX Saving Throw"), new Proficiency("Acrobatics"), new Proficiency("Stealth"), new Proficiency("Sleight of Hand") };
    private Proficiency[] constitutionProficiencies = { new Proficiency("CON Saving Throw") };
    private Proficiency[] intelligenceProficiencies = { new Proficiency("INT Saving Throw"), new Proficiency("Arcana"), new Proficiency("History"), new Proficiency("Investigation"), new Proficiency("Nature"), new Proficiency("Religion") };
    private Proficiency[] wisdomProficiencies       = { new Proficiency("WIS Saving Throw"), new Proficiency("Animal Handling"), new Proficiency("Insight"), new Proficiency("Medicine"), new Proficiency("Perception"), new Proficiency("Survival") };
    private Proficiency[] charismaProficiencies     = { new Proficiency("CHA Saving Throw"), new Proficiency("Deception"), new Proficiency("Intimidation"), new Proficiency("Performance"), new Proficiency("Persuasion") };

    private List<Proficiency> weaponProficiencies = new ArrayList<>();
    private List<Proficiency> armorProficiencies = new ArrayList<>();
    private List<Proficiency> toolProficiencies = new ArrayList<>();
    private List<String> languages = new ArrayList<>();

    private boolean heroicInspiration;
    // Heroic Inspiration
    public boolean isHeroicInspiration() { return heroicInspiration; }
    public void setHeroicInspiration(boolean heroicInspiration) { this.heroicInspiration = heroicInspiration; }
    public List<Proficiency> getWeaponProficiencies() { return weaponProficiencies; }
    public void addWeaponProficiency(String name) { weaponProficiencies.add(new Proficiency(name)); }
    public void removeWeaponProficiency(String name) { weaponProficiencies.removeIf(p -> p.name.equals(name)); }
    public List<Proficiency> getArmorProficiencies() { return armorProficiencies; }
    public void addArmorProficiency(String name) { armorProficiencies.add(new Proficiency(name)); }
    public void removeArmorProficiency(String name) { armorProficiencies.removeIf(p -> p.name.equals(name)); }
    public List<Proficiency> getToolProficiencies() { return toolProficiencies; }
    public void addToolProficiency(String name) { toolProficiencies.add(new Proficiency(name)); }
    public void removeToolProficiency(String name) { toolProficiencies.removeIf(p -> p.name.equals(name)); }
    public List<String> getLanguages() { return languages; }
    public void addLanguage(String language) { languages.add(language); }
    public void removeLanguage(String language) { languages.remove(language); }
    public Proficiency[] getStrengthProficiencies()     { return strengthProficiencies; }
    public Proficiency[] getDexterityProficiencies()    { return dexterityProficiencies; }
    public Proficiency[] getConstitutionProficiencies() { return constitutionProficiencies; }
    public Proficiency[] getIntelligenceProficiencies() { return intelligenceProficiencies; }
    public Proficiency[] getWisdomProficiencies()       { return wisdomProficiencies; }
    public Proficiency[] getCharismaProficiencies()     { return charismaProficiencies; }

    /*----------------------------------------Features--------------------------------------------------*/

    static class Feats{ //remember the Player that they ALWAYS have to do the adjusting of proficiencies or stats coming from this manually
        private String name;
        private String description;

        public Feats(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
    }

    private List<Feats> feats = new ArrayList<>();
    private List<Feats> speciesTraits = new ArrayList<>();
    private List<Feats> classFeatures = new ArrayList<>();

    public List<Feats> getFeats() { return feats; }
    public void addFeat(String name, String description) { feats.add(new Feats(name, description)); }
    public void removeFeat(String name) { feats.removeIf(f -> f.getName().equals(name)); }
    public List<Feats> getSpeciesTraits() { return speciesTraits; }
    public void addSpeciesTrait(String name, String description) { speciesTraits.add(new Feats(name, description)); }
    public void removeSpeciesTrait(String name) { speciesTraits.removeIf(f -> f.getName().equals(name)); }
    public List<Feats> getClassFeatures() { return classFeatures; }
    public void addClassFeature(String name, String description) { classFeatures.add(new Feats(name, description)); }
    public void removeClassFeature(String name) { classFeatures.removeIf(f -> f.getName().equals(name)); }


    /*----------------------------------------SpellCasting-------------------------------------------------*/

    public static class SpellSlot{
        int level;
        int total;
        int expended;

        public SpellSlot(int level, int total) {
            this.level = level;
            this.total = 0;
            this.expended = 0;
        }
        public void addSpellSlot() {
            switch (level) {
                case 0: break;
                case 1: if(total < 4 )total++; break;
                case 2, 3, 4, 5: if(total < 3 )total++; break;
                case 6, 7: if(total < 2 )total++; break;
                case 8, 9: if(total < 1 )total++; break;
            }
        }
        public void restoreExpendedSlots() {
            expended = 0;
        }
        public void removeSpellSlot() {
            if(total == 0) return;
            total--;
        }
    }

    private String spellcastingAbility;
    private int spellcastingModifier = 0;
    private int spellSaveDC = 5;
    private int spellAttackBonus = 0;

    private SpellSlot[] spellSlots = {
            new SpellSlot(1, 0),
            new SpellSlot(2, 0),
            new SpellSlot(3, 0),
            new SpellSlot(4, 0),
            new SpellSlot(5, 0),
            new SpellSlot(6, 0),
            new SpellSlot(7, 0),
            new SpellSlot(8, 0),
            new SpellSlot(9, 0)
    };

    private List<Spell> spellList = new ArrayList<>();

    public String getSpellcastingAbility(){return spellcastingAbility;}
    public void setSpellcastingAbility(String spellcastingAbility){this.spellcastingAbility = spellcastingAbility;}
    public int getSpellcastingModifier(){return spellcastingModifier;}
    public void setSpellcastingModifier(int spellcastingModifier){this.spellcastingModifier = spellcastingModifier;}
    public int getSpellSaveDC(){return this.spellSaveDC;}
    public void setSpellSaveDC(int newDC){this.spellSaveDC = newDC;}
    public int getSpellAttackBonus() { return spellAttackBonus; }
    public void setSpellAttackBonus(int spellAttackBonus) { this.spellAttackBonus = spellAttackBonus; }
    public SpellSlot[] getSpellSlots() { return spellSlots; }
    public SpellSlot getSpellSlot(int level) { return spellSlots[level - 1]; }
    public List<Spell> getSpellList() { return spellList; }
    public void addSpell(Spell spell) { spellList.add(spell); }
    public void removeSpell(String name) { spellList.removeIf(s -> s.getSpellName().equals(name)); }


    /*----------------------------------------Equipment-------------------------------------------------*/

    private List<InventoryItem> inventory = new ArrayList<>();
    private int copperCoins = 0;
    private int silverCoins = 0;
    private int electrumCoins = 0;
    private int goldCoins = 0;
    private int platinumCoins = 0;
    private List<String> attunedItems = new ArrayList<>(); // max 3 Entrys
    public List<String> getAttunedItems() { return attunedItems; }

    public void attune(String itemName) {
        if (attunedItems.size() < 3)
            attunedItems.add(itemName);
    }
    public void unattune(String itemName) {attunedItems.remove(itemName);}
    public List<InventoryItem> getInventory() { return inventory; }
    public void addItem(InventoryItem item) { inventory.add(item); }
    public void removeItem(String name) { inventory.removeIf(i -> i.getName().equals(name)); }
    public int getCopperCoins() { return copperCoins; }
    public void setCopperCoins(int copperCoins) { this.copperCoins = copperCoins; }
    public int getSilverCoins() { return silverCoins; }
    public void setSilverCoins(int silverCoins) { this.silverCoins = silverCoins; }
    public int getElectrumCoins() { return electrumCoins; }
    public void setElectrumCoins(int electrumCoins) { this.electrumCoins = electrumCoins; }
    public int getGoldCoins() { return goldCoins; }
    public void setGoldCoins(int goldCoins) { this.goldCoins = goldCoins; }
    public int getPlatinumCoins() { return platinumCoins; }
    public void setPlatinumCoins(int platinumCoins) { this.platinumCoins = platinumCoins; }

    /*----------------------------------------CharacteristicsAndApperance-------------------------------------------------*/
    private String background;
    private String alignment;
    private String personalityTraits;
    private String ideals;
    private String bonds;
    private String flaws;
    private BufferedImage portrait = null;

    public String getBackground() { return background; }
    public void setBackground(String background) { this.background = background; }
    public String getAlignment() { return alignment; }
    public void setAlignment(String alignment) { this.alignment = alignment; }
    public String getPersonalityTraits() { return personalityTraits; }
    public void setPersonalityTraits(String personalityTraits) { this.personalityTraits = personalityTraits; }
    public String getIdeals() { return ideals; }
    public void setIdeals(String ideals) { this.ideals = ideals; }
    public String getBonds() { return bonds; }
    public void setBonds(String bonds) { this.bonds = bonds; }
    public String getFlaws() { return flaws; }
    public void setFlaws(String flaws) { this.flaws = flaws; }
    public BufferedImage getPortrait() { return portrait; }
    public void setPortrait(BufferedImage portrait) { this.portrait = portrait; }

    /*----------------------------------------Constructor-------------------------------------------------*/

    public Character(String name, String race,String className, int level, String background, Dice hitDie) {
        this.name = name;
        this.race = race;
        if(level > 0 && level <= 20)
            this.level = level;
        this.classes.add(new Class(className));
        for(int i = this.level; i > 1; i--)
            this.classes.get(0).levelUp();
        this.background = background;
        this.hitDie = hitDie;
    }
}
