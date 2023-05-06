package MinimalistLogos.formes;

// Importation de toutes les bibliothèques JavaFX nécessaires à l'application
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Ligne implements Forme {
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    // Liste pour stocker toutes les instances de Ligne créées
    private static final List<Ligne> allLines = new ArrayList<>();

    public Ligne() {
        allLines.add(this);
    }

    @Override
    public void setDebut(double x, double y) {
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void setFin(double x, double y) {
        this.endX = x;
        this.endY = y;
    }

    @Override
    public void dessiner(GraphicsContext gc) {
        gc.strokeLine(startX, startY, endX, endY);
    }

    @Override
    public double getStartX() {
        return startX;
    }

    @Override
    public double getStartY() {
        return startY;
    }

    @Override
    public double getEndX() {
        return endX;
    }

    @Override
    public double getEndY() {
        return endY;
    }

    // Méthode pour calculer la distance entre un point (x, y) et la ligne
    public double distanceFromPoint(double x, double y) {
        double x1 = getStartX();
        double y1 = getStartY();
        double x2 = getEndX();
        double y2 = getEndY();

        double lineLength = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        double area = Math.abs((x1 * (y2 - y) + x2 * (y - y1) + x * (y1 - y2)) / 2);
        double distance = (2 * area) / lineLength;

        // Vérifier si le point projeté est sur le segment de ligne
        double dotProduct = ((x - x1) * (x2 - x1) + (y - y1) * (y2 - y1)) / Math.pow(lineLength, 2);
        if (dotProduct < 0 || dotProduct > 1) {
            // Si le point projeté est en dehors du segment de ligne, retourner la distance minimale aux extrémités
            double distanceToStart = Math.sqrt(Math.pow((x - x1), 2) + Math.pow((y - y1), 2));
            double distanceToEnd = Math.sqrt(Math.pow((x - x2), 2) + Math.pow((y - y2), 2));
            distance = Math.min(distanceToStart, distanceToEnd);
        }

        return distance;
    }

    /*
    // Méthode pour déplacer la ligne
    public void move(double deltaX, double deltaY) {
        startX += deltaX;
        startY += deltaY;
        endX += deltaX;
        endY += deltaY;
    }
    */

    /*
    // Méthode pour obtenir la liste de toutes les lignes
    public static List<Ligne> getAllLines() {
        return allLines;
    }
    */

    @Override
    public boolean contains(double x, double y) {
        double distance = distanceFromPoint(x,y);
        double tolerance = 5; // Vous pouvez ajuster la tolérance en fonction de vos besoins
        return distance < tolerance;
    }

    //public double distanceFromPoint(double x, double y) {}
}