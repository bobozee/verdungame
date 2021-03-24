package verdungame.tiles;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.InputStream;

public abstract class Tile {

    protected ImageView iv;

    public Tile(Pane view, int x, int y) {

        iv = new ImageView();
        iv.setX(x);
        iv.setY(y);
        iv.setFitHeight(30);
        iv.setFitWidth(30);
        view.getChildren().add(iv);

    }

    public Tile(Pane view, int x, int y, int width, int height) {

        iv = new ImageView();
        iv.setX(x);
        iv.setY(y);
        iv.setFitHeight(height);
        iv.setFitWidth(width);
        view.getChildren().add(iv);

    }

    protected void setSprite(InputStream url) {
        iv.setImage(new Image(url));
    }

    public double getWidth() {
        return iv.getFitWidth();
    }

    public double getHeight() {
        return iv.getFitHeight();
    }

    public double getX() {
        return iv.getX();
    }

    public double getY() {
        return iv.getY();
    }

}
