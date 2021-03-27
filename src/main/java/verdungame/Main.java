package verdungame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import verdungame.tiles.*;
import verdungame.units.*;

import java.util.ArrayList;


public class Main extends Application {

    //fields to manage selections across different methods
    private Tile selectionPrimary;
    private int selectionIdXPrim;
    private int selectionIdYPrim;
    private Tile selectionSecondary;
    private int selectionIdXSec;
    private int selectionIdYSec;

    //lists of all units and points on the map
    private ArrayList<Unit> units;
    private ArrayList<UnitModifying> modUnits;
    private ArrayList<TilePoint> tilePoints;

    //basic game data
    private Allegiances currAllegiance;
    private int pointsRed;
    private int pointsGreen;
    private float possibleMoves;
    private final int maxMoves = 8;

    //graphical stuff
    private Button[] recBtns;
    private Map map;
    private Text pointsGreenText;
    private Text pointsRedText;
    private Text boardInfoTextLeft;
    private Text boardInfoTextRight;
    private Pane boardPane;
    private Scene boardScene;
    private EventHandler<MouseEvent> mouseHandler;
    private EventHandler<KeyEvent> keyHandler;
    private VBox logBox;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        units = new ArrayList<>();
        tilePoints = new ArrayList<>();
        modUnits = new ArrayList<>();

        currAllegiance = Allegiances.GREEN;
        pointsGreen = 100;
        pointsRed = 100;
        possibleMoves = maxMoves;

