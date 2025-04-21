package br.edu.ifba.inf008.interfaces;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public interface IUIController
{
    public abstract void showOverdueBooksReport(VBox reportContainer);
    public abstract void showReportsPage(Stage stage);
}