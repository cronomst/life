package com.wordsthatfollow.life.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author kshook
 */
public class RenderPane extends JPanel implements Runnable
{

    private boolean running;
    private long lastTick = 0;

    public void handleInput(long elapsedTime)
    {

    }

    public void update(long elapsedTime)
    {

    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.render((Graphics2D)g);
    }

    @Override
    public void run()
    {
        running = true;
        this.setLastTick(System.currentTimeMillis());
        long elapsedTime;
        while (running) {
            synchronized (this) {
                elapsedTime = System.currentTimeMillis() - getLastTick();
                setLastTick(System.currentTimeMillis());
                handleInput(elapsedTime);
                update(elapsedTime);
            }
            repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {}

        }
    }

    private long getLastTick()
    {
        return lastTick;
    }

    private void setLastTick(long lastTick)
    {
        this.lastTick = lastTick;
    }

    public void render(Graphics2D g)
    {
    }

    public void start()
    {
        Thread t = new Thread(this);
        t.start();
    }

    public void stop()
    {
        running = false;
    }

}
