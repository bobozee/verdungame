package verdungame.units;

import javafx.scene.image.Image;
import verdungame.Allegiances;
import verdungame.tiles.TileDynamic;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Unit {

    protected HashMap<Class<? extends TileDynamic>, Image> occupationIcons;
    protected TileDynamic position;
    protected ArrayList<Class<? extends TileDynamic>> possibleTerrain;
    protected Allegiances allegiance;
    protected Stats stats;
    private ModifierStats modifiers;


    public Unit() {
        occupationIcons = new HashMap();
        possibleTerrain = new ArrayList<Class<? extends TileDynamic>>();
        modifiers = new ModifierStats();
    }


    public Allegiances getAllegiance() {
        return allegiance;
    }

    public Image getOccupationIconForTile(TileDynamic tile) {
        return occupationIcons.get(tile.getClass());
    }

    public TileDynamic getPosition() {
        return position;
    }

    public void setPosition(TileDynamic position) {
        this.position = position;
    }

    public Stats getStats() {
        return stats;
    }

    public ArrayList<Class<? extends TileDynamic>> getPossibleTerrain() {
        return possibleTerrain;
    }

    public ModifierStats getSelfModifiers() {
        return modifiers;
    }

}
