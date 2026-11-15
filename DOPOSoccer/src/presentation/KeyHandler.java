package presentation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    //izquierdo
    public boolean upPressed, downPressed, leftPressed, rightPressed, jumpPressed, kickPressed;

    //derecho
    public boolean upPressedR, downPressedR, leftPressedR, rightPressedR, jumpPressedR, kickPressedR;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_SPACE) {
            jumpPressed = true;
        }
        if(code == KeyEvent.VK_LEFT) {
            leftPressedR = true;
        }
        if(code == KeyEvent.VK_RIGHT) {
            rightPressedR = true;
        }
        if(code == KeyEvent.VK_UP) {
            jumpPressedR = true;
        }
        if(code == KeyEvent.VK_Q){
            kickPressed = true;
        }
        if(code == KeyEvent.VK_M){
            kickPressedR = true;
        }

    }

    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SPACE) {
            jumpPressed = false;
        }
        if(code == KeyEvent.VK_LEFT) {
            leftPressedR = false;
        }
        if(code == KeyEvent.VK_RIGHT) {
            rightPressedR = false;
        }
        if(code == KeyEvent.VK_UP) {
            jumpPressedR = false;
        }
        if(code == KeyEvent.VK_Q){
            kickPressed = false;
        }
        if(code == KeyEvent.VK_M){
            kickPressedR = false;
        }
    }
}
