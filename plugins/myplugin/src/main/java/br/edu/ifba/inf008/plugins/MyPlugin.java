package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyPlugin implements IPlugin {
    public boolean init() {
        IUIController uiController = ICore.getInstance().getUIController();

        VBox reportContainer = new VBox();
        uiController.showOverdueBooksReport(reportContainer);

        Platform.runLater(() -> {
            Stage reportStage = new Stage();
            uiController.showReportsPage(reportStage);
        });

        return true;
    }
}