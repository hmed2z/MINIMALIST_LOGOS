package MinimalistLogos;

// Importation de toutes les bibliothèques JavaFX nécessaires à l'application
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
//import javafx.scene.control.Label;

public class DrawingPane extends BorderPane {

    //private Label statusBar;

    public DrawingPane() {
        final Canvas tempCanvas;

        // Création de la zone de dessin
        Canvas canvas = new Canvas(900, 700);
        ColorPicker colorPicker = new ColorPicker();

        // Création et configuration du canvas temporaire
        tempCanvas = new Canvas(800, 600);
        tempCanvas.setMouseTransparent(true);
        canvas.widthProperty().bind(tempCanvas.widthProperty());
        canvas.heightProperty().bind(tempCanvas.heightProperty());

        // Création d'un StackPane pour contenir les deux Canvas
        StackPane canvasContainer = new StackPane(canvas, tempCanvas);

        // Création de l'objet DrawingCanvas en lui passant le canvasContainer et le colorPicker
        DrawingCanvas drawingCanvas = new DrawingCanvas(canvasContainer, colorPicker);

        // Configuration du menu

        // Création de l'objet MenuBarCreator en lui passant l'objet drawingCanvas
        MenuBarCreator menuBarCreator = new MenuBarCreator(drawingCanvas);
        // Création d'un conteneur VBox pour accueillir la barre de menu et le colorPicker
        VBox topContainer = new VBox(menuBarCreator.getMenuBar(), menuBarCreator.getToolBar(), menuBarCreator.getToolBar1(), colorPicker) ;

        // Alignement des éléments au centre
        topContainer.setAlignment(Pos.TOP_CENTER);
        //couleurs.setAlignment(Pos.BOTTOM_CENTER);

        // Définition des marges autour du conteneur
        topContainer.setPadding(new Insets(10, 10, 10, 10));

        // Définition de l'espacement entre les éléments du conteneur
        topContainer.setSpacing(10);
        // Ajout du conteneur en haut du BorderPane
        setTop(topContainer);

        setCenter(canvasContainer);
    }
}