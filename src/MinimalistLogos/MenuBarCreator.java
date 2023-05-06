package MinimalistLogos;

// Importation de toutes les bibliothèques JavaFX nécessaires à l'application
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

// Classe pour créer la barre de menus et la barre d'outils
public class MenuBarCreator {

    private MenuBar menuBar;
    private ToolBar toolBar;
    private ToolBar toolBar1;
    private final DrawingCanvas drawingCanvas;
    private final FileManager fileManager;

    // Constructeur de MenuBarCreator prenant en paramètre un DrawingCanvas
    public MenuBarCreator(DrawingCanvas drawingCanvas) {
        // Initialisation du DrawingCanvas et FileManager
        this.drawingCanvas = drawingCanvas;
        this.fileManager = new FileManager(drawingCanvas);

        // Création des éléments de menu FICHIER
        MenuItem nouveau = new MenuItem("Nouveau");
        nouveau.setOnAction(event -> drawingCanvas.newFile());

        MenuItem charger = new MenuItem("Charger Projet");
        //ACTION

        MenuItem enregistrer = new MenuItem("Enregistrer");
        //ACTION

        MenuItem enregistrerSous = new MenuItem("Enregistrer Sous...");
        enregistrerSous.setOnAction(event -> fileManager.save());

        MenuItem quitter = new MenuItem("Quitter");
        quitter.setOnAction(event -> exitFile());

        // Création du menu "Fichier"
        Menu fichierMenu = new Menu("Fichier");
        fichierMenu.getItems().addAll(nouveau, charger, enregistrer, enregistrerSous, quitter); //ouvrir

        // Création des éléments de menu AFFICHAGE
        Menu zoom = new Menu("Zoom");
        MenuItem zoomPlus = new MenuItem("Avant +");
        //ACTION
        MenuItem zoomMoins = new MenuItem("Arrière -");
        //ACTION
        zoom.getItems().addAll(zoomPlus, zoomMoins);

        MenuItem remplirFenetre = new MenuItem("Remplir la Fenêtre");
        //ACTION

        MenuItem quadrillage = new MenuItem("Quadrillage");
        //ACTION

        // Création du menu "Affichage"
        Menu affichageMenu = new Menu("Affichage");
        affichageMenu.getItems().addAll(zoom, remplirFenetre, quadrillage);

        // Création des éléments du menu INSERTION
        MenuItem image = new MenuItem("Image/Photo");
        image.setOnAction(event -> fileManager.load());

        MenuItem texte = new MenuItem("Texte");
        //ACTION

        // Création du menu AIDE
        Menu insertionMenu = new Menu("Insertion");
        insertionMenu.getItems().addAll(image, texte);

        // Création des éléments du menu INSERTION
        MenuItem astuces = new MenuItem("Astuces");
        //ACTION

        MenuItem documentation = new MenuItem("Documentation");
        //ACTION

        // Création du menu "Aide"
        Menu aideMenu = new Menu("Aide");
        aideMenu.getItems().addAll(astuces, documentation);

        /*
        MenuItem helpMenuItem = new MenuItem("Aide");
        helpMenuItem.setOnAction(e -> showHelpDialog());

        void showHelpDialog() {
            // Afficher une boîte de dialogue avec des instructions pour utiliser l'application.
        }
        */

        // Création de la barre de menus
        menuBar = new MenuBar();
        menuBar.getMenus().add(fichierMenu);
        menuBar.getMenus().add(affichageMenu);
        menuBar.getMenus().add(insertionMenu);
        menuBar.getMenus().add(aideMenu);

        // Création des outils (boutons)
        Button pencilTool = new Button();
        String pencil = "src\\MinimalistLogos\\icones\\crayon.png" ;
        Image crayonImg = new Image("file:" + pencil) ;
        ImageView crayonImgView = new ImageView(crayonImg) ;
        crayonImgView.setFitHeight(25) ;
        crayonImgView.setFitWidth(25) ;
        pencilTool.setGraphic(crayonImgView);
        pencilTool.setPrefSize(25,25);
        pencilTool.setOnAction(event -> drawingCanvas.setDrawingMode("crayon"));

        //Gomme
        Button eraserTool = new Button();
        String eraser = "src\\MinimalistLogos\\icones\\gomme.png" ;
        Image gommeImg = new Image("file:" + eraser) ;
        ImageView gommeImgView = new ImageView(gommeImg) ;
        gommeImgView.setFitHeight(25) ;
        gommeImgView.setFitWidth(25) ;
        eraserTool.setGraphic(gommeImgView);
        eraserTool.setPrefSize(25,25);
        eraserTool.setOnAction(event -> drawingCanvas.setDrawingMode("gomme"));

        //Annulation - Rétablissement
        Button undoButton = new Button();
        String annuler = "src\\MinimalistLogos\\icones\\annuler.png" ;
        Image annulerImg = new Image("file:" + annuler) ;
        ImageView annulerImgView = new ImageView(annulerImg) ;
        annulerImgView.setFitHeight(25) ;
        annulerImgView.setFitWidth(25) ;
        undoButton.setGraphic(annulerImgView);
        undoButton.setPrefSize(25,25);
        undoButton.setOnAction(event -> drawingCanvas.undo());

        Button redoButton = new Button();
        String retablir = "src\\MinimalistLogos\\icones\\retablir.png" ;
        Image retablirImg = new Image("file:" + retablir) ;
        ImageView retablirImgView = new ImageView(retablirImg) ;
        retablirImgView.setFitHeight(25) ;
        retablirImgView.setFitWidth(25) ;
        redoButton.setGraphic(retablirImgView);
        redoButton.setPrefSize(25,25);
        //redoButton.setOnAction(e -> drawingCanvas.redo());

        // Création des boutons pour les formes
        Button ligneTool = new Button();
        String ligne = "src\\MinimalistLogos\\icones\\ligne.png" ;
        Image ligneImg = new Image("file:" + ligne) ;
        ImageView ligneImgView = new ImageView(ligneImg) ;
        ligneImgView.setFitHeight(25) ;
        ligneImgView.setFitWidth(25) ;
        ligneTool.setGraphic(ligneImgView);
        ligneTool.setPrefSize(25,25);
        ligneTool.setOnAction(event -> drawingCanvas.setDrawingMode("ligne"));

        Button cercleTool = new Button();
        String cercle = "src\\MinimalistLogos\\icones\\cercle.png" ;
        Image cercleImg = new Image("file:" + cercle) ;
        ImageView cercleImgView = new ImageView(cercleImg) ;
        cercleImgView.setFitHeight(25) ;
        cercleImgView.setFitWidth(25) ;
        cercleTool.setGraphic(cercleImgView);
        cercleTool.setPrefSize(25,25);
        cercleTool.setOnAction(event -> drawingCanvas.setDrawingMode("cercle"));

        Button quadrilatereTool = new Button();
        String quadrilatere = "src\\MinimalistLogos\\icones\\quadrilatere.png" ;
        Image quadrilatereImg = new Image("file:" + quadrilatere) ;
        ImageView quadrilatereImgView = new ImageView(quadrilatereImg) ;
        quadrilatereImgView.setFitHeight(25) ;
        quadrilatereImgView.setFitWidth(25) ;
        quadrilatereTool.setGraphic(quadrilatereImgView);
        quadrilatereTool.setPrefSize(25,25);
        quadrilatereTool.setOnAction(event -> drawingCanvas.setDrawingMode("quadrilatere"));

        // Création de la barre d'outils avec les outils et les boutons de formes
        toolBar = new ToolBar(pencilTool, eraserTool, ligneTool, cercleTool, quadrilatereTool);
        toolBar1 = new ToolBar(undoButton, redoButton);
    }

    // Méthode pour retourner la barre de menus
    public MenuBar getMenuBar() {
        return menuBar;
    }


    // Méthode pour retourner la barre d'outils de dessin
    public ToolBar getToolBar() {
        return toolBar;
    }

    // Méthode pour retourner la barre d'Annulation - Rétablissement d'actions
    public ToolBar getToolBar1() {
        return toolBar1;
    }

    //QUITTER
    public void exitFile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir quitter Minimalist Logos ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}