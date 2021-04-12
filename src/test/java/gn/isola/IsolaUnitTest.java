package gn.isola;

import static org.hamcrest.Matchers.is;
import org.hamcrest.collection.IsArray;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.DataPoints;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import gn.isola.game.Isola;
import gn.isola.game.IllegalMoveException;

@RunWith(Theories.class)
public class IsolaUnitTest {
    private Isola isola;
    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;

    @Before
    public void setUp() {
        isola = new Isola();
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    @Test
    public void shouldDrawGrid() {
        isola.toStream(printStream);

        assertThat(
                outputStream.toString().split("\n"),
                IsArray.array(
                        is("a1 a2 a3 -1 a5 a6 a7"),
                        is("b1 b2 b3 b4 b5 b6 b7"),
                        is("c1 c2 c3 c4 c5 c6 c7"),
                        is("d1 d2 d3 d4 d5 d6 d7"),
                        is("e1 e2 e3 e4 e5 e6 e7"),
                        is("f1 f2 f3 f4 f5 f6 f7"),
                        is("g1 g2 g3 -2 g5 g6 g7")
                )
        );
    }

    @Test
    public void shouldBeAbleToMakeAMove() throws IllegalMoveException {
        Isola isola = new Isola();
        isola = isola.move("a5");
        isola.toStream(printStream);

        assertThat(
                outputStream.toString().split("\n"),
                IsArray.array(
                        is("a1 a2 a3 [] -1 a6 a7"),
                        is("b1 b2 b3 b4 b5 b6 b7"),
                        is("c1 c2 c3 c4 c5 c6 c7"),
                        is("d1 d2 d3 d4 d5 d6 d7"),
                        is("e1 e2 e3 e4 e5 e6 e7"),
                        is("f1 f2 f3 f4 f5 f6 f7"),
                        is("g1 g2 g3 -2 g5 g6 g7")
                )
        );

    }

    @Test
    public void shouldBeAbleToRemoveASquare() throws IllegalMoveException {
        Isola isola = new Isola();
        isola = isola.move("a5").erase("a1");
        isola.toStream(printStream);

        assertThat(
                outputStream.toString().split("\n"),
                IsArray.array(
                        is("   a2 a3 [] -1 a6 a7"),
                        is("b1 b2 b3 b4 b5 b6 b7"),
                        is("c1 c2 c3 c4 c5 c6 c7"),
                        is("d1 d2 d3 d4 d5 d6 d7"),
                        is("e1 e2 e3 e4 e5 e6 e7"),
                        is("f1 f2 f3 f4 f5 f6 f7"),
                        is("g1 g2 g3 -2 g5 g6 g7")
                )
        );

    }

    @Test
    public void shouldChainTwoMovesTogether() throws IllegalMoveException {
        new Isola().move("a5").erase("a1").move("g3").toStream(printStream);
        assertThat(
                outputStream.toString().split("\n"),
                IsArray.array(
                        is("   a2 a3 [] -1 a6 a7"),
                        is("b1 b2 b3 b4 b5 b6 b7"),
                        is("c1 c2 c3 c4 c5 c6 c7"),
                        is("d1 d2 d3 d4 d5 d6 d7"),
                        is("e1 e2 e3 e4 e5 e6 e7"),
                        is("f1 f2 f3 f4 f5 f6 f7"),
                        is("g1 g2 -2 [] g5 g6 g7")
                )
        );
    }

    @Test
    public void shouldChainMovesAndRemovalOfSquares() throws IllegalMoveException {
        new Isola().move("a5").erase("a1").move("g3").erase("g7").toStream(printStream);
        assertThat(
                outputStream.toString().split("\n"),
                IsArray.array(
                        is("   a2 a3 [] -1 a6 a7"),
                        is("b1 b2 b3 b4 b5 b6 b7"),
                        is("c1 c2 c3 c4 c5 c6 c7"),
                        is("d1 d2 d3 d4 d5 d6 d7"),
                        is("e1 e2 e3 e4 e5 e6 e7"),
                        is("f1 f2 f3 f4 f5 f6 f7"),
                        is("g1 g2 -2 [] g5 g6   ")
                )
        );
    }

    @Test
    public void shouldLetPlayerOneChainMultipleMovesAndSquareRemovals() throws Exception {
        new Isola()
                .move("a5").erase("a1") //player 1
                .move("g3").erase("g7") //player 2
                .move("a6").erase("a7") //player 1
                .toStream(printStream);
        assertThat(
                outputStream.toString().split("\n"),
                IsArray.array(
                        is("   a2 a3 [] a5 -1   "),
                        is("b1 b2 b3 b4 b5 b6 b7"),
                        is("c1 c2 c3 c4 c5 c6 c7"),
                        is("d1 d2 d3 d4 d5 d6 d7"),
                        is("e1 e2 e3 e4 e5 e6 e7"),
                        is("f1 f2 f3 f4 f5 f6 f7"),
                        is("g1 g2 -2 [] g5 g6   ")
                )
        );
    }
    
    @Test(expected = IllegalMoveException.class)    
    public void shouldRejectPlayerMoveThatIsNonAdjacent() throws Exception {
        new Isola().move("c2");
    }

    @DataPoints
    public final static String[] failingMoves = {"c4", "g4", "g3", "g1"};

    @Theory
    @Test(expected = IllegalMoveException.class)
    public void shouldRejectInvalidMoves(String boardPosition) throws Exception {
        new Isola().move(boardPosition);
    }
}

