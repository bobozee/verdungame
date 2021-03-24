package verdungame.units;

import javafx.scene.layout.Pane;
import verdungame.Allegiances;
import verdungame.tiles.Ground;
import verdungame.tiles.TileDynamic;

public class UnitInspector {

    private Unit result;

    public <T extends Unit> UnitInspector(Class<? extends Unit> unitType) throws Exception {

        Pane tempPane = new Pane();
        Ground tempTile = new Ground(tempPane, -1916, -1916, Allegiances.NEUTRAL);

        result = unitType.getDeclaredConstructor(TileDynamic.class, Allegiances.class).newInstance(tempTile, Allegiances.NEUTRAL);

    }

    public <T extends Unit> UnitInspector(Class<? extends Unit> unitType, TileDynamic target) throws Exception {

        result = unitType.getDeclaredConstructor(TileDynamic.class, Allegiances.class).newInstance(target, Allegiances.NEUTRAL);

    }

    public Unit getResult() {
        return result;
    }

}
