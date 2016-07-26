package com.wordsthatfollow.life;

import com.wordsthatfollow.life.ui.RenderPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 *
 * @author kshook
 */
public class Life extends RenderPane
{
    private long elapsed = 0;
    /**
     * Time in ms between generations
     */
    private final int GENERATION_TIME = 100;
    /**
     * Cell width/height in px
     */
    private final int CELL_SIZE = 2;
    /**
     * Field width in cells
     */
    private final int FIELD_WIDTH = 100;
    /**
     * Field height in cells
     */
    private final int FIELD_HEIGHT = 100;
    
    private final int DEAD = 0;
    private final int ALIVE = 1;
    
    /**
     * Current state of cells
     */
    private int[][] cells;
    /**
     * Next state of cells
     */
    private int[][] cellsNextState;
    
    public Life()
    {
        cells = new int[FIELD_HEIGHT][FIELD_WIDTH];
        cellsNextState = new int[FIELD_HEIGHT][FIELD_WIDTH];

//        createGlider(0,0);
        createRandomCells(3500);
        
        this.setPreferredSize(new Dimension(FIELD_WIDTH*CELL_SIZE, FIELD_HEIGHT*CELL_SIZE));
    }
    
    private void createGlider(int x, int y)
    {
        setCell(x+1, y+1, ALIVE);
        setCell(x+2, y+2, ALIVE);
        setCell(x+2, y+3, ALIVE);
        setCell(x+1, y+3, ALIVE);
        setCell(x+0, y+3, ALIVE);
    }
    
    private void createRandomCells(int count)
    {
        for (int i=0; i<count; i++) {
            int x = (int)(Math.random()*FIELD_WIDTH);
            int y = (int)(Math.random()*FIELD_HEIGHT);
            setCell(x, y, ALIVE);
        }
    }
    
    @Override
    public void update(long elapsedTime)
    {
        elapsed += elapsedTime;
        if (elapsed >= GENERATION_TIME) {
            elapsed -= GENERATION_TIME;
            runGeneration();
            updateCurrentState();
        }
    }
    
    @Override
    public void render(Graphics2D g)
    {
        for (int x=0; x<FIELD_WIDTH; x++) {
            for (int y=0; y<FIELD_HEIGHT; y++) {
                if (getCell(x, y) == ALIVE) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x*CELL_SIZE, y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
    
    private void runGeneration()
    {
        for (int x=0; x<FIELD_WIDTH; x++) {
            for (int y=0; y<FIELD_WIDTH; y++) {
                int count = getNeighborCount(x,y);
                int current = getCell(x, y);
                
                if (current == ALIVE) {
                    if (count < 2) {
                        cellsNextState[y][x] = DEAD;
                    } else if (count == 2 || count == 3) {
                        cellsNextState[y][x] = ALIVE;
                    } else if (count > 3) {
                        cellsNextState[y][x] = DEAD;
                    }
                } else if (current == DEAD) {
                    if (count == 3) { 
                        cellsNextState[y][x] = ALIVE;
                    }
                }
            }
        }
    }
    
    private void updateCurrentState()
    {
        for (int x=0; x<FIELD_WIDTH; x++) {
            for (int y=0; y<FIELD_HEIGHT; y++) {
                setCell(x, y, cellsNextState[y][x]);
            }
        }
    }
    
    private int getCell(int x, int y)
    {
        x = normalizeRange(x, FIELD_WIDTH);
        y = normalizeRange(y, FIELD_HEIGHT);
        
        return cells[y][x];
    }
    
    private void setCell(int x, int y, int state)
    {
        x = normalizeRange(x, FIELD_WIDTH);
        y = normalizeRange(y, FIELD_HEIGHT);
        cells[y][x] = state;
    }
    
    private int normalizeRange(int value, int max)
    {
        if (value < 0) {
            return value % max + max;
        }
        return value % max;
    }
    
    private int getNeighborCount(int x, int y)
    {
        int count = 0;
        for (int dx=-1; dx<2; dx++) {
            for (int dy=-1; dy<2; dy++) {
                if ((dx != 0 || dy != 0) && (getCell(x+dx, y+dy) == ALIVE)) {
                    count++;
                }
            }
        }
        return count;
    }
}
