/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Snake;

/**
 *
 * @author LENOVO
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {
    public Main() {
        createUI();
    }

    private void createUI() {
        add(new Game());
        
        setTitle("Chubby Snake");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false); // do not resizable
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, 
                    "Exit the game?", "Confirm Exit",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        }); // show notification
    }

    public static void main(String[] args) {
        Main ex = new Main();
        ex.setVisible(true); // Show screen
    }
}