package verdungame.units;

import java.util.Collection;
import java.util.HashMap;

public class ModifierStats extends Stats {

    private HashMap<UnitModifying, Modifier> modifiers;

    public ModifierStats() {

        super(100, 100, 100, 100, 100, 100, 100);
        modifiers = new HashMap<>();

    }

    public Stats applyTo(Stats rawStats) {

        return new Stats(   (int) ( ((float)attack / 100) * rawStats.getAttack()),
                            (int) ( ((float)defense / 100) * rawStats.getDefense()),
                            (int) ( ((float)push / 100) * rawStats.getPush()),
                            (int) ( ((float)attackRange / 100) * rawStats.getAttackRange()),
                            (int) ( ((float)effectRange / 100) * rawStats.getEffectRange()),
                            (int) ( ((float)moveCost / 100) * rawStats.getMoveCost()),
                            (int) ( ((float)spawnCost / 100) * rawStats.getSpawnCost()));


    }

    public void addModifierOrigin(UnitModifying origin, Modifier modifier) {

        Stats addedStats = modifier.getStats();

        attack += addedStats.getAttack();
        defense += addedStats.getDefense();
        push += addedStats.getPush();
        attackRange += addedStats.getAttackRange();
        moveCost += addedStats.getMoveCost();
        spawnCost += addedStats.getSpawnCost();
        effectRange += addedStats.getEffectRange();

        modifiers.put(origin, modifier);

    }

    public void removeModifierOrigin(UnitModifying origin) {

        Stats addedStats = modifiers.get(origin).getStats();

        attack -= addedStats.getAttack();
        defense -= addedStats.getDefense();
        push -= addedStats.getPush();
        attackRange -= addedStats.getAttackRange();
        moveCost -= addedStats.getMoveCost();
        spawnCost -= addedStats.getSpawnCost();
        effectRange -= addedStats.getEffectRange();

        modifiers.remove(origin);

    }

    public boolean hasModifier(Modifier modifier) {
        return modifiers.containsValue(modifier);
    }

    public HashMap<UnitModifying, Modifier> getMap() {
        return modifiers;
    }

}
