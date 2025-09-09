/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;

// Usuario.java
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String username;
    private final String passwordHash; // SHA-256 hash
    private String nombreCompleto;
    private final LocalDateTime fechaRegistro;
    private LocalDateTime ultimaSesion;
    private final boolean[] nivelesCompletados;
    private long totalSecondsPlayed;
    private int partidasJugadas;
    private final List<String> historial; // simple historial de sesiones/acciones
    private String avatarResourcePath; // ruta dentro de /res/ si el usuario elige avatar
    private int score;

    public Usuario(String username, String passwordHash, String nombreCompleto) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.nombreCompleto = nombreCompleto;
        this.fechaRegistro = LocalDateTime.now();
        this.ultimaSesion = null;
        this.nivelesCompletados = new boolean[7]; // mínimo 7 niveles
        this.totalSecondsPlayed = 0L;
        this.partidasJugadas = 0;
        this.historial = new ArrayList<>();
        this.avatarResourcePath = null;
        this.score = 0;
    }

    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public LocalDateTime getUltimaSesion() { return ultimaSesion; }
    public void setUltimaSesion(LocalDateTime ultimaSesion) { this.ultimaSesion = ultimaSesion; }
    public boolean[] getNivelesCompletados() { return nivelesCompletados; }
    public long getTotalSecondsPlayed() { return totalSecondsPlayed; }
    public void addSecondsPlayed(long s) { totalSecondsPlayed += s; }
    public int getPartidasJugadas() { return partidasJugadas; }
    public void incrementPartidasJugadas() { partidasJugadas++; }
    public List<String> getHistorial() { return historial; }
    public String getAvatarResourcePath() { return avatarResourcePath; }
    public void setAvatarResourcePath(String avatarResourcePath) { this.avatarResourcePath = avatarResourcePath; }
    public int getScore() { return score; }
    public void addScore(int delta) { score += delta; }

    public void marcarNivelCompletado(int nivelIndex) {
        if (nivelIndex >= 0 && nivelIndex < nivelesCompletados.length) {
            nivelesCompletados[nivelIndex] = true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return username.equals(usuario.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
