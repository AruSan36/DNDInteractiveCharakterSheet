package model;

public enum Dice {
    D4(4), D6(6), D8(8),D10(10), D12(12), D20(20);

    private final int sides;
    Dice(int sides) { this.sides = sides; }
    public int getSides() { return sides; }
}
