package MinimalistLogos;

// Importation de toutes les bibliothèques JavaFX nécessaires à l'application
import MinimalistLogos.formes.Ligne;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import MinimalistLogos.formes.Forme;
import MinimalistLogos.formes.Cercle;
import MinimalistLogos.formes.Quadrilatere;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.WritableImage;
import java.util.Stack;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class DrawingCanvas {

    private static DrawingCanvas currentInstance;

    /*
    private enum InteractionState {
        SELECTION,
        DEPLACEMENT,
        REMODELLAGE
    }
    */

    //private final InteractionState interactionState = InteractionState.SELECTION;
    private Forme selectedForme = null;
    private Forme forme;
    private Ligne ligne;
    private Ligne selectedLine;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final ColorPicker colorPicker;
    private String drawingMode = "crayon";
    private final List<Ligne> lignes = new ArrayList<>();
    private final GraphicsContext tempGc;
    private final Stack<WritableImage> undoStack;
    private final Stack<WritableImage> redoStack;
    private final Canvas previewCanvas;

    public DrawingCanvas(StackPane canvasContainer, ColorPicker colorPicker) {
        super(); // Appelle le constructeur de la classe parente
        undoStack = new Stack<>(); // Initialise la pile des actions à annuler
        redoStack = new Stack<>(); // Initialise la pile des actions à rétablir
        canvas = new Canvas(800, 500); // Crée un nouveau Canvas de taille 800x500
        previewCanvas = new Canvas(800, 500); // Crée un autre Canvas de taille 800x500 pour prévisualiser les dessins
        previewCanvas.setMouseTransparent(true); // Rend le previewCanvas insensible aux événements de la souris
        tempGc = previewCanvas.getGraphicsContext2D(); // Récupère le contexte graphique du previewCanvas pour le dessin temporaire

        this.colorPicker = colorPicker; // Associe le ColorPicker passé en paramètre à l'attribut de classe

        saveToUndoStack(); // Enregistre l'état initial du canvas dans la pile des actions à annuler

        canvasContainer.getChildren().addAll(canvas, previewCanvas); // Ajoute les deux Canvas au StackPane passé en paramètre

        gc = canvas.getGraphicsContext2D(); // Récupère le contexte graphique du canvas principal pour le dessin

        gc.setStroke(Color.WHITE); // Définit la couleur du trait par défaut à blanc

        // Associe les gestionnaires d'événements aux actions de la souris
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);

        // Met à jour la couleur du trait en fonction de la sélection dans le ColorPicker
        colorPicker.setOnAction(e -> gc.setStroke(colorPicker.getValue()));

        currentInstance = this; // Définit l'instance actuelle comme l'instance courante de la classe

        /*
        this.setOnMousePressed(event -> {
            lastMousePosition = new Point2D(event.getX(), event.getY());

            for (Forme forme : formes) {
                if (forme.contains(event.getX(), event.getY())) {
                    selectedForme = forme;
                    interactionState = InteractionState.DEPLACEMENT;
                    break;
                }
            }
        });

        this.setOnMouseDragged(event -> {
            if (selectedForme != null) {
                double dx = event.getX() - lastMousePosition.getX();
                double dy = event.getY() - lastMousePosition.getY();

                if (interactionState == InteractionState.DEPLACEMENT) {
                    selectedForme.setDebut(selectedForme.getStartX() + dx, selectedForme.getStartY() + dy);
                    selectedForme.setFin(selectedForme.getEndX() + dx, selectedForme.getEndY() + dy);
                } else if (interactionState == InteractionState.REMODELLAGE) {
                    selectedForme.setFin(event.getX(), event.getY());
                }

                lastMousePosition = new Point2D(event.getX(), event.getY());
                redraw();
            }
        });

        this.setOnMouseReleased(event -> {
            interactionState = InteractionState.SELECTION;
        });
        */
    }

    // Méthode getInstance() pour récupérer l'instance actuelle de la classe DrawingCanvas
    public static DrawingCanvas getInstance() {
        return currentInstance; // Retourne l'instance courante de la classe DrawingCanvas
    }

    /*
    public void updateStatusBar(String tool) {
        statusBar.setText("Outil en cours d'utilisation: " + tool);
    }
    */

    /*
    private void startDrawing(MouseEvent event) {
        saveCanvasState();
        gc.beginPath();
        gc.moveTo(event.getX(), event.getY());
        gc.stroke();
    }
    */

    /*
    public void saveCanvasState() {
        WritableImage currentCanvasImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        PixelReader pixelReader = canvas.snapshot(null, currentCanvasImage).getPixelReader();
        WritableImage copiedImage = new WritableImage(pixelReader, (int) canvas.getWidth(), (int) canvas.getHeight());
        undoStack.push(copiedImage);
    }
    */

    // Méthode pour annuler la dernière action effectuée sur le canvas
    public void undo() {
        if (!undoStack.isEmpty()) {
            double width = gc.getCanvas().getWidth();
            double height = gc.getCanvas().getHeight();

            // Sauvegarde de l'état actuel du canvas dans la pile de rétablissement (redoStack)
            WritableImage currentImage = new WritableImage((int) width, (int) height);
            canvas.snapshot(null, currentImage);
            redoStack.push(currentImage);

            // Restauration de l'état précédent du canvas à partir de la pile d'annulation (undoStack)
            WritableImage previousImage = undoStack.pop();
            gc.drawImage(previousImage, 0, 0);
        }
    }

    // Méthode pour sauvegarder l'état actuel du canvas dans la pile d'annulation (undoStack)
    private void saveToUndoStack() {
        WritableImage currentImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, currentImage);
        undoStack.add(currentImage);
    }

    // Méthode pour définir le mode de dessin (crayon, gomme, ligne, cercle, quadrilatère, déplacement, sélection)
    public void setDrawingMode(String mode) {
        saveToUndoStack() ;
        this.drawingMode = mode;
        if (mode.equals("deplacement")) {
            canvas.setCursor(Cursor.MOVE);
        } else {
            canvas.setCursor(Cursor.DEFAULT);
        }
        //updateStatusBar(mode);
    }

    // Méthode pour vérifier si le point (x, y) est proche d'une ligne avec une tolérance donnée
    private boolean isNearLine(double x, double y, Ligne line, double tolerance) {
        double distance = line.distanceFromPoint(x, y);
        return distance < tolerance;
    }

    // Méthode pour gérer l'événement de pression de la souris
    private void handleMousePressed(MouseEvent event) {
        // Les différentes actions à effectuer en fonction du mode de dessin sélectionné
        if (drawingMode.equals("crayon")) {
            gc.setStroke(colorPicker.getValue());
            gc.setLineWidth(2);
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        } else if (drawingMode.equals("gomme")) {
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(10);
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        } else if (drawingMode.equals("ligne")) {
            gc.setStroke(colorPicker.getValue());
            gc.setLineWidth(2);
            ligne = new Ligne();
            ligne.setDebut(event.getX(), event.getY());
        } else if (drawingMode.equals("cercle")) {
                gc.setStroke(colorPicker.getValue());
                gc.setLineWidth(2);
                forme = new Cercle();
                forme.setDebut(event.getX(), event.getY());
        } else if (drawingMode.equals("quadrilatere")) {
                gc.setStroke(colorPicker.getValue());
                gc.setLineWidth(2);
                forme = new Quadrilatere();
                forme.setDebut(event.getX(), event.getY());
        } else if (drawingMode.equals("deplacement")) {
            for (Ligne line : lignes) {
                if (isNearLine(event.getX(), event.getY(), line, 5)) {
                    selectedLine = line;
                    break;
                }
            }
        } else if (drawingMode.equals("selection")) {
            for (Forme forme : formes) {
                if (forme.contains(event.getX(), event.getY())) {
                    selectedForme = forme;
                    break;
                }
            }
        }
    }

    // Méthode pour gérer l'événement de glissement de la souris
    private void handleMouseDragged(MouseEvent event) {
        // Les différentes actions à effectuer en fonction du mode de dessin sélectionné
        if (drawingMode.equals("crayon")) {
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        } else if (drawingMode.equals("gomme")) {
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        } else if (drawingMode.equals("ligne")) {
            previewCanvas.setOpacity(1);
            GraphicsContext previewGc = previewCanvas.getGraphicsContext2D();
            previewGc.clearRect(0, 0, previewCanvas.getWidth(), previewCanvas.getHeight()); // Effacer le contenu du previewGc avant de dessiner
            redrawLines(previewGc); // Redessiner les lignes existantes sur le contexte temporaire
            ligne.setFin(event.getX(), event.getY());
            ligne.dessiner(previewGc);
        } if (drawingMode.equals("cercle") || drawingMode.equals("quadrilatere")) {
            previewCanvas.setOpacity(1);
            GraphicsContext previewGc = previewCanvas.getGraphicsContext2D();
            previewGc.clearRect(0, 0, previewCanvas.getWidth(), previewCanvas.getHeight()); // Effacer le contenu du previewGc avant de dessiner
            redrawFormes(previewGc); // Redessiner les formes existantes sur le contexte temporaire
            forme.setFin(event.getX(), event.getY());
            forme.dessiner(previewGc);
        } else if (drawingMode.equals("deplacement") && selectedForme != null) {
            double dx = event.getX() - selectedForme.getStartX();
            double dy = event.getY() - selectedForme.getStartY();
            selectedForme.setDebut(selectedForme.getStartX() + dx, selectedForme.getStartY() + dy);
            selectedForme.setFin(selectedForme.getEndX() + dx, selectedForme.getEndY() + dy);
            redrawFormes(gc);
        }
    }

    // Méthode pour gérer l'événement de relâchement de la souris
    private void handleMouseReleased(MouseEvent event) {
        // Les différentes actions à effectuer en fonction du mode de dessin sélectionné
        if (drawingMode.equals("crayon") || drawingMode.equals("gomme")) {
            gc.closePath();
        } else if (drawingMode.equals("ligne")) {
            GraphicsContext previewGc = previewCanvas.getGraphicsContext2D();
            previewGc.clearRect(0, 0, previewCanvas.getWidth(), previewCanvas.getHeight());
            gc.beginPath();
            gc.moveTo(ligne.getStartX(), ligne.getStartY());
            gc.lineTo(ligne.getEndX(), ligne.getEndY());
            gc.stroke();
            gc.closePath();
            formes.add(ligne);
            previewCanvas.setOpacity(0);
        } if (drawingMode.equals("cercle") || drawingMode.equals("quadrilatere")) {
            GraphicsContext previewGc = previewCanvas.getGraphicsContext2D();
            previewGc.clearRect(0, 0, previewCanvas.getWidth(), previewCanvas.getHeight());
            gc.beginPath();
            forme.dessiner(gc);
            gc.closePath();
            formes.add(forme);
            previewGc.clearRect(0, 0, previewCanvas.getWidth(), previewCanvas.getHeight());
            previewCanvas.setOpacity(0);
        } else if (drawingMode.equals("deplacement")) {
            selectedLine = null;
        }
    }

    /*
    void attrape(MouseEvent e) {
        for (int i = 0; i < modele.getSize(); i++) {
            FormeGeo f = modele.get(i);
            if (f instanceof Ligne && f.estDedans(e.getX(), e.getY())) {
                formeIdx = i;
                enDeplacement = true;
                x_souris = e.getX();
                y_souris = e.getY();
                break;
            } else if (f instanceof Cercle && f.estDedans(e.getX(), e.getY())) {
                formeIdx = i;
                enDeplacement = true;
                x_souris = e.getX();
                y_souris = e.getY();
                break;
            } else if (f instanceof Quadrilatere && f.estDedans(e.getX(), e.getY())) {
                formeIdx = i;
                enDeplacement = true;
                x_souris = e.getX();
                y_souris = e.getY();
                break;
            }
        }
    }
    */

    // Liste pour stocker toutes les formes dessinées sur le canvas
    private List<Forme> formes = new ArrayList<>();

    // Méthode pour redessiner toutes les formes sur le canvas
    private void redrawFormes(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Effacer le contenu du canvas
        for (Forme forme : formes) {
            forme.dessiner(gc); // Dessiner chaque forme à partir de la liste des formes
        }
    }

    // Méthode pour redessiner toutes les lignes sur le canvas
    private void redrawLines(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Effacer le contenu du canvas
        for (Ligne line : lignes) {
            line.dessiner(gc); // Dessiner chaque ligne à partir de la liste des lignes
        }
    }

    /*
    private void redraw() {
        gc.clearRect(0, 0, getWidth(), getHeight());

        for (Forme forme : formes) {
            forme.dessiner(gc);
            if (selectedForme == forme) {
                gc.setStroke(Color.GREEN);
                gc.setLineWidth(2);
                gc.strokeRect(forme.getStartX() - 2, forme.getStartY() - 2,
                        forme.getEndX() - forme.getStartX() + 4,
                        forme.getEndY() - forme.getStartY() + 4);
            }
        }
    }
    */

    // Méthode pour obtenir le canvas
    public Canvas getCanvas() {
        return canvas;
    }

    // Méthode pour effacer le contenu du canvas et vider la liste des formes
    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        formes.clear();
    }

    // Méthode pour créer un nouveau fichier (après confirmation de l'utilisateur)
    public void newFile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir créer un nouveau fichier et d'écraser votre fichier actuel ?");

        // Affichage de la boîte de dialogue et récupération de la réponse de l'utilisateur
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            getInstance().clearCanvas(); // Effacer le canvas si l'utilisateur confirme la création d'un nouveau fichier
        }
    }

    /*
    public void deleteSelectedForme() {
        if (selectedForme != null) {
            formes.remove(selectedForme);
            redrawFormes(gc);
            selectedForme = null;
        }
    }
    */
}