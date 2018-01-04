import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlMenu implements ActionListener {

    private Fenetre fenetre;
    private Model model;

    public ControlMenu(Fenetre fenetre, Model model) {
        this.fenetre = fenetre;
        this.model = model;
        fenetre.setControlMenu(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model = new Model();
        fenetre.setModel(model);
        fenetre.initAttributs();
        fenetre.getMainPanel().removeAll();
        fenetre.getMainPanel().revalidate();
        fenetre.getMainPanel().repaint();
        if(e.getSource().equals(fenetre.getItemNewGame1())){ //1 Joueur
            new ControlButtonIA(fenetre, model);
        }else if(e.getSource().equals(fenetre.getItemNewGame2())){ //2 Joueurs
            new ControlButton(fenetre, model);
        }else if(e.getSource().equals(fenetre.getItemQuit())){ //Quitter
            System.exit(0);
        }
        fenetre.addToWindow();
        fenetre.pack();
        fenetre.getMainPanel().setVisible(true);
    }
}
