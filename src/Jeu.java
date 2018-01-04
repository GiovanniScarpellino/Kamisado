public class Jeu {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                Model model = new Model();
                new ControlGroup(model);
            }
        });
    }

}
