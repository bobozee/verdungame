package verdungame.tiles;

import javafx.scene.layout.Pane;
import verdungame.Allegiances;

public class Bridge extends TileDynamic {

    public Bridge(Pane view, int x, int y) {
        super(view, x, y, Allegiances.NEUTRAL, false);
        setSprites();
    }

    public Bridge(Pane view, int x, int y, int width, int height) {
        super(view, x, y, width, height, Allegiances.NEUTRAL, false);
        setSprites();
    }

    private void setSprites() {
        super.setNeutralControl(getClass().getClassLoader().getResourceAsStream("tiles/Bridge.png"));
        super.free();
    }
}