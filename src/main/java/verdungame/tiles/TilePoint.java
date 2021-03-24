package verdungame.tiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import verdungame.Allegiances;

import java.io.InputStream;

public abstract class TilePoint extends Tile {

    private Allegiances allegiance;
    private Image redControl;
    private Image greenControl;

    public TilePoint(Pane view, int x, int y, Allegiances allegiance) {
        super(view, x, y);
        this.allegiance = allegiance;
    }

    public TilePoint(Pane view, int x, int y, int width, int height, Allegiances allegiance) {
        super(view, x, y, width, height);
        this.allegiance = allegiance;
    }

    public void switchAllegiance() {

        if (allegiance == Allegiances.GREEN) {

            allegiance = Allegiances.RED;
            super.iv.setImage(redControl);

        } else {

            allegiance = Allegiances.GREEN;
            super.iv.setImage(greenControl);

        }

    }

    public Allegiances getAllegiance() {
        return allegiance;
    }

    protected void setGreenControl(InputStream url) {
        greenControl = new Image(url);
    }

    protected void setRedControl(InputStream url) {
        redControl = new Image(url);
    }
}
