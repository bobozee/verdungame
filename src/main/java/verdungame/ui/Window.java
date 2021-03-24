package verdungame.ui;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public abstract class Window {

    private Scene scene;
    private VBox layout;
    private Stage stage;

    public Window(int width, int height, String title) {

        layout = new VBox();
        scene = new Scene(layout, width, height);
        stage = new Stage();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }

    protected Scene getScene() {
        return scene;
    }

    protected VBox getLayout() {
        return layout;
    }

    protected Stage getStage() {
        return stage;
    }

}
