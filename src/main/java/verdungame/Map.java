package verdungame;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import verdungame.tiles.*;
import verdungame.units.Infantry;


public class Map {

    private final int amntHori = 12;
    private final int amntVert = 24;
    private int yOffset;

    private Tile tiles[][];

    private Scene scene;
    private Pane view;
    private ImageView cursor;

    private int vertFract;
    private int horiFract;

    private int selectionIdX;
    private int selectionIdY;

    public Map(Pane view, Scene scene) {

        tiles = new Tile[amntVert][amntHori];
        this.scene = scene;
        this.view = view;
        this.yOffset = 0;

        defineMap();

    }

    public Map(Pane view, Scene scene, int yOffset) {

        tiles = new Tile[amntVert][amntHori];
        this.scene = scene;
        this.view = view;
        this.yOffset = yOffset;

        defineMap();

    }

    private void defineMap() {

        vertFract = ((int)scene.getHeight() - yOffset) / amntVert;
        horiFract = (int)scene.getWidth() / amntHori;

        for (int i = 0; i < amntVert / 2; i++) {

            for (int j = 0; j < amntHori; j++) {

                tiles[i][j] = new Ground(view, horiFract * j, vertFract * i + yOffset, horiFract, vertFract, Allegiances.RED);

            }

        }

        for (int i = amntVert / 2; i < amntVert; i++) {

            for (int j = 0; j < amntHori; j++) {

                tiles[i][j] = new Ground(view, horiFract * j, vertFract * i + yOffset, horiFract, vertFract, Allegiances.GREEN);

            }

        }
//--
        tiles[5][6] = new Point(view, horiFract * 6, vertFract * 5 + yOffset, horiFract, vertFract, Allegiances.RED);
        tiles[7][2] = new Point(view, horiFract * 2, vertFract * 7 + yOffset, horiFract, vertFract, Allegiances.RED);

        tiles[16][6] = new Point(view, horiFract * 6, vertFract * 16 + yOffset, horiFract, vertFract, Allegiances.GREEN);
        tiles[22][8] = new Point(view, horiFract * 8, vertFract * 22 + yOffset, horiFract, vertFract, Allegiances.GREEN);


        for (int j = 0; j < amntHori / 2; j++) {

            tiles[11][j] = new Water(view, horiFract * j, vertFract * 11 + yOffset, horiFract, vertFract);
            tiles[12][j] = new Water(view, horiFract * j, vertFract * 12 + yOffset, horiFract, vertFract);
        }

        for (int j = amntHori / 2; j < amntHori; j++) {

            tiles[12][j] = new Water(view, horiFract * j, vertFract * 12 + yOffset, horiFract, vertFract);
            tiles[13][j] = new Water(view, horiFract * j, vertFract * 13 + yOffset, horiFract, vertFract);

        }

        tiles[11][3] = new Bridge(view, horiFract * 3, vertFract * 11 + yOffset, horiFract, vertFract);
        tiles[12][3] = new Bridge(view, horiFract * 3, vertFract * 12 + yOffset, horiFract, vertFract);

//--
    }

    public Tile moveCursor(MouseEvent mouseEvent) {

        if (cursor == null) {

            cursor = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("tiles/Selection.png")));
            cursor.setFitHeight(vertFract);
            cursor.setFitWidth(horiFract);
            view.getChildren().add(cursor);

        }

        int mouseX = (int)mouseEvent.getX();
        int mouseY = (int)mouseEvent.getY() - yOffset;

        if (mouseY >= yOffset) {

            cursor.setX(horiFract * ((int)mouseX / horiFract));
            cursor.setY(vertFract * ((int)mouseY / vertFract) + yOffset);

            for (int i = 0; i < amntVert; i++) {

                for (int j = 0; j < amntHori; j++) {

                    if (mouseY >= vertFract * i && mouseY < vertFract * (i + 1)) {

                        if (mouseX >= horiFract * j && mouseX < horiFract * (j + 1)) {

                            selectionIdX = i;
                            selectionIdY = j;
                            return tiles[i][j];

                        }

                    }

                }

            }

        }

        return null;

    }

    public Tile moveCursor(Tile tile) {

        if (cursor == null) {

            cursor = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("tiles/Selection.png")));
            cursor.setFitHeight(vertFract);
            cursor.setFitWidth(horiFract);
            view.getChildren().add(cursor);

        }

        int tileX = (int)tile.getX();
        int tileY = (int)tile.getY();

        for (int i = 0; i < amntVert; i++) {

            for (int j = 0; j < amntHori; j++) {

                if (tileY >= vertFract * i && tileY < vertFract * (i + 1)) {

                    if (tileX >= horiFract * j && tileX < horiFract * (j + 1)) {

                        selectionIdX = i;
                        selectionIdY = j;
                        return tiles[i][j];

                    }

                }

            }

        }

        return null;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getSelectionIdX() {
        return selectionIdX;
    }

    public int getSelectionIdY() {
        return selectionIdY;
    }

    public int getAmntHori() {
        return amntHori;
    }

    public int getAmntVert() {
        return amntVert;
    }

    public Tile getTileWithIndex(int x, int y) {
        return tiles[x][y];
    }

    public int getTileX(Tile tile) {

        for (int i = 0; i < amntVert; i++) {

            for (int j = 0; j < amntHori; j++) {

                if (tiles[i][j] == tile) {

                    return i;

                }

            }

        }

        return -1;

    }

    public int getTileY(Tile tile) {

        for (int i = 0; i < amntVert; i++) {

            for (int j = 0; j < amntHori; j++) {

                if (tiles[i][j] == tile) {

                    return j;

                }

            }

        }

        return -1;

    }

    public boolean isInReach(Tile t1, Tile t2, int maxDistance) {

        for (int i = -maxDistance; i <= maxDistance; i++) {

            for (int j = -maxDistance; j <= maxDistance; j++) {

                Tile scanTile = getTileWithIndex(getTileX(t1) + i, getTileY(t1) + j);

                if (scanTile instanceof TileDynamic) {

                    TileDynamic dynTile = (TileDynamic) scanTile;

                    if (scanTile == t2) {

                        return true;

                    }

                }

            }

        }

        return false;

    }

}