        initializeBoard(primaryStage);
        initializeRecruitment();
        initializeLog();

    }

    private void initializeBoard(Stage boardStage) {

        boardStage.setTitle("Destroy them all!");
        boardPane = new Pane();
        boardScene = new Scene(boardPane, 480, 960);

        boardInfoTextLeft = new Text(currAllegiance + "'s Turn! (" + possibleMoves  + " moves left)");
        boardPane.getChildren().add(boardInfoTextLeft);
        boardInfoTextLeft.setY(20);
        boardInfoTextLeft.setStyle("-fx-font: 15 arial;");

        boardInfoTextRight = new Text("21. February 1916, Verdun");
        boardPane.getChildren().add(boardInfoTextRight);
        boardInfoTextRight.setY(20);
        boardInfoTextRight.setX(boardScene.getWidth() / 2);
        boardInfoTextRight.setStyle("-fx-font: 15 arial;");

        map = new Map(boardPane, boardScene, 24);

        boardStage.setScene(boardScene);
        boardStage.show();


        for (int i = 0; i < map.getAmntVert(); i++) {

            for (int j = 0; j < map.getAmntHori(); j++) {

                Tile scanTile = map.getTileWithIndex(i, j);

                if (scanTile instanceof TilePoint) {

                    tilePoints.add((TilePoint) scanTile);

                }

                if (scanTile instanceof TileDynamic) {

                    TileDynamic dynTile = (TileDynamic) scanTile;

                    if (dynTile.isOccupied()) {

                        if (dynTile.getOccupier() instanceof UnitModifying) {

                            modUnits.add((UnitModifying) dynTile.getOccupier());

                        } else if (dynTile.getOccupier() != null) {

                            units.add(dynTile.getOccupier());

                        }

                    }

                }

            }

        }

        mouseHandler = mouseEvent -> {

            if (mouseEvent.isPrimaryButtonDown()) {

                selectionPrimary = map.moveCursor(mouseEvent);
                selectionIdXPrim = map.getSelectionIdX();
                selectionIdYPrim = map.getSelectionIdY();
                selectionSecondary = null;

            } else if (mouseEvent.isSecondaryButtonDown() && selectionPrimary != null) {

                selectionSecondary = map.moveCursor(mouseEvent);
                selectionIdXSec = map.getSelectionIdX();
                selectionIdYSec = map.getSelectionIdY();
                handleSelections();

            }

            update();

        };
        boardScene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseHandler);

        //handle keyboard events
        keyHandler = event -> {

            if (event.getCode() == KeyCode.SPACE) {

                for (TilePoint tp : tilePoints) {

                    if (tp.getAllegiance() == currAllegiance) {

                        if (currAllegiance == Allegiances.GREEN) {
                            pointsGreen += 50;
                        } else {
                            pointsRed += 50;
                        }

                    }

                }

                if (currAllegiance == Allegiances.GREEN) {

                    currAllegiance = Allegiances.RED;

                } else {

                    currAllegiance = Allegiances.GREEN;

                }

                log(currAllegiance + "'s Turn!");

                possibleMoves = maxMoves;

            }

            update();

        };
        boardScene.addEventFilter(KeyEvent.KEY_PRESSED, keyHandler);
    }

    private void initializeRecruitment() {

        VBox recBox = new VBox();

        pointsGreenText = new Text("Green's Points: " + pointsGreen);
        recBox.getChildren().add((pointsGreenText));

        pointsRedText = new Text("Red's Points: " + pointsRed);
        recBox.getChildren().add((pointsRedText));

        recBtns = new Button[8];
        recBtns[0] = new Button("Infanty");
        recBtns[1] = new Button("Medium Infantry");
        recBtns[2] = new Button("Heavy Infantry");
        recBtns[3] = new Button("Armored Infantry");
        recBtns[4] = new Button("Artillery");
        recBtns[5] = new Button("Tank");
        recBtns[6] = new Button("Recon");
        recBtns[7] = new Button("Marines");

        for (Button btn : recBtns) {

            btn.setMaxWidth(Double.MAX_VALUE);
            recBox.getChildren().add(btn);
            btn.setDisable(true);

        }

        Scene recScene = new Scene(recBox, 200, 350);
        Stage recStage = new Stage();
        recStage.setTitle("Recruit new Units!");
        recStage.setScene(recScene);
        recStage.show();

        handleRecruitments();

    }

    private void initializeLog() {

        logBox = new VBox();

        Scene logScene = new Scene(logBox, 250, 400);
        Stage logStage = new Stage();
        logStage.setTitle("Infolog");
        logStage.setScene(logScene);
        logStage.show();

    }

    private void handleSelections() {

        //only listen to a selection between to dynamic tiles
        if (selectionPrimary instanceof TileDynamic && selectionSecondary instanceof TileDynamic) {

            TileDynamic dynTilePrim = (TileDynamic) selectionPrimary;
            TileDynamic dynTileSec = (TileDynamic) selectionSecondary;

            Stats unitMStats = null;
            try {
                UnitInspector ui = new UnitInspector(Infantry.class, dynTilePrim);
                Unit uiresult = ui.getResult();
                unitMStats = uiresult.getSelfModifiers().applyTo(ui.getResult().getStats());
            } catch (Exception e) {
                e.printStackTrace();
            }
            

            //handle the logic for selecting a friendly unit
            if (dynTilePrim.isOccupied() && dynTilePrim.getOccupier().getAllegiance() == currAllegiance) {

                assert unitMStats != null;
                if (possibleMoves - unitMStats.getMoveCost() >= 0) {

                    Unit ally = dynTilePrim.getOccupier();

                    updateModifiers(ally);

                    boolean acceptedTerrain = false;

                    if (ally.getPossibleTerrain().contains(dynTileSec.getClass())) {

                        acceptedTerrain = true;

                    }

                    //handle the marching of said unit
                    if (!dynTileSec.isOccupied()) {

                        if (acceptedTerrain) {

                            if (Math.abs(selectionIdXPrim - selectionIdXSec) <= 1
                                    && Math.abs(selectionIdYPrim - selectionIdYSec) <= 1) {

                                if (possibleMoves - ally.getSelfModifiers().applyTo(ally.getStats()).getMoveCost() >= 0) {

                                    ally.getPosition().free();
                                    dynTileSec.occupy(ally);

                                    possibleMoves -= ally.getSelfModifiers().applyTo(ally.getStats()).getMoveCost();

                                    log(currAllegiance + " moves a " + ally.getClass().getName().replace("verdungame.units.", "") + "!");

                                }

                            }

                        } else {

                            errLog(IllegalMoves.BAD_TARGET);

                        }

                    } else {

                        Unit stranger = dynTileSec.getOccupier();

                        updateModifiers(stranger);

                        //handle the attacking of a unit with said unit
                        if (stranger.getAllegiance() != ally.getAllegiance()) {

                            int distanceX = Math.abs(selectionIdXPrim - selectionIdXSec);
                            int distanceY = Math.abs(selectionIdYPrim - selectionIdYSec);
                            int distance = Math.max(distanceX, distanceY);

                            if (distance <= ally.getSelfModifiers().applyTo(ally.getStats()).getAttackRange()) {

                                log(currAllegiance + " commences an attack!");

                                if (new BattleManager(ally, stranger, distance, this).getResult()) {

                                    if (acceptedTerrain) {

                                        ally.getPosition().free();
                                        stranger.getPosition().occupy(ally);

                                    } else {

                                        stranger.getPosition().free();

                                    }

                                    units.remove(stranger);

                                    log(currAllegiance + " wins the attack!");

                                } else {

                                    ally.getPosition().free();
                                    units.remove(ally);

                                    log(currAllegiance + " loses the attack!");

                                }

                                possibleMoves -= ally.getSelfModifiers().applyTo(ally.getStats()).getMoveCost();

                            } else {

                                errLog(IllegalMoves.ENEMY_TOO_FAR_AWAY);

                            }

                        } else {

                            errLog(IllegalMoves.ALLY_ATTACK);

                        }

                    }

                } else {

                    errLog(IllegalMoves.NOT_ENOUGH_MOVES);

                }

            }

        }

        selectionPrimary = map.moveCursor(selectionSecondary);
        selectionIdXPrim = map.getSelectionIdX();
        selectionIdYPrim = map.getSelectionIdY();
        selectionSecondary = null;

    }

    private void handleRecruitments() {

        //these are all events, thus they don't need to be rerun
        recBtns[0].setOnAction(e -> {
            try {
                spawn(Infantry.class);
                update();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

    }

    public <T extends Unit> void spawn(Class<? extends Unit> unitType) throws Exception {

        log(currAllegiance + " spawns a " + unitType.getName().replace("verdungame.units.", "") + "!");

        TileDynamic dynTile = (TileDynamic) selectionPrimary;

        Unit newbie = unitType.getDeclaredConstructor(TileDynamic.class, Allegiances.class).newInstance(dynTile, currAllegiance);

        updateModifiers(newbie);

        dynTile.occupy(newbie);

        possibleMoves -= newbie.getSelfModifiers().applyTo(newbie.getStats()).getMoveCost();

        if (newbie instanceof UnitModifying) {

            modUnits.add((UnitModifying) newbie);

        } else {

            units.add(newbie);

        }

        switch (currAllegiance) {

            case RED -> pointsRed -= newbie.getSelfModifiers().applyTo(newbie.getStats()).getSpawnCost();

            case GREEN -> pointsGreen -= newbie.getSelfModifiers().applyTo(newbie.getStats()).getSpawnCost();

        }

    }

    private void update() {

        boardInfoTextLeft.setText(currAllegiance + "'s Turn! (" + possibleMoves + " moves left)");

        updateRecruitments();

        int greenPointTiles = 0;
        int redPointTiles = 0;

        for (TilePoint tp : tilePoints) {

            boolean isBorderingEnemy = true;

            for (int i = -1; i <= 1; i++) {

                for (int j = -1; j <= 1; j++) {

                    Tile scanTile = map.getTileWithIndex(clampValue(map.getTileX(tp) + j, map.getAmntVert() - 1, 0),
                                                        clampValue(map.getTileY(tp) + i, map.getAmntHori() - 1, 0));

                    if (scanTile instanceof TileDynamic) {

                        TileDynamic dynTile = (TileDynamic) scanTile;

                        if (dynTile.getAllegiance() == tp.getAllegiance()) {

                            isBorderingEnemy = false;

                        }

                    }

                }

            }

            if (isBorderingEnemy) {

                tp.switchAllegiance();
                log(currAllegiance + " captured a point!");

            }

            if (tp.getAllegiance() == Allegiances.GREEN) {

                greenPointTiles++;

            } else if (tp.getAllegiance() == Allegiances.RED) {

                redPointTiles++;

            }

        }


        if (greenPointTiles == 0) {

            gameOver(Allegiances.RED);

        } else if (redPointTiles == 0) {

            gameOver(Allegiances.GREEN);

        }

    }

    private void gameOver(Allegiances winner) {

        log(winner + " won the game!");
        boardInfoTextRight.setText("December 18, 1916, Victory!");

        boardScene.removeEventFilter(MouseEvent.MOUSE_PRESSED, mouseHandler);
        boardScene.removeEventFilter(KeyEvent.ANY, keyHandler);

        boardPane.getChildren().removeAll();
    }

    private int clampValue(int value, int max, int min) {

        if (value > max) {
            return max;
        }

        return Math.max(value, min);

    }

    private void updateRecruitments() {

        if (selectionPrimary instanceof TileDynamic) {

            TileDynamic dynTile = (TileDynamic) selectionPrimary;

            for (Button btn : recBtns) {
                btn.setDisable(true);
            }

            boolean nearPoint = false;
            for (TilePoint tp : tilePoints) {

                if (tp.getAllegiance() == currAllegiance) {

                    if (map.isInReach(tp, dynTile, 1)) {

                        nearPoint = true;

                    }

                }

            }

            if (dynTile.getAllegiance() == currAllegiance && !dynTile.isOccupied() && nearPoint) {

                try {

                    //Infantry
                    UnitInspector ui = new UnitInspector(Infantry.class, dynTile);
                    Unit uiresult = ui.getResult();
                    updateModifiers(uiresult);
                    Stats uistats = uiresult.getSelfModifiers().applyTo(ui.getResult().getStats());
                    if (pointsGreen >= uistats.getSpawnCost() && possibleMoves - uistats.getMoveCost() > 0) {
                        recBtns[0].setDisable(false);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        pointsGreenText.setText("Green's Points: " + pointsGreen);
        pointsRedText.setText("Red's Points: " + pointsRed);

    }

    private void applyModifiers(UnitModifying origin, Unit target) {

        Class<? extends Unit> targetType = target.getClass();

        ModManager modM = origin.getModManager();

        //if the modmap has a modifier for the target type
        if (modM.getModMap().containsKey(targetType)) {

            //if the target is in reach of the effect range of the modifying unit
            if (map.isInReach(target.getPosition(), origin.getPosition(),
                    origin.getSelfModifiers().applyTo(origin.getStats()).getEffectRange())) {

                //if the target is registered in the modmap but doesn't have the modifier applied
                if (!target.getSelfModifiers().hasModifier(modM.getModMap().get(targetType))) {

                    target.getSelfModifiers().addModifierOrigin(origin, modM.getModMap().get(targetType));

                }

            //if the target is out of reach yet does have the modifier applied
            } else if (target.getSelfModifiers().hasModifier(modM.getModMap().get(targetType))) {

                target.getSelfModifiers().removeModifierOrigin(origin);

            }

        }

    }

    private void updateModifiers(Unit target) {

        for (UnitModifying modUnit: modUnits) {

            if (map.isInReach(modUnit.getPosition(), target.getPosition(),
                    modUnit.getSelfModifiers().applyTo(modUnit.getStats()).getEffectRange())) {

                applyModifiers(modUnit, target);

            }

        }

    }

    public void log(String text) {

        logBox.getChildren().add(new Text(text));

        if (logBox.getChildren().size() > 20) {

            logBox.getChildren().remove(0);

        }

    }

    public void errLog(IllegalMoves move) {

        String text = move.getText().replace("(ALLEGIANCE)", currAllegiance.toString());

        log(text);

    }

    public int getPoints(Allegiances allegiance) {

        switch (allegiance) {

            case RED: return pointsRed;
            case GREEN: return pointsGreen;

        }

        return 0;

    }

}
