package MinimalistLogos.formes;

import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point2D;

// Classe Quadrilatere implémentant l'interface Forme
public class Quadrilatere implements Forme {
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    // Tableau pour stocker les 4 points du quadrilatère
    private final Point2D[] points = new Point2D[4];

    // Implémentation des méthodes de l'interface Forme
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
        double width = endX - startX;
        double height = endY - startY;
        gc.strokeRect(startX, startY, width, height); // Dessine un rectangle avec les coordonnées et dimensions spécifiées
    }

    @Override
    public boolean contains(double x, double y) {
        // Vous pouvez utiliser l'algorithme de Ray casting pour vérifier si un point est à l'intérieur d'un polygone
        boolean inside = false;
        int n = 4; // Le nombre de sommets du quadrilatère
        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = points[i].getX();
            double yi = points[i].getY();
            double xj = points[j].getX();
            double yj = points[j].getY();

            if (((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)) {
                inside = !inside;
            }
        }
        return inside;
    }

    @Override
    public double getStartX() {
        return Math.min(Math.min(points[0].getX(), points[1].getX()), Math.min(points[2].getX(), points[3].getX()));
    }

    @Override
    public double getStartY() {
        return Math.min(Math.min(points[0].getY(), points[1].getY()), Math.min(points[2].getY(), points[3].getY()));
    }

    @Override
    public double getEndX() {
        return Math.max(Math.max(points[0].getX(), points[1].getX()), Math.max(points[2].getX(), points[3].getX()));
    }

    @Override
    public double getEndY() {
        return Math.max(Math.max(points[0].getY(), points[1].getY()), Math.max(points[2].getY(), points[3].getY()));
    }
}