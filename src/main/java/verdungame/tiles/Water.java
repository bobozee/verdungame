package verdungame.tiles;

import javafx.scene.layout.Pane;
import verdungame.Allegiances;

public class Water extends TileDynamic {

    public Water(Pane view, int x, int y) {
        super(view, x, y, Allegiances.NEUTRAL, false);
        setSprites();
    }

    public Water(Pane view, int x, int y, int width, int height) {
        super(view, x, y, width, height, Allegiances.NEUTRAL, false);
        setSprites();
    }

    private void setSprites() {
        super.setNeutralControl(getClass().getClassLoader().getResourceAsStream("tiles/Water.png"));
        super.free();
    }
}