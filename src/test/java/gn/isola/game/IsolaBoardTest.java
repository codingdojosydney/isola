package gn.isola.game;

import junit.framework.TestCase;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import gn.isola.IsolaPlayer;
import static gn.isola.IsolaPlayer.*;

public class IsolaBoardTest {
    @Test
    public void shouldPrintStartingPositions() {
        IsolaBoard isolaBoard = new IsolaBoard(playerOne.toBoardRepresentation(), playerTwo.toBoardRepresentation());
        OutputStream outputStream = new ByteArrayOutputStream();
        isolaBoard.print(new PrintStream(outputStream));
        assertThat(outputStream.toString(), equalTo(
                "a1 a2 a3 -1 a5 a6 a7\n"
                        + "b1 b2 b3 b4 b5 b6 b7\n"
                        + "c1 c2 c3 c4 c5 c6 c7\n"
                        + "d1 d2 d3 d4 d5 d6 d7\n"
                        + "e1 e2 e3 e4 e5 e6 e7\n"
                        + "f1 f2 f3 f4 f5 f6 f7\n"
                        + "g1 g2 g3 -2 g5 g6 g7"
        ));
    }

    @Test
    public void shouldPrintHomePositionsAsSquareBracketsWhenPlayersAreNotOnThem() {
        final String playerOneRepresentation = playerOne.toBoardRepresentation();
        IsolaBoard isolaBoard = new IsolaBoard(playerOneRepresentation, playerTwo.toBoardRepresentation());
        isolaBoard = isolaBoard.liftPiece("a4", playerOneRepresentation);
        isolaBoard = isolaBoard.placePiece("a5", playerOneRepresentation);
        OutputStream outputStream = new ByteArrayOutputStream();
        isolaBoard.print(new PrintStream(outputStream));
        assertThat(outputStream.toString(), equalTo(
                "a1 a2 a3 [] -1 a6 a7\n"
                        + "b1 b2 b3 b4 b5 b6 b7\n"
                        + "c1 c2 c3 c4 c5 c6 c7\n"
                        + "d1 d2 d3 d4 d5 d6 d7\n"
                        + "e1 e2 e3 e4 e5 e6 e7\n"
                        + "f1 f2 f3 f4 f5 f6 f7\n"
                        + "g1 g2 g3 -2 g5 g6 g7"
        ));
    }
}