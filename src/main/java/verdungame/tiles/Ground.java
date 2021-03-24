package verdungame.tiles;

import javafx.scene.layout.Pane;
import verdungame.Allegiances;

public class Ground extends TileDynamic {

    public Ground(Pane view, int x, int y, Allegiances allegiance) {
        super(view, x, y, allegiance, true);
        setSprites();
    }

    public Ground(Pane view, int x, int y, int width, int height, Allegiances allegiance) {
        super(view, x, y, width, height, allegiance, true);
        setSprites();
    }

    private void setSprites() {
        super.setNeutralControl(getClass().getClassLoader().getResourceAsStream("tiles/Square.png"));
        super.setRedControl(getClass().getClassLoader().getResourceAsStream("tiles/Enemy.png"));
        super.setGreenControl(getClass().getClassLoader().getResourceAsStream("tiles/Friendly.png"));
        super.free();
    }
}