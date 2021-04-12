package gn.isola.game;

import static gn.isola.IsolaPlayer.*;
import gn.isola.IsolaPlayer;
import gn.isola.game.IllegalMoveException;

import java.io.PrintStream;

public class Isola {

    private final IsolaBoard isolaBoard;
    private final IsolaPlayer currentPlayer;

    public Isola() {
        currentPlayer = playerOne;
        this.isolaBoard = new IsolaBoard(playerOne.toBoardRepresentation(), playerTwo.toBoardRepresentation());
    }

    private Isola(IsolaBoard isolaBoard, IsolaPlayer nextPlayer) {
        this.currentPlayer = nextPlayer;
        this.isolaBoard = isolaBoard;
    }

    public void toStream(PrintStream stream) {
        isolaBoard.print(stream);
    }

    public Isola move(String boardPosition) throws IllegalMoveException {
        if(isolaBoard.isThisAnIllegalMove(boardPosition)) {
            throw new IllegalMoveException();
        }

        final String playerRepresentation = currentPlayer.toBoardRepresentation();

        final IsolaBoard boardWithoutPiece = isolaBoard.liftPiece(boardPosition, playerRepresentation);
        final IsolaBoard board = boardWithoutPiece.placePiece(boardPosition, playerRepresentation);

        return new Isola(board, currentPlayer);
    }

    public Isola erase(String boardPosition) {
        final IsolaBoard board = isolaBoard.erase(boardPosition);
        return new Isola(board, currentPlayer.next());
    }

}
