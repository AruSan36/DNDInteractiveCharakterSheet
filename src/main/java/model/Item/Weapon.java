package model.Item;

import model.Dice;

import java.util.List;

public class Weapon extends InventoryItem{
    private Dice damageDice;  // 1d6 for example
    private int attackBonus;
    private String damageType;  //bludgeoning, piercing, slashing
    private String weaponType;  //Simple or Martial
    private String weaponMastery; //2024 rules exclusie. A feature that gives the weapon more depth
    private List<String> properties; //versatile, finesse, two-handed, etc.


    public Weapon(String name, int weight, int value, String description, int quantity, Dice damageDice, String damageType, String weaponType, String weaponMastery, String... properties){
        super(name, weight, value, description, quantity);
        this.damageDice = damageDice;
        this.damageType = damageType;
        this.weaponType = weaponType;
        this.weaponMastery = weaponMastery;
        this.properties = List.of(properties);
    }
    public Dice getDamageDice() { return damageDice; }
    public String getDamageType() { return damageType; }
    public String getWeaponMaster(){return weaponMastery;}
    public String getWeaponType() {return weaponType;}
    public List<String> getProperties() { return properties; }
}
