/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;
// Recursos.java
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Recursos {
    public static Image jugador;
    public static Image caja;
    public static Image meta;
    public static Image pared;
    public static Image piso;

    public static Image fondoMapa;
    public static Image nivelLibre;
    public static Image nivelBloqueado;

    static {
        jugador = load("/res/moltres1.png");
        caja = load("/res/box.png");
        meta = load("/res/desbloqueo.png");
        pared = load("/res/patron-fisuras-pared-ladrillo-pixel-es-piedra-papel-tapiz-ladrillo-p.png");
        piso = load("/res/Gemini_Generated_Image_1ns5pg1ns5pg1ns5.png");

        fondoMapa = load("/res/fondo.png");
        nivelLibre = load("/res/desbloqueo.png");
        nivelBloqueado = load("/res/unnamed.png");
    }

    private static Image load(String resourcePath) {
        try {
            URL url = Recursos.class.getResource(resourcePath);
            if (url != null) {
                return new ImageIcon(url).getImage();
            } else {
                System.err.println("Recurso no encontrado: " + resourcePath);
            }
        } catch (Exception e) {
            System.err.println("Error cargando recurso " + resourcePath + ": " + e.getMessage());
        }
        return null;
    }
}

