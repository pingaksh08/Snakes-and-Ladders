import java.util.concurrent.ThreadLocalRandom;

public class Board {
    Cell[][] cells;

    Board(int boardSize, int noOfSnakes, int noOfLadders) {
        initializeCells(boardSize);
        addSnakeAndLadders(cells, noOfSnakes, noOfLadders);
    }

    private void initializeCells(int boardSize) {
        cells = new Cell[boardSize][boardSize];
        for(int i=0 ; i<boardSize ; i++) {
            for(int j=0 ; j<boardSize ; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    private void addSnakeAndLadders(Cell[][] cells, int noOfSnakes, int noOfLadders) {
        while(noOfSnakes > 0) {
            int snakeHead = ThreadLocalRandom.current().nextInt(1, cells.length * cells.length-1);
            int snakeTail = ThreadLocalRandom.current().nextInt(1, cells.length * cells.length-1);
            if(snakeTail >= snakeHead) continue;

            Jump snakeObj = new Jump();
            snakeObj.start = snakeHead;
            snakeObj.end = snakeTail;

            Cell cell = getCell(snakeHead);
            cell.jump = snakeObj;

            noOfSnakes--;
        }

        while(noOfLadders > 0) {
            int ladderStart = ThreadLocalRandom.current().nextInt(1, cells.length * cells.length-1);
            int ladderEnd = ThreadLocalRandom.current().nextInt(1, cells.length * cells.length-1);
            if(ladderStart >= ladderEnd) continue;

            Jump ladderObj = new Jump();
            ladderObj.start = ladderStart;
            ladderObj.end = ladderEnd;

            Cell cell = getCell(ladderStart);
            cell.jump = ladderObj;

            noOfLadders--;
        }
    }

    public Cell getCell(int playerPosition) {
        int row = playerPosition / cells.length;
        int col =  playerPosition % cells.length;
        return cells[row][col];
    }
}
