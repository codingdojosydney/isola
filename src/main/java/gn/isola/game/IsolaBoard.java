package gn.isola.game;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

class IsolaBoard {

    private final String board;
    private final List<String> coveredSquares;

    public IsolaBoard(String playerOneRepresentation, String playerTwoRepresentation) {
        this(Collections.unmodifiableList(Arrays.asList("a4","g4")),
                String.format("a1 a2 a3 %s a5 a6 a7\n"
        +"b1 b2 b3 b4 b5 b6 b7\n"
        +"c1 c2 c3 c4 c5 c6 c7\n"
        +"d1 d2 d3 d4 d5 d6 d7\n"
        +"e1 e2 e3 e4 e5 e6 e7\n"
        +"f1 f2 f3 f4 f5 f6 f7\n"
        +"g1 g2 g3 %s g5 g6 g7", playerOneRepresentation, playerTwoRepresentation));
    }

    private IsolaBoard(List<String> coveredSquares, String board) {
        this.board = board;
        this.coveredSquares = Collections.unmodifiableList(coveredSquares);
    }

    private String setUpHomePositionsOn(String board) {
        return board.replaceAll("a4", "[]").replaceAll("g4", "[]");
    }

    public void print(PrintStream stream) {
        stream.print(setUpHomePositionsOn(board));
    }

    public IsolaBoard placePiece(String boardPosition, String playerRepresentation) {
        final String pieceHasBeenPlaced = board.replaceAll(boardPosition, playerRepresentation);
        return new IsolaBoard(coveredSquares, pieceHasBeenPlaced);
    }

    public IsolaBoard liftPiece(String boardPosition, String playerRepresentation) {
        List<String> newQueue = Arrays.asList(coveredSquares.get(1), boardPosition);
        final String squareBeingUncovered = coveredSquares.get(0);
        final String pieceHasBeenLifted = board.replaceAll(playerRepresentation, squareBeingUncovered);
        return new IsolaBoard(newQueue, pieceHasBeenLifted);
    }

    public IsolaBoard erase(String boardPosition) {
        return new IsolaBoard(coveredSquares, board.replaceAll(boardPosition, "  "));
    }

    public boolean isThisAnIllegalMove(String destinationPosition) {
        String currentPlayerPosition = coveredSquares.get(0);

        int numericCurrentPosition = Integer.valueOf(currentPlayerPosition.substring(1,2));
        int numericDestinationPosition = Integer.valueOf(destinationPosition.substring(1,2));

        byte asciiDestinationPosition;
        byte asciiCurrentPosition;
        try {
            asciiDestinationPosition = destinationPosition.substring(0, 1).getBytes("US-ASCII")[0];
            asciiCurrentPosition = currentPlayerPosition.substring(0, 1).getBytes("US-ASCII")[0];
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("This exception is probably Halvard's fault",e);
        }

        final boolean verticalFailure = Math.abs(asciiCurrentPosition - asciiDestinationPosition) > 1;
        final boolean horizontalFailure = Math.abs(numericCurrentPosition - numericDestinationPosition) > 1;

        return verticalFailure || horizontalFailure;
    }

}
