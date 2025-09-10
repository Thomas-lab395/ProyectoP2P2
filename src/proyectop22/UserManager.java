/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;

// UserManager.java
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String USERS_DIR = "users";
    private final Map<String, Usuario> usuarios = new HashMap<>();

    public UserManager() {
        loadAllUsers();
    }

    private void loadAllUsers() {
        try {
            Path base = Paths.get(USERS_DIR);
            if (!Files.exists(base)) Files.createDirectories(base);

            Files.list(base).filter(Files::isDirectory).forEach(dir -> {
                Path p = dir.resolve("profile.dat");
                if (Files.exists(p)) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(p.toFile()))) {
                        Object obj = ois.readObject();
                        if (obj instanceof Usuario u) {
                            usuarios.put(u.getUsername(), u);
                        }
                    } catch (Exception e) {
                        System.err.println("No se pudo cargar usuario " + dir + ": " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    public boolean usernameExists(String username) {
        return usuarios.containsKey(username);
    }

    public Usuario register(String username, String plainPassword, String nombreCompleto) throws IOException {
        if (usernameExists(username)) throw new IllegalArgumentException("Usuario ya existe.");
        String hash = hashPassword(plainPassword);
        Usuario u = new Usuario(username, hash, nombreCompleto);
        usuarios.put(username, u);
        saveUser(u);
        return u;
    }

    public Usuario authenticate(String username, String plainPassword) throws IOException {
        Usuario u = usuarios.get(username);
        if (u == null) return null;
        String hash = hashPassword(plainPassword);
        if (u.getPasswordHash().equals(hash)) {
            u.setUltimaSesion(LocalDateTime.now());
            u.incrementPartidasJugadas(); // consideramos que iniciar sesión es un intento
            saveUser(u);
            return u;
        }
        return null;
    }

    public void saveUser(Usuario u) throws IOException {
        Path userDir = Paths.get(USERS_DIR, u.getUsername());
        if (!Files.exists(userDir)) Files.createDirectories(userDir);
        Path profile = userDir.resolve("profile.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(profile.toFile()))) {
            oos.writeObject(u);
            oos.flush();
        }
    }

    public Usuario getUsuario(String username) {
        return usuarios.get(username);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] b = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte x : b) sb.append(String.format("%02x", x));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
