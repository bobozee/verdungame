package verdungame.units;

import verdungame.Main;

import java.util.ArrayList;
import java.util.Random;

public class BattleManager {

    private boolean success;
    private Main main;
    private Unit attacker;
    private Unit defender;

    public BattleManager(Unit attacker, Unit defender, int distance, Main main) {

        this.defender = defender;
        this.attacker = attacker;
        this.main = main;

        Stats attackerStats = attacker.getSelfModifiers().applyTo(attacker.getStats());
        Stats defenderStats = defender.getSelfModifiers().applyTo(defender.getStats());

        main.log("");

        int attackerHealth = attackerStats.getPush();
        int offensiveAtt = floatAround(attackerStats.getAttack(), 0.7f);
        main.log("   Attacker's attack: " + offensiveAtt);
        main.log("   Attacker's push: " + attackerHealth);
        logModifierReport(attacker);
        main.log("");


        int defensiveAtt = floatAround(defenderStats.getAttack(), 0.7f);
        int defenderHealth = defenderStats.getDefense();
        main.log("   Defender's attack: " + defensiveAtt);
        main.log("   Defender's defense: " + defenderHealth);
        logModifierReport(defender);
        main.log("");

        if (distance <= defenderStats.getAttackRange()) {
            attackerHealth -= defensiveAtt;
        }
        defenderHealth -= offensiveAtt;

        main.log("   Pow!");
        main.log("   Attacker's health: " + attackerHealth);
        main.log("   Defender's health: " + defenderHealth);

        main.log("");


        if (attackerHealth >= defenderHealth) {
            success = true;
        } else {
            success = false;
        }

    }

    public boolean getResult() {
        return success;
    }

    private int floatAround(int value, float margin) {

        Random r = new Random();
        return (int)(value * margin) + r.nextInt((int)((1 / margin) * value));

    }

    private void logModifierReport(Unit target) {

        String text = "      ";

        final float[] attackPercent = {0};
        final float[] defensePercent = {0};
        final float[] pushPercent = {0};
        final ArrayList<UnitModifying> origins = new ArrayList<>();

        target.getSelfModifiers().getMap().forEach( (key, value) -> {

            attackPercent[0] += value.getStats().getAttack();
            defensePercent[0] += value.getStats().getDefense();
            pushPercent[0] += value.getStats().getDefense();

            origins.add(key);
        });

        if (!origins.isEmpty()) {

            text += "with Modifiers:";

            if (attackPercent[0] > 0) {
                text += "\n         Attack: " + attackPercent[0];
            }

            if (defensePercent[0] > 0) {
                text += "\n         Defense: " + defensePercent[0];
            }

            if (pushPercent[0] > 0) {
                text += "\n         Push: " + pushPercent[0];
            }

            text += "\n      from: ";

            for (UnitModifying origin : origins) {

                text += "\n         " + origin.getClass().getName().replace("verdungame.units.", "");

            }
        }

    }

}
