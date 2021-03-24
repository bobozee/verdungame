package verdungame.tiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import verdungame.Allegiances;
import verdungame.units.Unit;

import java.io.InputStream;


public abstract class TileDynamic extends Tile {

    private Unit occupier;
    private boolean occupation;
    private Allegiances allegiance;
    private boolean conquerOnOccupation;
    private Image redControl;
    private Image greenControl;
    private Image neutralControl;

    public TileDynamic(Pane view, int x, int y, Allegiances allegiance, boolean conquerOnOccupation) {

        super(view, x, y);
        this.allegiance = allegiance;
        this.conquerOnOccupation = conquerOnOccupation;

    }

    public TileDynamic(Pane view, int x, int y, int width, int height, Allegiances allegiance, boolean conquerOnOccupation) {

        super(view, x, y, width, height);
        this.allegiance = allegiance;
        this.conquerOnOccupation = conquerOnOccupation;

    }

    public TileDynamic(Pane view, int x, int y, boolean conquerOnOccupation) {

        super(view, x, y);
        this.allegiance = Allegiances.NEUTRAL;
        this.conquerOnOccupation = conquerOnOccupation;

    }

    public TileDynamic(Pane view, int x, int y, int width, int height, boolean conquerOnOccupation) {

        super(view, x, y, width, height);
        this.allegiance = Allegiances.NEUTRAL;
        this.conquerOnOccupation = conquerOnOccupation;

    }

    public void occupy(Unit unit) {

        occupier = unit;
        occupation = true;
        unit.setPosition(this);
        super.iv.setImage(unit.getOccupationIconForTile(this));

        if (conquerOnOccupation) {

            allegiance = unit.getAllegiance();

        }

    }

    public void free() {

        switch (allegiance) {

            case RED:
                super.iv.setImage(redControl);
                break;

            case GREEN:
                super.iv.setImage(greenControl);
                break;

            case NEUTRAL:
                super.iv.setImage(neutralControl);
                break;

        }

        occupier = null;
        occupation = false;

    }

    public boolean isOccupied() {
        return occupation;
    }

    public Unit getOccupier() {
        return occupier;
    }

    public Allegiances getAllegiance() {
        return allegiance;
    }

    protected void setRedControl(InputStream url) {
        redControl = new Image(url);
    }

    protected void setGreenControl(InputStream url) {
        greenControl = new Image(url);
    }

    protected void setNeutralControl(InputStream url)  {
        neutralControl = new Image(url);
    }

}
