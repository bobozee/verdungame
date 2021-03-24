package verdungame.tiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Mountain extends TileStatic {

    public Mountain(Pane view, int x, int y) {
        super(view, x, y);
        super.setSprite(getClass().getClassLoader().getResourceAsStream("tiles/Mountain.png"));
    }

    public Mountain(Pane view, int x, int y, int width, int height) {
        super(view, x, y, width, height);
        super.setSprite(getClass().getClassLoader().getResourceAsStream("tiles/Mountain.png"));
    }
}
