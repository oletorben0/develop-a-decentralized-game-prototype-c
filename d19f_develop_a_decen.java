import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

interface GameAction {
    void performAction();
}

class Player {
    private String name;
    private Map<String, GameAction> actions;

    public Player(String name) {
        this.name = name;
        this.actions = new HashMap<>();
    }

    public void addAction(String actionName, GameAction action) {
        actions.put(actionName, action);
    }

    public void performAction(String actionName) {
        GameAction action = actions.get(actionName);
        if (action != null) {
            action.performAction();
        }
    }
}

class DecentralizedGame {
    private Map<String, Player> players;
    private Map<String, GameAction> globalActions;

    public DecentralizedGame() {
        this.players = new HashMap<>();
        this.globalActions = new HashMap<>();
    }

    public void addPlayer(String playerName) {
        Player player = new Player(playerName);
        players.put(playerName, player);
    }

    public void addGlobalAction(String actionName, GameAction action) {
        globalActions.put(actionName, action);
    }

    public void performAction(String playerName, String actionName) {
        Player player = players.get(playerName);
        if (player != null) {
            player.performAction(actionName);
        }
    }

    public void broadcastAction(String actionName) {
        GameAction action = globalActions.get(actionName);
        if (action != null) {
            action.performAction();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        DecentralizedGame game = new DecentralizedGame();

        game.addPlayer("player1");
        game.addPlayer("player2");

        game.addGlobalAction("attack", new GameAction() {
            @Override
            public void performAction() {
                System.out.println("Global attack action performed");
            }
        });

        GameAction player1Action = new GameAction() {
            @Override
            public void performAction() {
                System.out.println("Player 1 special action performed");
            }
        };

        GameAction player2Action = new GameAction() {
            @Override
            public void performAction() {
                System.out.println("Player 2 special action performed");
            }
        };

        game.players.get("player1").addAction("special", player1Action);
        game.players.get("player2").addAction("special", player2Action);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter player name or 'global' for global action: ");
            String input = scanner.nextLine();
            if (input.equals("global")) {
                game.broadcastAction("attack");
            } else {
                System.out.print("Enter action name: ");
                String actionName = scanner.nextLine();
                game.performAction(input, actionName);
            }
        }
    }
}