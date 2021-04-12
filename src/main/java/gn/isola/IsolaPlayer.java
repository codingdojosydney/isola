package gn.isola;

public class IsolaPlayer {
    public static final IsolaPlayer playerOne = new IsolaPlayer("-1");
    public static final IsolaPlayer playerTwo = new IsolaPlayer("-2");

    private final String playerRepresentation;

    public IsolaPlayer(String playerRepresentation) {
        this.playerRepresentation = playerRepresentation;
    }

    public String toBoardRepresentation() {
        return playerRepresentation;
    }

    public String toString() {
        return playerRepresentation;
    }

    public boolean equals(Object o) {
        return this == o;
    }

    public int hashCode() {
        int result;
        result = (playerRepresentation != null ? playerRepresentation.hashCode() : 0);
        return result;
    }

    public IsolaPlayer next() {
       return  this.equals(playerOne) ? playerTwo : playerOne;
    }

}
