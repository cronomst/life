package com.wordsthatfollow.life;

import com.wordsthatfollow.life.ui.RenderPane;
import javax.swing.JFrame;

/**
 *
 * @author kshook
 */
public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RenderPane panel = new Life();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        panel.start();
    }
}
