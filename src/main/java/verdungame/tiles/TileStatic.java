package verdungame.tiles;

import javafx.scene.layout.Pane;

public abstract class TileStatic extends Tile {

    public TileStatic(Pane view, int x, int y) {
        super(view, x, y);
    }

    public TileStatic(Pane view, int x, int y, int width, int height) {
        super(view, x, y, width, height);
    }
}
