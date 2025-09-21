/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.sokoban;

// Nivel.java
public class Nivel {

    public static int[][] getNivelByIndex(int index) {
        switch (index) {
            case 0: return getNivel1();
            case 1: return getNivel2();
            case 2: return getNivel3();
            // Agrega aquí los casos para los niveles 4, 5, 6 y 7
            default: return getNivel1(); // Nivel por defecto
        }
    }

    public static int[][] getNivel1() {
        return new int[][] {
            {1,1,1,1,1,1,1},
            {1,9,0,1,0,0,1},
            {1,0,2,3,0,2,1},
            {1,0,0,1,3,0,1},
            {1,1,1,1,1,1,1},
        };
    }

    public static int[][] getNivel2() {
        return new int[][] {
            {1,1,1,1,1,1,1,1},
            {1,3,0,0,1,0,0,1},
            {1,0,2,9,2,0,3,1},
            {1,0,0,1,0,0,0,1},
            {1,1,1,1,1,1,1,1},
        };
    }

    public static int[][] getNivel3() {
        // Diseña tu nivel 3 aquí
        return new int[][] {
            {1,1,1,1,1,1,1},
            {1,0,0,3,0,0,1},
            {1,0,2,1,2,0,1},
            {1,0,9,3,0,0,1},
            {1,1,1,1,1,1,1},
        };
    }
    // Añade aquí los métodos para getNivel4(), getNivel5(), etc.
}