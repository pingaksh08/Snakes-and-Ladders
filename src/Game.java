import java.awt.*;
import java.util.Deque;
import java.util.LinkedList;

public class Game {
    Board board;
    Dice dice;
    Deque<Player> players = new LinkedList<Player>();
    Player winner;

    public Game() {
        initializeGame();
    }

    private void initializeGame() {
        board = new Board(10, 5,4);
        dice = new Dice(1);
        winner = null;
        addPlayers();
    }

    private void addPlayers() {
        Player player1 = new Player("P1", 0);
        Player player2 = new Player("P2", 0);
        players.add(player1);
        players.add(player2);
    }

    public void startGame() {

        while(winner == null) {

            // check which player's turn is there
            Player playerTurn = findPlayerTurn();
            System.out.println("player turn is :" + playerTurn.playerId + ". Current position is :" + playerTurn.currPosition);

            // roll dice
            int diceNumbers = dice.rollDice();

            // get new player position
            int newPlayerPosition = playerTurn.currPosition + diceNumbers;
            newPlayerPosition = jumpCheck(newPlayerPosition);
            playerTurn.currPosition = newPlayerPosition;
            System.out.println("Player : " +playerTurn.playerId+ "turn ended. New position is : " +playerTurn.currPosition);

            //checking winning condition
            if(newPlayerPosition >= board.cells.length * board.cells.length-1){
                winner = playerTurn;
            }
        }

        System.out.println("Winner is : " +winner.playerId);

    }

    private Player findPlayerTurn() {
        Player playerTurn = players.removeFirst();
        players.addLast(playerTurn);
        return playerTurn;
    }

    private int jumpCheck(int position) {
        if(position > board.cells.length * board.cells.length-1) return position;

        Cell cell = board.getCell(position);
        if(cell.jump != null && cell.jump.start == position) {
            String jumpBy = (cell.jump.start > cell.jump.end) ? "Snake" : "Ladder";
            System.out.println("Jump by " + jumpBy);
            return cell.jump.end;
        }

        return position;
    }
}
