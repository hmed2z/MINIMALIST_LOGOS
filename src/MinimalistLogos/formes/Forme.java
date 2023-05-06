package MinimalistLogos.formes;

import javafx.scene.canvas.GraphicsContext;

// Interface Forme définissant les méthodes communes pour toutes les formes géométriques
public interface Forme {

    // Méthode pour dessiner la forme sur le GraphicsContext donné
    void dessiner(GraphicsContext gc);

    // Méthode pour définir les coordonnées de départ (x, y) de la forme
    void setDebut(double x, double y);

    // Méthode pour définir les coordonnées de fin (x, y) de la forme
    void setFin(double x, double y);

    // Méthode pour vérifier si un point (x, y) est contenu dans la forme
    boolean contains(double x, double y);

    // Méthodes pour obtenir les coordonnées de départ et de fin de la forme
    double getStartX();

    double getStartY();

    double getEndX();

    double getEndY();
}