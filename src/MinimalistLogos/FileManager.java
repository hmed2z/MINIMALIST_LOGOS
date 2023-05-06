package MinimalistLogos;

// Importation de toutes les bibliothèques JavaFX nécessaires à l'application
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileManager {
    // Variable non statique pour stocker une instance de DrawingCanvas
    private final DrawingCanvas drawingCanvas;

    // Constructeur qui prend une instance de DrawingCanvas en paramètre
    public FileManager(DrawingCanvas drawingCanvas) {
        this.drawingCanvas = drawingCanvas;
    }

    // Méthode non statique pour sauvegarder l'image
    public void save() {
        // Création d'un FileChooser pour choisir le fichier de sauvegarde
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Drawing");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Images", "*.png")
        );
        // Affichage du FileChooser et récupération du fichier choisi
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                // Récupération du Canvas à partir de DrawingCanvas
                Canvas canvas = drawingCanvas.getCanvas();
                // Création d'une image WritableImage à partir du Canvas
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                // Capture d'une image à partir du Canvas
                canvas.snapshot(null, writableImage);
                // Écriture de l'image dans le fichier au format PNG
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            } catch (IOException ex) {
                System.err.println("Error saving the drawing: " + ex.getMessage());
            }
        }
    }

    // Méthode non statique pour charger une image
    public void load() {
        // Création d'un FileChooser pour choisir le fichier à charger
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Drawing");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Images", "*.png")
        );
        // Affichage du FileChooser et récupération du fichier choisi
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                // Lecture de l'image à partir du fichier
                Image image = new Image(new FileInputStream(file));
                // Récupération du Canvas à partir de DrawingCanvas
                Canvas canvas = drawingCanvas.getCanvas();
                // Récupération du GraphicsContext pour dessiner sur le Canvas
                GraphicsContext gc = canvas.getGraphicsContext2D();
                // Effacement du contenu précédent du Canvas
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                // Dessin de l'image chargée sur le Canvas
                (gc).drawImage(image, 0, 0);
            } catch (IOException ex) {
                System.err.println("Error loading the drawing: " + ex.getMessage());
            }
        }
    }
}