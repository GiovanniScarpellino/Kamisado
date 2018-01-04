import javax.swing.*;
import java.awt.*;

public class IconTower implements Icon{

    private int width, height;
    private Color colorRect, colorCircle;
    private int numPlayer;

    public IconTower(Color colorCircle, Color colorRect, int numPlayer) {
        this.colorCircle = colorCircle;
        this.colorRect = colorRect;
        this.width = 50;
        this.height = 50;
        this.numPlayer = numPlayer;
    }

    public Color getColorCircle() {
        return colorCircle;
    }

    public Color getColorRect(){
        return colorRect;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    //Déssine la tour sous forme d'icone
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(colorCircle);
        g.fillOval(x - 20, y - 20, 90, 90);
        g.setColor(colorRect);
        g.fillRect(x, y, 50, 50);
    }

    public int getIconWidth() {
        return width;
    }

    public int getIconHeight() {
        return height;
    }
}
