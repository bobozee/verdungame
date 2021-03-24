package verdungame.tiles;

import javafx.scene.layout.Pane;
import verdungame.Allegiances;

public class Point extends TilePoint {

    public Point(Pane view, int x, int y, Allegiances allegiance) {
        super(view, x, y, allegiance);
        setSprites();
    }

    public Point(Pane view, int x, int y, int width, int height, Allegiances allegiance) {
        super(view, x, y, width, height, allegiance);
        setSprites();
    }

    private void setSprites() {
        super.setSprite(getClass().getClassLoader().getResourceAsStream("tiles/Square.png"));
        super.setRedControl(getClass().getClassLoader().getResourceAsStream("tiles/EnemyPoint.png"));
        super.setGreenControl(getClass().getClassLoader().getResourceAsStream("tiles/FriendlyPoint.png"));
        super.switchAllegiance();
        super.switchAllegiance();
    }
}