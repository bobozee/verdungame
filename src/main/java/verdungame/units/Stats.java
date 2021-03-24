package verdungame.units;

public class Stats {

    protected int attack;
    protected int defense;
    protected int push;
    protected int attackRange;
    protected int moveCost;
    protected int spawnCost;
    protected int effectRange;

    public Stats(int attack, int defense, int push, int attackRange, int effectRange, int moveCost, int spawnCost) {
        this.attack = attack;
        this.defense = defense;
        this.push = push;
        this.attackRange = attackRange;
        this.moveCost = moveCost;
        this.spawnCost = spawnCost;
        this.effectRange = effectRange;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getPush() {
        return push;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getMoveCost() {
        return moveCost;
    }

    public int getSpawnCost() {
        return spawnCost;
    }

    public int getEffectRange() {
        return effectRange;
    }

}
