public class Board {
    private int[][] tiles;
    public Board(int[][] tiles) {
        this.tiles = tiles;
    }
    public String toString() {
        int n = tiles.length;
        StringBuilder s = new StringBuilder(new String(String.valueOf(n) + "\n"));
        for (int[] row : tiles) {
            for (int col : row) {
                s.append(col);
                s.append(" ");
            }
            s.deleteCharAt(s.lastIndexOf(" "));
            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        int[][] tiles = {
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3},
        };
        Board board = new Board(tiles);
        System.out.println(board);
    }
}
