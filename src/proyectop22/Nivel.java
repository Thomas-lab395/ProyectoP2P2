/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;

// Nivel.java
public class Nivel {
    /*
     * Valores del mapa:
     * 0 = vacío
     * 1 = pared
     * 2 = caja
     * 3 = meta
     * 9 = jugador (solo para definición, se convertirá a 0 y se fija playerX/playerY)
     */

    public static int[][] getTutorial() {
        return new int[][] {
            {1,1,1,1,1,1},
            {1,0,0,3,0,1},
            {1,0,2,0,0,1},
            {1,0,0,9,0,1},
            {1,1,1,1,1,1},
        };
    }

    public static int[][] getNivel1() {
        return new int[][] {
            {1,1,1,1,1,1,1},
            {1,0,0,0,0,3,1},
            {1,0,2,2,0,0,1},
            {1,0,0,9,0,0,1},
            {1,1,1,1,1,1,1},
        };
    }

    public static int[][] getNivel2() {
        return new int[][] {
            {1,1,1,1,1,1,1,1},
            {1,0,0,0,0,3,0,1},
            {1,0,2,1,0,2,0,1},
            {1,0,0,9,0,0,0,1},
            {1,1,1,1,1,1,1,1},
        };
    }

    // Agrega más niveles si lo deseas...
}

