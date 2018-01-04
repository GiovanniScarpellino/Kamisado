import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlButton implements ActionListener {

    private Fenetre fenetre;
    private Model model;
    private Case clickedCase;
    private Case previousCase;
    private int player = 1;
    private Color forcedColor;

    public ControlButton(Fenetre fenetre, Model model) {
        this.fenetre = fenetre;
        this.model = model;
        fenetre.setControlButton(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        previousCase = clickedCase;
        clickedCase = model.getCase(e.getSource());

        if(previousCase != null) {
            switchPlayer();
            forcedColor = clickedCase.getCaseColor();
        }

        for (int i = 0; i < model.getCases().length; i++) {
            for (int j = 0; j < model.getCases().length; j++) {
                clear(i,j);
                if(previousCase != null) showWhoPlay(i,j);
            }
        }

        if(previousCase == null) {
            showDeplacement();
            checkBloqued();
        }else{
            moveTo();
            if(checkWin()){
                for (int i = 0; i < model.getCases().length; i++) {
                    for (int j = 0; j < model.getCases().length; j++) {
                        clear(i,j);
                    }
                }
            }
            clickedCase = null;
            previousCase = null;
        }
    }

    private void clear(int i, int j) {
        getjButton(i,j).setEnabled(false);
        getjButton(i,j).setBorder(null);
    }

    public void showDeplacement(){
        if(clickedCase.getIcon().getNumPlayer() == 1){
            boolean stopLine = false;
            boolean stopDiagoLeft = false;
            boolean stopDiagoRight = false;
            //Ligne
            for (int i = clickedCase.getPosI(); i < model.getCases().length && !stopLine; i++) {
                if(model.getCase(i, clickedCase.getPosJ()).haveTower() && i != clickedCase.getPosI()) stopLine = true;
                if(!stopLine){
                    getjButton(i, clickedCase.getPosJ()).setEnabled(true);
                    getjButton(i, clickedCase.getPosJ()).setBorder(new LineBorder(Color.black, 3));
                }
                if(i == clickedCase.getPosI()) getjButton(clickedCase.getPosI(), clickedCase.getPosJ()).setEnabled(false);
            }
            //Digonal gauche
            for (int i = clickedCase.getPosI(), j = clickedCase.getPosJ(); j >= 0 && i < model.getCases().length && !stopDiagoLeft; j--, i++) {
                if(model.getCase(i,j).haveTower() && i != clickedCase.getPosI()) stopDiagoLeft = true;
                if(!stopDiagoLeft){
                    getjButton(i,j).setEnabled(true);
                    getjButton(i,j).setBorder(new LineBorder(Color.black, 3));
                }
                if(i == clickedCase.getPosI()) getjButton(clickedCase.getPosI(), clickedCase.getPosJ()).setEnabled(false);
            }
            //Diagonal Droite
            for (int i = clickedCase.getPosI(), j = clickedCase.getPosJ(); j < model.getCases().length && i < model.getCases().length && !stopDiagoRight; j++, i++) {
                if(model.getCase(i,j).haveTower() && i != clickedCase.getPosI()) stopDiagoRight = true;
                if(!stopDiagoRight){
                    getjButton(i,j).setEnabled(true);
                    getjButton(i,j).setBorder(new LineBorder(Color.black, 3));
                }
                if(i == clickedCase.getPosI()) getjButton(clickedCase.getPosI(), clickedCase.getPosJ()).setEnabled(false);
            }
        }else{
            boolean stopLine = false;
            boolean stopDiagoLeft = false;
            boolean stopDiagoRight = false;
            //Ligne
            for (int i = clickedCase.getPosI(); i >= 0 && !stopLine; i--) {
                if(model.getCase(i, clickedCase.getPosJ()).haveTower() && i != clickedCase.getPosI()) stopLine = true;
                if(!stopLine){
                    getjButton(i, clickedCase.getPosJ()).setEnabled(true);
                    getjButton(i, clickedCase.getPosJ()).setBorder(new LineBorder(Color.black, 3));
                }
                if(i == clickedCase.getPosI()) getjButton(clickedCase.getPosI(), clickedCase.getPosJ()).setEnabled(false);
            }
            //Digonal gauche
            for (int i = clickedCase.getPosI(), j = clickedCase.getPosJ(); j >= 0 && i >= 0 && !stopDiagoLeft; j--, i--) {
                if(model.getCase(i,j).haveTower() && i != clickedCase.getPosI()) stopDiagoLeft = true;
                if(!stopDiagoLeft){
                    getjButton(i,j).setEnabled(true);
                    getjButton(i,j).setBorder(new LineBorder(Color.black, 3));
                }
                if(i == clickedCase.getPosI()) getjButton(clickedCase.getPosI(), clickedCase.getPosJ()).setEnabled(false);
            }
            //Diagonal Droite
            for (int i = clickedCase.getPosI(), j = clickedCase.getPosJ(); j < model.getCases().length && i >= 0 && !stopDiagoRight; j++, i--) {
                if(model.getCase(i,j).haveTower() && i != clickedCase.getPosI()) stopDiagoRight = true;
                if(!stopDiagoRight){
                    getjButton(i,j).setEnabled(true);
                    getjButton(i,j).setBorder(new LineBorder(Color.black, 3));
                }
                if(i == clickedCase.getPosI()) getjButton(clickedCase.getPosI(), clickedCase.getPosJ()).setEnabled(false);
            }
        }
    }

    private void checkBloqued(){
        int isBloqued = 0;
        if(clickedCase.getIcon().getNumPlayer() == 1){
            if(model.getCase(clickedCase.getPosI() + 1, clickedCase.getPosJ()).haveTower()) isBloqued++;
            if(clickedCase.getPosJ() + 1 == model.getCases().length || model.getCase(clickedCase.getPosI() + 1, clickedCase.getPosJ() + 1).haveTower()) isBloqued++;
            if(clickedCase.getPosJ() - 1 == -1 || model.getCase(clickedCase.getPosI() + 1, clickedCase.getPosJ() - 1).haveTower()) isBloqued++;
        }else if(clickedCase.getIcon().getNumPlayer() == 2){
            if(model.getCase(clickedCase.getPosI() - 1, clickedCase.getPosJ()).haveTower()) isBloqued++;
            if(clickedCase.getPosJ() + 1 == model.getCases().length || model.getCase(clickedCase.getPosI() - 1, clickedCase.getPosJ() + 1).haveTower()) isBloqued++;
            if(clickedCase.getPosJ() - 1 == -1 || model.getCase(clickedCase.getPosI() - 1, clickedCase.getPosJ() - 1).haveTower()) isBloqued++;
        }
        if(isBloqued == 3){
            creerDialog("Le matche est nul !");
        }
    }

    private void moveTo(){
        getjButton(clickedCase).setIcon(getjButton(previousCase).getIcon());
        clickedCase.setIcon(getjButton(previousCase).getIcon());
        getjButton(previousCase).setIcon(null);
        previousCase.setIcon(null);
    }

    private boolean checkWin(){
        if(clickedCase.getIcon().getNumPlayer() == 1 && clickedCase.getPosI() == model.getCases().length - 1){
            creerDialog("Joueur 1 gagne !");
            return true;
        }else if(clickedCase.getIcon().getNumPlayer() == 2 && clickedCase.getPosI() == 0){
            creerDialog("Joueur 2 gagne !");
            return true;
        }
        return false;
    }

    private void switchPlayer(){
        switch (player){
            case 1:
                player = 2;
                break;
            case 2:
                player = 1;
                break;
        }
    }

    private void showWhoPlay(int i, int j){
        if(model.getCase(i,j).haveTower() && model.getCase(i,j).getIcon().getNumPlayer() == player && model.getCase(i,j).getIcon().getColorRect() == forcedColor){
            getjButton(i,j).setEnabled(true);
            getjButton(i,j).setBorder(new LineBorder(Color.black, 3));
        }
    }

    private void creerDialog(String message) {
        JOptionPane.showMessageDialog(fenetre, message, "Gagnant !", JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton getjButton(int i, int j) {
        return fenetre.getjButton(i, j);
    }

    private JButton getjButton(Case selectedCase) {
        return getjButton(selectedCase.getPosI(), selectedCase.getPosJ());
    }
}
