/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Snake;

/**
 *
 * @author LENOVO
 */
import java.util.*;

public class Snake {
    private Direction direction;
    private Point head;
    private ArrayList<Point> tail;
    
    public Snake(int x, int y) {
        this.head = new Point(x, y);
        this.direction = Direction.LEFT;
        this.tail = new ArrayList<Point>();
        
        this.tail.add(new Point(0, 0));
        this.tail.add(new Point(0, 0));
        this.tail.add(new Point(0, 0));
    }

    public void move() {
        ArrayList newTail = new ArrayList<Point>();
        
        for (int i = 0, size = tail.size(); i < size; i++) {
            Point previous = i == 0 ? head : tail.get(i - 1);

            newTail.add(new Point(previous.getX(), previous.getY()));
        }
        
        this.tail = newTail;
        
        this.head.move(this.direction, 10);
    }
    
    public void addTail() {
        
        this.tail.add(new Point(-10, -10));
    }
    
    public void turn(Direction d) {       
        if (d.isX() && direction.isY() || d.isY() && direction.isX()) {
           direction = d; 
        }       
    }
    
    public ArrayList<Point> getTail() {
        return this.tail;
    }
    
    public Point getHead() {
        return this.head;
    }
}

