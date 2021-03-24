package verdungame.units;

import java.util.HashMap;
import java.util.Map;

public class ModManager {

    private final HashMap<Class<? extends Unit>, Modifier> modMap;

    public ModManager() {
        modMap = new HashMap<>();
    }

    public Map<Class<? extends Unit>, Modifier> getModMap() {
        return modMap;
    }

}
