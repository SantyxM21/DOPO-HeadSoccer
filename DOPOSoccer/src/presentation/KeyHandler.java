package presentation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    //izquierdo
    public boolean leftPressed, rightPressed, jumpPressed, kickPressed, powerPressed;

    //derecho
    public boolean leftPressedR, rightPressedR, jumpPressedR, kickPressedR, powerPressedR;

    // y kickHeld evita patear con la tecla presionada
    private boolean kickHeld, kickHeldR;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

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
            if (!kickHeld) kickPressed = true;
            kickHeld = true;
        }
        if(code == KeyEvent.VK_M){
            if (!kickHeldR) kickPressedR = true;
            kickHeldR = true;
        }
        if(code == KeyEvent.VK_E){
            powerPressed = true;
        }
        if(code == KeyEvent.VK_N){
            powerPressedR = true;
        }

    }

    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

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
            kickHeld = false;
        }
        if(code == KeyEvent.VK_M){
            kickHeldR = false;
        }
        if(code == KeyEvent.VK_E){
            powerPressed = false;
        }
        if(code == KeyEvent.VK_N){
            powerPressedR = false;
        }
    }
}
