package verdungame.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import verdungame.Allegiances;
import verdungame.Main;
import verdungame.units.Infantry;

public class Recruiter extends Window {

    private Text pointsGreenText;
    private Text pointsRedText;
    private Button[] recBtns;
    private Main main;

    public Recruiter(int width, int height, Main main) {

        super(width, height, "Recruiter");

        this.main = main;

        generate();

    }

    private void generate() {

        pointsGreenText = new Text("Green's Points: " + main.getPoints(Allegiances.GREEN));
        super.getLayout().getChildren().add((pointsGreenText));

        pointsRedText = new Text("Red's Points: " + main.getPoints(Allegiances.RED));
        super.getLayout().getChildren().add((pointsRedText));

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
            super.getLayout().getChildren().add(btn);
            btn.setDisable(true);

        }

        handleRecruitments();

    }

    private void handleRecruitments() {

        //these are all events, thus they don't need to be rerun
        recBtns[0].setOnAction(e -> {
            try {
                main.spawn(Infantry.class);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

    }

}
