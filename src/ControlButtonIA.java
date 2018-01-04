import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class ControlButtonIA implements ActionListener {

    private Fenetre fenetre;
    private Model model;
    private Case previousCase;
    private Case clickedCase;
    private int player = 1;
    private Color forcedColor;
    private boolean stopLoop;

    public ControlButtonIA(Fenetre fenetre, Model model) {
        this.fenetre = fenetre;
        this.model = model;
        this.fenetre.setControlButtonIA(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stopLoop = false;
        previousCase = clickedCase;
        clickedCase = model.getCase(e.getSource());

        if (previousCase != null) { //Quand le joueur à déplacé sa tour
            switchPlayer();
            forcedColor = clickedCase.getCaseColor();
            moveTo(clickedCase, previousCase);
        }

        for (int i = 0; i < model.getCases().length; i++) {
            for (int j = 0; j < model.getCases().length; j++) {
                clear(i, j);
                if (previousCase != null) {
                    playIA(i, j);
                }
            }
        }
        for (int i = 0; i < model.getCases().length; i++) {
            for (int j = 0; j < model.getCases().length; j++) {
                showWhoPlay(i, j);
            }
        }
        if (previousCase == null) {
            showDeplacement();
            checkBloqued();
        } else {
            if (checkWin()) {
                for (int i = 0; i < model.getCases().length; i++) {
                    for (int j = 0; j < model.getCases().length; j++) {
                        clear(i, j);
                    }
                }
            }
            clickedCase = null;
            previousCase = null;
        }
    }

    private void clear(int i, int j) {
        getjButton(i, j).setEnabled(false);
        getjButton(i, j).setBorder(null);
    }

    private void showDeplacement() {
        if (clickedCase.getIcon().getNumPlayer() == 1) {
            calculLine();
            calculDiagonalGauche();
            calculDiagonalDroite();
        }
    }

    private void calculLine() {
        boolean stopLine = false;
        //Ligne
        for (int i = clickedCase.getPosI(); i < model.getCases().length && !stopLine; i++) {
            if (model.getCase(i, clickedCase.getPosJ()).haveTower() && i != clickedCase.getPosI()) stopLine = true;
            if (!stopLine) {
                getjButton(i, clickedCase.getPosJ()).setEnabled(true);
                getjButton(i, clickedCase.getPosJ()).setBorder(new LineBorder(Color.black, 3));
            }
            if (i == clickedCase.getPosI())
                getjButton(clickedCase.getPosI(), clickedCase.getPosJ()).setEnabled(false);
        }
    }

    private void calculDiagonalGauche() {
        boolean stopDiagoLeft = false;
        //Digonal gauche
        for (int i = clickedCase.getPosI(), j = clickedCase.getPosJ(); j >= 0 && i < model.getCases().length && !stopDiagoLeft; j--, i++) {
            if (model.getCase(i, j).haveTower() && i != clickedCase.getPosI()) stopDiagoLeft = true;
            if (!stopDiagoLeft) {
                getjButton(i, j).setEnabled(true);
                getjButton(i, j).setBorder(new LineBorder(Color.black, 3));
            }
            if (i == clickedCase.getPosI())
                getjButton(clickedCase.getPosI(), clickedCase.getPosJ()).setEnabled(false);
        }
    }

    private void calculDiagonalDroite() {
        boolean stopDiagoRight = false;
        //Diagonal Droite
        for (int i = clickedCase.getPosI(), j = clickedCase.getPosJ(); j < model.getCases().length && i < model.getCases().length && !stopDiagoRight; j++, i++) {
            if (model.getCase(i, j).haveTower() && i != clickedCase.getPosI()) stopDiagoRight = true;
            if (!stopDiagoRight) {
                getjButton(i, j).setEnabled(true);
                getjButton(i, j).setBorder(new LineBorder(Color.black, 3));
            }
            if (i == clickedCase.getPosI())
                getjButton(clickedCase.getPosI(), clickedCase.getPosJ()).setEnabled(false);
        }
    }

    private int calculLine(Case caseIa) {
        int random = new Random().nextInt(model.getCases().length-2)+1;
        for (int i = caseIa.getPosI(); (i >= random); i--) {
            if (model.getCase(i, caseIa.getPosJ()).haveTower() && i != caseIa.getPosI()) return i;
        }
        return random;
    }

    private String calculDiagonalGauche(Case caseIa) {
        int random = (int)(Math.random() * model.getCases().length);
        for (int i = caseIa.getPosI(), j = caseIa.getPosJ(); j >= random && i >= random; j--, i--) {
            if (model.getCase(i, j).haveTower() && i != caseIa.getPosI()) return i + "-" + j;
        }
        return random+"-"+random;
    }

    private String calculDiagonalDroite(Case caseIa) {
        int randomI = (int)((Math.random()+caseIa.getPosI()) * model.getCases().length);
        int randomJ = (int)((Math.random()+caseIa.getPosJ()) * model.getCases().length);
        System.out.println(randomI);
        System.out.println(randomJ);
        for (int i = caseIa.getPosI(), j = caseIa.getPosJ(); j < randomJ && i >= randomI; j++, i--) {
            if (model.getCase(i, j).haveTower() && i != caseIa.getPosI()) return i + "-" + j;
        }
        return randomI+"-"+randomJ;
    }

    private void checkBloqued() {
        int isBloqued = 0;
        if (clickedCase.getIcon().getNumPlayer() == 1) {
            if (model.getCase(clickedCase.getPosI() + 1, clickedCase.getPosJ()).haveTower()) isBloqued++;
            if (clickedCase.getPosJ() + 1 == model.getCases().length || model.getCase(clickedCase.getPosI() + 1, clickedCase.getPosJ() + 1).haveTower())
                isBloqued++;
            if (clickedCase.getPosJ() - 1 == -1 || model.getCase(clickedCase.getPosI() + 1, clickedCase.getPosJ() - 1).haveTower())
                isBloqued++;
        } else if (clickedCase.getIcon().getNumPlayer() == 2) {
            if (model.getCase(clickedCase.getPosI() - 1, clickedCase.getPosJ()).haveTower()) isBloqued++;
            if (clickedCase.getPosJ() + 1 == model.getCases().length || model.getCase(clickedCase.getPosI() - 1, clickedCase.getPosJ() + 1).haveTower())
                isBloqued++;
            if (clickedCase.getPosJ() - 1 == -1 || model.getCase(clickedCase.getPosI() - 1, clickedCase.getPosJ() - 1).haveTower())
                isBloqued++;
        }
        if (isBloqued == 3) {
            creerDialog("Le matche est nul !");
        }
    }

    private void moveTo(Case clickedCase, Case previousCase) {
        getjButton(clickedCase).setIcon(getjButton(previousCase).getIcon());
        clickedCase.setIcon(getjButton(previousCase).getIcon());
        getjButton(previousCase).setIcon(null);
        previousCase.setIcon(null);
    }

    private boolean checkWin() {
        if (clickedCase.getIcon().getNumPlayer() == 1 && clickedCase.getPosI() == model.getCases().length - 1) {
            creerDialog("Joueur 1 gagne !");
            return true;
        } else if (clickedCase.getIcon().getNumPlayer() == 2 && clickedCase.getPosI() == 0) {
            creerDialog("Joueur 2 gagne !");
            return true;
        }
        return false;
    }

    private void switchPlayer() {
        switch (player) {
            case 1:
                player = 2;
                break;
            case 2:
                player = 1;
                break;
        }
    }

    private void playIA(int i, int j) {
        if (model.getCase(i, j).haveTower() && model.getCase(i, j).getIcon().getNumPlayer() == 2 && model.getCase(i, j).getIcon().getColorRect() == forcedColor && !stopLoop) {
            int random = (int)(Math.random()*1);
            switch(random){
                case 0:
                    int posI = calculLine(model.getCase(i,j));
                    System.out.println(posI);
                    moveTo(model.getCase(i-posI,j), model.getCase(i,j));
                    forcedColor = model.getCase(i-posI,j).getCaseColor();
                    break;
                case 1:
                    String pos = calculDiagonalGauche(model.getCase(i, j));
                    posI = Integer.parseInt(pos.split("-")[0]);
                    int posJ = Integer.parseInt(pos.split("-")[1]);
                    moveTo(model.getCase(i-posI,j-posJ), model.getCase(i,j));
                    forcedColor = model.getCase(i-posI, j-posJ).getCaseColor();
                    break;
                case 2:
                    pos = calculDiagonalDroite(model.getCase(i, j));
                    posI = Integer.parseInt(pos.split("-")[0]);
                    posJ = Integer.parseInt(pos.split("-")[1]);
                    moveTo(model.getCase(i-posI,j-posJ), model.getCase(i,j));
                    forcedColor = model.getCase(i-posI, j-posJ).getCaseColor();
                    break;
            }
            switchPlayer();
            stopLoop = true;
        }
    }

    private void showWhoPlay(int i, int j) {
        if (model.getCase(i, j).haveTower() && model.getCase(i, j).getIcon().getNumPlayer() == player && model.getCase(i, j).getIcon().getColorRect() == forcedColor) {
            getjButton(i, j).setEnabled(true);
            getjButton(i, j).setBorder(new LineBorder(Color.black, 3));
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
