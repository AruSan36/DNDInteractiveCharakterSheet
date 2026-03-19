package model.Item;

public class Armor extends InventoryItem{
    private String armorType;
    private int armorClass; // the base Armor Class
    private boolean allowsDexBonus; // if the AC is affected by Dexterity modifier
    private int maxDexBonus; //sets the maximum Dexterity modifier that can be applied to the AC
    private boolean steatlthDisadvantage;

    public Armor(String name, int weight, int value, String description, int quantity, String armorType, int armorClass, boolean allowsDexBonus, int maxDexBonus, boolean steatlthDisadvantage){
        super(name, weight, value, description, quantity);
        this.armorType = armorType;
        this.armorClass = armorClass;
        this.allowsDexBonus = allowsDexBonus;
        this.maxDexBonus = maxDexBonus;
        this.steatlthDisadvantage = steatlthDisadvantage;
    }

    public String getArmorType() { return armorType; }
    public int getArmorClass() { return armorClass; }
    public boolean getAllowsDexBonus() { return allowsDexBonus; }
    public int getMaxDexBonus() { return maxDexBonus; }
    public boolean getSteatlthDisadvantage() { return steatlthDisadvantage; }
}