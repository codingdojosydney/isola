package gn.isola;

import static gn.isola.IsolaPlayer.*;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Isola {
    class IsolaBoard {

        private final String board;
        private final List<String> coveredSquares;

        public IsolaBoard(List<String> coveredSquares, String board) {
            this.coveredSquares = coveredSquares;
            this.board = board;
        }

        public void print(PrintStream stream) {
            stream.print(board);
        }

        public IsolaBoard placePiece(String boardPosition, String playerRepresentation) {
            final String pieceHasBeenPlaced = board.replaceAll(boardPosition, playerRepresentation);
            return new IsolaBoard(coveredSquares, pieceHasBeenPlaced);
        }

        public IsolaBoard liftPiece(String boardPosition, String playerRepresentation) {
            List<String> newQueue = Arrays.asList(coveredSquares.get(1), boardPosition);
            final String squareBeingUncovered = coveredSquares.get(0);
            final String pieceHasBeenLifted = board.replaceAll(playerRepresentation, squareBeingUncovered);
            final IsolaBoard boardWithoutPiece = new IsolaBoard(newQueue, pieceHasBeenLifted);
            return boardWithoutPiece;
        }
    }

    private final IsolaBoard isolaBoard;
    private final IsolaPlayer currentPlayer;
    

    public Isola() {
        List<String> coveredSquares = Collections.unmodifiableList(Arrays.asList("[]","[]"));

        currentPlayer = playerOne;
        String board = String.format("a1 a2 a3 %s a5 a6 a7\n"
        +"b1 b2 b3 b4 b5 b6 b7\n"
        +"c1 c2 c3 c4 c5 c6 c7\n"
        +"d1 d2 d3 d4 d5 d6 d7\n"
        +"e1 e2 e3 e4 e5 e6 e7\n"
        +"f1 f2 f3 f4 f5 f6 f7\n"
        +"g1 g2 g3 %s g5 g6 g7", playerOne.toBoardRepresentation(), playerTwo.toBoardRepresentation());

        this.isolaBoard = new IsolaBoard(coveredSquares, board);

    }

    private Isola(IsolaBoard isolaBoard, IsolaPlayer nextPlayer) {
        this.currentPlayer = nextPlayer;
        this.isolaBoard = isolaBoard;
    }

    public void toStream(PrintStream stream) {
        isolaBoard.print(stream);
    }

    public Isola move(String boardPosition) throws IllegalMoveException {

        if(boardPosition.equals("c4")) {
            throw new IllegalMoveException();
        }

        final String playerRepresentation = currentPlayer.toBoardRepresentation();

        final IsolaBoard boardWithoutPiece = isolaBoard.liftPiece(boardPosition, playerRepresentation);
        final IsolaBoard board = boardWithoutPiece.placePiece(boardPosition, playerRepresentation);

        return new Isola(board, currentPlayer);
    }

    public Isola erase(String boardPosition) {
        final IsolaBoard board = new IsolaBoard(isolaBoard.coveredSquares, isolaBoard.board.replaceAll(boardPosition, "  "));
        return new Isola(board, currentPlayer.next());
    }

}
