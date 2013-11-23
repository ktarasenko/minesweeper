package com.ktarasenko.minesweeper.tests;

import com.ktarasenko.minesweeper.model.GameTable;
import com.ktarasenko.minesweeper.model.TableGenerator;
import com.ktarasenko.minesweeper.util.Point;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashSet;

import static org.mockito.Mockito.*;

public class ModelTest extends TestCase {

    public void testTableGenerator(){
        for (int i = 0; i < 100; i++){
           TableGenerator generator = new TableGenerator();
           HashSet<Point> set = new HashSet<Point>();
           for (int j = 0; j < generator.getMinesCount(); j++){
               set.add(generator.next());
           }
           assertEquals("Generator should plant distinct mines on every call", generator.getMinesCount(),set.size());
        }
    }

    public void testGeneratorGetNeighbours(){
        TableGenerator generator = new TableGenerator();
        ArrayList<Point> points = generator.getNeighbors(0,0);
        assertTrue("There should be 3 neighbours for 0,0", points.size() == 3);
        assertTrue("There should  neighbours for 0,0: 1,0 1,1 0,1", points.contains(new Point(0,1)) && points.contains(new Point(1,1)) && points.contains(new Point(1,0)));
        points = generator.getNeighbors(2,2);
        assertTrue("There should be 8 neighbours for 0,0", points.size() == 8);
        points = generator.getNeighbors(generator.getWidth() -1, generator.getHeight() -1);
        assertTrue("There should be 3 neighbours for right corner point", points.size() == 3);

        points = generator.getNeighbors(1, generator.getHeight() -1);
        assertTrue("There should be 5 neighbours for that point", points.size() == 5);

    }


    public void testGameTableInitDimensionsMin(){
        try {
         new GameTable(-1, -1, 8);
         fail();
        } catch (Throwable t){
            assertEquals("Should throw IllegalArgumentException on dimensions <= 0",
                    IllegalArgumentException.class, t.getClass());
        }
    }

    public void testGameTableInitDimensionsMax(){
        try {
            new GameTable(GameTable.MAX_SIZE + 1, GameTable.MAX_SIZE + 1, 8);
            fail();
        } catch (Throwable t){
            assertEquals("Should throw IllegalArgumentException on dimensions > MAX_SIZE",
                    IllegalArgumentException.class, t.getClass());
        }
    }

    public void testGameTableInitWrongMinesMin(){
        try {
            new GameTable(5, 5, -1);
            fail();
        } catch (Throwable t){
            assertEquals("Should throw IllegalArgumentException if there's no mines ",
                    IllegalArgumentException.class, t.getClass());
        }
    }

    public void testGameTableInitWrongMinesMax(){
        try {
            new GameTable(5, 5, 26);
            fail();
        } catch (Throwable t){
            assertEquals("Should throw IllegalArgumentException if there's more mines than cells available ",
                     IllegalArgumentException.class, t.getClass());
        }
    }

    public void testOneMine(){
        TableGenerator tableGenerator = createTableGeneratorMock();
        when(tableGenerator.next()).thenReturn(new Point(0, 0));
        GameTable table = new GameTable(tableGenerator);
        assertTrue("clicking on a field without a mine should return true",table.open(2,2));
        assertTrue("puzzle should be open then ", table.check());
        assertFalse("clicking on a field with a mine should return false", table.open(0, 0));
    }

    public void testFullOfMines(){
        TableGenerator tableGenerator = new TableGenerator(2, 2, 4);
        GameTable table = new GameTable(tableGenerator);
        assertFalse("clicking on any field should trigger a mine", table.open(1, 1));
    }

    public void testCheats(){
        TableGenerator tableGenerator = createTableGeneratorMock();
        when(tableGenerator.next()).thenReturn(new Point(0, 0));
        GameTable table = new GameTable(tableGenerator);
        table.cheat();
        assertTrue("cheat command should reveal mines", table.get(0,0) == GameTable.State.CHEATING_MINE);
    }

    public void testStatesInitial(){
        TableGenerator tableGenerator = new TableGenerator(2, 2, 4);
        GameTable table = new GameTable(tableGenerator);
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 2; j++){
                assertTrue("table should be closed at the beginning", table.get(i, j) == GameTable.State.CLOSED);
            }
        }
    }

    public void testStatesOneOpen(){
        TableGenerator tableGenerator = createTableGeneratorMock();
        when(tableGenerator.next()).thenReturn(new Point(0, 0));
        GameTable table = new GameTable(tableGenerator);
        table.open(2, 2);
        assertTrue("expecting EMPTY", table.get(2, 2) == GameTable.State.EMPTY);
        assertTrue("expecting ONE", table.get(1, 1) == GameTable.State.ONE);
        assertTrue("expecting ONE", table.get(1, 0) == GameTable.State.ONE);
        assertTrue("expecting ONE", table.get(0, 1) == GameTable.State.ONE);
        assertTrue("expecting CLOSED", table.get(0, 0) == GameTable.State.CLOSED);
    }

    public void testStatesOpen2(){
        TableGenerator tableGenerator = createTableGeneratorMock();
        when(tableGenerator.next()).thenReturn(new Point(0, 0), new Point(1,1));
        GameTable table = new GameTable(tableGenerator);
        table.open(2, 2);
        assertTrue("expecting ONE", table.get(2, 2) == GameTable.State.ONE);
        table.open(0, 1);
        assertTrue("expecting TWO", table.get(0, 1) == GameTable.State.TWO);
        table.open(1, 0);
        assertTrue("expecting TWO", table.get(1, 0) == GameTable.State.TWO);
        assertTrue("expecting CLOSED", table.get(0, 0) == GameTable.State.CLOSED);
        assertTrue("expecting CLOSED", table.get(1, 1) == GameTable.State.CLOSED);
    }


    private TableGenerator createTableGeneratorMock() {
        TableGenerator tableGenerator = mock(TableGenerator.class);
        when(tableGenerator.getNeighbors(anyInt(), anyInt())).thenCallRealMethod();
        when(tableGenerator.getHeight()).thenReturn(GameTable.DEFAULT_HEIGHT);
        when(tableGenerator.getWidth()).thenReturn(GameTable.DEFAULT_WIDTH);
        when(tableGenerator.getMinesCount()).thenReturn(GameTable.DEFAULT_MINES);
        return  tableGenerator;
    }

}
