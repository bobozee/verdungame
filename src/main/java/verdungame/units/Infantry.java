package verdungame.units;

import javafx.scene.image.Image;
import verdungame.Allegiances;
import verdungame.tiles.Ground;
import verdungame.tiles.Bridge;
import verdungame.tiles.TileDynamic;

public class Infantry extends Unit {

    public Infantry(TileDynamic position, Allegiances allegiance) {

        super();
        super.stats = new Stats(40, 50, 30, 1, 0, 1, 20);
        super.position = position;
        super.allegiance = allegiance;

        super.possibleTerrain.add(Ground.class);
        super.possibleTerrain.add(Bridge.class);

        switch (allegiance) {

            case RED:

                super.occupationIcons.put(Ground.class, new Image(getClass().getClassLoader().getResourceAsStream("occupations/BadInf.png")));
                super.occupationIcons.put(Bridge.class, new Image(getClass().getClassLoader().getResourceAsStream("occupations/BridgeInfBad.png")));

                break;

            case GREEN:

                super.occupationIcons.put(Ground.class, new Image(getClass().getClassLoader().getResourceAsStream("occupations/GoodInf.png")));
                super.occupationIcons.put(Bridge.class, new Image(getClass().getClassLoader().getResourceAsStream("occupations/BridgeInfGood.png")));

                break;

        }

    }
}
