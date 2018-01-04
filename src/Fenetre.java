import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Fenetre extends JFrame{

    private JButton[][] jButtons;

    private JMenuBar barMenu;
    private JMenuItem itemNewGame1;
    private JMenuItem itemNewGame2;
    private JMenuItem itemQuit;

    private Model model;

    private JPanel mainPanel;

    public Fenetre(Model model) {
        this.model = model;
        initAttributs();
        initMenu();
        addToWindow();
    }

    public void initAttributs(){
        jButtons = new JButton[8][8];
        for(int i = 0; i < jButtons.length; i++){
            for(int j = 0; j < jButtons.length; j++){
                jButtons[i][j] = new JButton(model.getCase(i,j).getIcon());
                jButtons[i][j].setPreferredSize(new Dimension(100, 100));
                jButtons[i][j].setBackground(model.getCase(i,j).getCaseColor());
                jButtons[i][j].putClientProperty("i", i);
                jButtons[i][j].putClientProperty("j", j);
                if(!model.getCase(i,j).isSelected())jButtons[i][j].setEnabled(false);
                jButtons[i][j].setBorder(null);
                if(i == 0)jButtons[i][j].setBorder(new LineBorder(Color.black, 3));
            }
        }
    }

    private void initMenu(){
        //Menu
        barMenu = new JMenuBar();
        JMenu menu = new JMenu("Options");

        itemNewGame1 = new JMenuItem("Nouvelle partie (1 joueur)");
        itemNewGame2 = new JMenuItem("Nouvelle partie (2 joueurs)");
        itemQuit = new JMenuItem("Quitter");

        menu.add(itemNewGame1);
        menu.add(itemNewGame2);
        menu.add(itemQuit);

        barMenu.add(menu);
        setJMenuBar(barMenu);
    }

    public void addToWindow(){
        mainPanel = new JPanel(new GridLayout(8, 8));
        for (JButton[] jButton : jButtons) {
            for (int j = 0; j < jButtons.length; j++) {
                mainPanel.add(jButton[j]);
            }
        }
        setContentPane(mainPanel);
    }

    public void setControlButton(ControlButton controlButton){
        for (int i = 0; i < jButtons.length; i++) {
            for (int j = 0; j < jButtons.length; j++) {
                jButtons[i][j].addActionListener(controlButton);
            }
        }
    }

    public void setControlMenu(ControlMenu controlMenu){
        itemNewGame1.addActionListener(controlMenu);
        itemNewGame2.addActionListener(controlMenu);
        itemQuit.addActionListener(controlMenu);
    }

    public void setControlButtonIA(ControlButtonIA controlButtonIA){
        for (int i = 0; i < jButtons.length; i++) {
            for (int j = 0; j < jButtons.length; j++) {
                jButtons[i][j].addActionListener(controlButtonIA);
            }
        }
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public JButton[][] getjButtons() {
        return jButtons;
    }

    public JButton getjButton(int i, int j){return jButtons[i][j];}

    public JMenuItem getItemNewGame1() {
        return itemNewGame1;
    }

    public JMenuItem getItemNewGame2() {
        return itemNewGame2;
    }

    public JMenuItem getItemQuit() {
        return itemQuit;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
