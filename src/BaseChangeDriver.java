import javax.swing.*;
/**
 * BaseChangeDriver class
 * @author Johnathan Poeschel
 * @version 1.0, 25 Nov 2019
 */
public class BaseChangeDriver {
    /**
     * main method to run the BaseChange GUI
     * @param args
     */
    public static void main(String[] args) {
        BaseChange baseChange = new BaseChange();
        baseChange.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseChange.setSize(200, 400);
        baseChange.setLocationRelativeTo(null);
        baseChange.setVisible(true);
    }
}