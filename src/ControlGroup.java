import javax.swing.*;
import java.awt.*;

public class ControlGroup {
    public ControlGroup(Model model) {
        Fenetre fenetre = new Fenetre(model);
        new ControlButton(fenetre, model);
        new ControlMenu(fenetre, model);
        fenetre.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        fenetre.setLocation(dim.width/2-fenetre.getSize().width/2, dim.height/2-fenetre.getSize().height/2);
        fenetre.setResizable(false);
        fenetre.setTitle("Projet IHM SCARPELLINO Giovanni");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
    }
}
