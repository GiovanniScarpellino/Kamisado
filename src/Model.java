import javax.swing.*;
import java.awt.*;

public class Model {

    private Case[][] cases;
    private Color[][] colors;

    public Model() {
        cases = new Case[8][8];
        colors = new Color[][]{
                new Color[]{Color.gray, Color.green, Color.red, Color.yellow, Color.pink, Color.magenta, Color.blue, Color.orange},
                new Color[]{Color.magenta, Color.gray, Color.yellow, Color.blue, Color.green, Color.pink, Color.orange, Color.red},
                new Color[]{Color.blue, Color.yellow, Color.gray, Color.magenta, Color.red, Color.orange, Color.pink, Color.green},
                new Color[]{Color.yellow, Color.red, Color.green, Color.gray, Color.orange, Color.blue, Color.magenta, Color.pink},
                new Color[]{Color.pink, Color.magenta, Color.blue, Color.orange, Color.gray, Color.green, Color.red, Color.yellow},
                new Color[]{Color.green, Color.pink, Color.orange, Color.red, Color.magenta, Color.gray, Color.yellow, Color.blue},
                new Color[]{Color.red, Color.orange, Color.pink, Color.green, Color.blue, Color.yellow, Color.gray, Color.magenta},
                new Color[]{Color.orange, Color.blue, Color.magenta, Color.pink, Color.yellow, Color.red, Color.green, Color.gray}};
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases.length; j++) {
                cases[i][j] = new Case(i, j, isSelected(i), colors[i][j], haveTower(i, j));
            }
        }
    }

    //Pour le constructeur
    private boolean isSelected(int i){
        return i == 0;
    }

    private Icon haveTower(int i, int j){
        if(i == 0) return new IconTower(Color.white, colors[i][j], 1);
        if(i == 7) return new IconTower(Color.black, colors[i][j], 2);
        return null;
    }

    //Getters
    public Case getCase(int i, int j){
        return cases[i][j];
    }

    public Case getCase(Object object){
        JButton jButton = (JButton)object;
        return getCase((int)jButton.getClientProperty("i"), (int)jButton.getClientProperty("j"));
    }

    public Case[][] getCases(){
        return cases;
    }
}
