/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;

// LoginFrame.java
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginFrame extends JFrame {
    private final UserManager userManager;

    public LoginFrame(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Sokoban - Inicio de Sesión");
        setSize(420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(210, 230, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel lblUser = new JLabel("Usuario:");
        JTextField txtUser = new JTextField(15);
        JLabel lblPass = new JLabel("Contraseña:");
        JPasswordField txtPass = new JPasswordField(15);

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegister = new JButton("Registrarse");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblUser, gbc);
        gbc.gridx = 1; panel.add(txtUser, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblPass, gbc);
        gbc.gridx = 1; panel.add(txtPass, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(btnLogin, gbc);
        gbc.gridx = 1; panel.add(btnRegister, gbc);

        add(panel);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña.");
                return;
            }
            try {
                Usuario u = userManager.authenticate(user, pass);
                if (u != null) {
                    JOptionPane.showMessageDialog(this, "Bienvenido " + u.getNombreCompleto());
                    dispose();
                    SwingUtilities.invokeLater(() -> new MenuPrincipal(u, userManager).setVisible(true));
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error autenticando: " + ex.getMessage());
            }
        });

        btnRegister.addActionListener(e -> {
            JTextField txtU = new JTextField();
            JTextField txtN = new JTextField();
            JPasswordField txtP = new JPasswordField();

            Object[] msg = {
                    "Usuario:", txtU,
                    "Nombre Completo:", txtN,
                    "Contraseña:", txtP
            };

            int opt = JOptionPane.showConfirmDialog(this, msg, "Registro", JOptionPane.OK_CANCEL_OPTION);
            if (opt == JOptionPane.OK_OPTION) {
                String u = txtU.getText().trim();
                String n = txtN.getText().trim();
                String p = new String(txtP.getPassword());
                if (u.isEmpty() || n.isEmpty() || p.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                    return;
                }
                try {
                    Usuario newU = userManager.register(u, p, n);
                    JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error guardando usuario: " + ex.getMessage());
                }
            }
        });
    }
}
