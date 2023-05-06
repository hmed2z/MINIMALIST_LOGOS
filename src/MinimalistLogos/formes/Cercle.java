package MinimalistLogos.formes;

import javafx.scene.canvas.GraphicsContext;

// Classe Cercle qui implémente l'interface Forme
public class Cercle implements Forme {
    // Coordonnées du centre du cercle et rayon
    private double centerX;
    private double centerY;
    private double radius;

    // Méthode pour définir le point de départ (le centre du cercle)
    @Override
    public void setDebut(double x, double y) {
        this.centerX = x;
        this.centerY = y;
    }

    // Méthode pour définir le point final (un point sur la circonférence du cercle)
    @Override
    public void setFin(double x, double y) {
        double dx = x - centerX;
        double dy = y - centerY;
        // Calcul du rayon en utilisant la distance entre le centre et le point final
        radius = Math.sqrt(dx * dx + dy * dy);
    }

    // Méthode pour dessiner le cercle sur le contexte graphique
    @Override
    public void dessiner(GraphicsContext gc) {
        // Dessine un cercle en utilisant le contexte graphique
        gc.strokeOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }

    // Méthode pour vérifier si un point (x, y) est à l'intérieur du cercle
    public boolean contains(double x, double y) {
        double dx = x - centerX;
        double dy = y - centerY;
        // Calcul de la distance entre le centre du cercle et le point (x, y)
        double distance = Math.sqrt(dx * dx + dy * dy);
        // Si la distance est inférieure ou égale au rayon, le point est à l'intérieur du cercle
        return distance <= radius;
    }

    // Méthodes pour obtenir les coordonnées de départ et de fin du cercle
    @Override
    public double getStartX() {
        return centerX - radius;
    }

    @Override
    public double getStartY() {
        return centerY - radius;
    }

    @Override
    public double getEndX() {
        return centerX + radius;
    }

    @Override
    public double getEndY() {
        return centerY + radius;
    }
}