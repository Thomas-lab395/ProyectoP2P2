/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;
// Main.java
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManager um = new UserManager();
            LoginFrame lf = new LoginFrame(um);
            lf.setVisible(true);
        });
    }
}
