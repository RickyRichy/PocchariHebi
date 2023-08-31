/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Snake;

/**
 *
 * @author LENOVO
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;
    
    public boolean isX() {
        return this == LEFT || this == RIGHT;
    }
    
    public boolean isY() {
        return this == UP || this == DOWN;
    }
}
