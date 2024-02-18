package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel { // *
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDir = 1f, yDir = 1f;
    private Color color = new Color(150, 20, 90);
    private Random random;
    
    // Temporary multiple rectangles effect
    private ArrayList<MyRectangle> rectangles = new ArrayList<>();
    // ------------------------------------
    
    public GamePanel() {
        random = new Random();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
    
    public void changeXDelta(int value) {
        this.xDelta += value;
    }
    
    public void changeYDelta(int value) {
        this.yDelta += value;
    }
    
    // Temporary multiple rectangles effect
    public void spawnRectangle(int x, int y) {
        rectangles.add(new MyRectangle(x, y));
    }
    // ------------------------------------
    
    public void setRectPosition(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // **
        
        // Temporary multiple rectangles effect
        for(MyRectangle rectangle : rectangles) {
            rectangle.updateRectangle();
            rectangle.draw(g);
        }
        // ------------------------------------
        
        updateRectangle();
        g.setColor(color);
        g.fillRect((int) xDelta, (int) yDelta, 200, 50);
    }

    private void updateRectangle() {
        xDelta += xDir;
        if(xDelta > 400 || xDelta < 0) {
            xDir *= -1;
            color = getRandomColor();
        }
        
        yDelta += yDir;
        if(yDelta > 400 || yDelta < 0) {
            yDir *= -1;
            color = getRandomColor();
        }
    }

    private Color getRandomColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        
        return new Color(r, g, b);
    }
    
    // Temporary multiple rectangles effect
    public class MyRectangle {
        int x, y, w, h;
        int xDir = 1, yDir = 1;
        Color color;
        
        public MyRectangle(int x, int y) {
            this.x = x;
            this.y = y;
            w = random.nextInt(50);
            h = w;
            color = newColor();
        }
        
        public void updateRectangle() {
            this.x += xDir;
            this.y += yDir;
            
            if((x + w) > 400 || x < 0) {
                xDir *= -1;
                color = newColor();
            }
            if((y + h) > 400 || y < 0) {
                yDir *= -1;
                color = newColor();
            }
        }

        private Color newColor() {
           return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        }
        
        public void draw(Graphics g) {
            g.setColor(color);
            g.fillRect(x, y, w, h);
        }
    }
    // ------------------------------------
}


//<editor-fold defaultstate="collapsed" desc="comment">
/*

* = extends JPanel significa dizer que JPanel passa a ser uma super-classe de GamePanel, de
forma que GamePanel passa a ter acesso a todos os métodos de JPanel também.

** = basicamente chama o método paintComponent na super-classe (JPanel) e diz: "faz tudo o que vc
precisar fazer por aí com esse método que aí eu vou fazer tudo o que eu quiser com ele por aqui".

*/
//</editor-fold>
