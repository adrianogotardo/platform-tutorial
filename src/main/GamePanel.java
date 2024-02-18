package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

public class GamePanel extends JPanel { // *
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false;
    
    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        
        importImg();
        loadAnimations();
        
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }
    
    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");
        
        try { img = ImageIO.read(is); }
        catch (IOException e) { e.printStackTrace(); }
        finally { 
            try { is.close(); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];
        
        for(int j = 0; j < animations.length; j++) {
            for(int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // **
        
        updateAnimationTick();
        
        setAnimation();
        updatePosition();
        
        g.drawImage(animations[playerAction][animationIndex], (int) xDelta, (int) yDelta, 128, 80, null);
    }
    
    private void updateAnimationTick() {
        animationTick++;
        if(animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
            }
        }
    }
    
    public void setDirection(int direction) {
        this.playerDirection = direction;
        moving = true;
    }
    
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void setAnimation() {
        if(moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    private void updatePosition() {
        if(moving) {
            switch(playerDirection) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }
}


//<editor-fold defaultstate="collapsed" desc="comment">
/*

* = extends JPanel significa dizer que JPanel passa a ser uma super-classe de GamePanel, de
forma que GamePanel passa a ter acesso a todos os métodos de JPanel também.

** = basicamente chama o método paintComponent na super-classe (JPanel) e diz: "faz tudo o que vc
precisar fazer por aí com esse método que aí eu vou fazer tudo o que eu quiser com ele por aqui".

*/
//</editor-fold>
