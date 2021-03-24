package verdungame.units;

import java.util.ArrayList;

public abstract class UnitModifying extends Unit {

    protected ModManager modManager;

    public UnitModifying() {

        super();
        modManager = new ModManager();

    }

    public ModManager getModManager() {
        return modManager;
    }


}
