public class Board {
    private int[][] tiles;

    public Board(int[][] tiles) {
        this.tiles = tiles;
    }

    public String toString() {
        StringBuilder s = new StringBuilder(new String(String.valueOf(this.dimension()) + "\n"));
        for (int[] row : tiles) {
            for (int col : row) {
                s.append(" ");
                s.append(col);
            }
            s.append("\n");
        }

        return s.toString();
    }

    public int dimension() {
        return tiles.length;
    }

    public int hamming() {
        int ret = 0;
        for (int i = 0; i < this.dimension(); i++)
            for (int j = 0; j < this.dimension(); j++)
                if (tiles[i][j] != 0 && tiles[i][j] != i * this.dimension() + (j + 1))
                    ret += 1;
        return ret;
    }

    public int manhattan() {
        int ret = 0;
        for (int i = 0; i < this.dimension(); i++)
            for (int j = 0; j < this.dimension(); j++)
                if (tiles[i][j] != 0 && tiles[i][j] != i * this.dimension() + (j + 1)) {
                    int realI = tiles[i][j] / this.dimension();
                    int realJ = tiles[i][j] % this.dimension() - 1;
                    ret += Math.abs(realI - i) + Math.abs(realJ - j);
                }
        return ret;
    }

    public static void main(String[] args) {
        int[][] tiles = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5},
        };
        Board board = new Board(tiles);
        System.out.println(board);
        System.out.println(board.manhattan());
    }
}
