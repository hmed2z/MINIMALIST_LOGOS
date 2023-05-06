package MinimalistLogos;

// Importation de toutes les bibliothèques JavaFX nécessaires à l'application
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Classe MAIN permettant de lancer l'application
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Création de la fenêtre
        DrawingPane drawingPane = new DrawingPane();

        // "Modelage" et affichage de la fenêtre
        Scene scene = new Scene(drawingPane, 900, 700);
        primaryStage.setTitle("MINIMALIST LOGOS BY AHMED");
        primaryStage.setScene(scene);

        // Ajout de la feuille de style CSS
        scene.getStylesheets().add("MinimalistLogos/style.css");

        // Affichage de la fenêtre
        primaryStage.show();
    }

    // Méthode principale pour lancer l'application
    public static void main(String[] args) {
        launch(args);
    }
}