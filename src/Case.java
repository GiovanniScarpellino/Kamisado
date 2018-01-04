import javax.swing.*;
import java.awt.*;

public class Case{

    private Icon icon;
    private int posI, posJ;
    private boolean isSelected;
    private Color caseColor;

    public Case(int posI, int posJ, boolean isSelected, Color caseColor, Icon icon) {
        this.posI = posI;
        this.posJ = posJ;
        this.isSelected = isSelected;
        this.caseColor = caseColor;
        this.icon = icon;
    }

    //Setters
    public void setIcon(Icon icon){
        this.icon = icon;
    }

    //Getters
    public int getPosI() {
        return posI;
    }

    public int getPosJ() {
        return posJ;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public IconTower getIcon(){
        return (IconTower)icon;
    }

    public Color getCaseColor(){
        return caseColor;
    }

    public boolean cantBeSelected(){
        return !(posI < 0 || posI > 7 || posJ < 0 || posJ > 7);
    }

    //Méthodes
    public boolean haveTower(){
        return icon != null;
    }
}
