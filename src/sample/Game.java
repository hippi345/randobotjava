package sample;

import sample.models.Bot;
import sample.models.MoveEnum;
import sample.models.Treasure;

// class for the game components
class Game
{

    private int turnCount = 0;

    Bot bot;
    Treasure treasure;


    Game(int gridSizePassed)
    {
        this.bot = new Bot(gridSizePassed);
        this.treasure = new Treasure();
    }

    void InitializeGame(View gameView)
    {
        this.treasure.RandomizeLocation(gameView.gridSize);

        this.bot.RandomizeLocation(gameView.gridSize);

        while(this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY())
        {
            this.bot.RandomizeLocation(gameView.gridSize);
        }

        gameView.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }

    void InitializeGame()
    {
        View view = View.getInstance();
        this.treasure.RandomizeLocation(view.gridSize);

        this.bot.RandomizeLocation(view.gridSize);

        while(this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY())
            {
                this.bot.RandomizeLocation(view.gridSize);
            }

        view.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }

    void executeAutoMove() {
        // make next move
        ++turnCount;
        System.out.println("Current turn: " + turnCount);
        // Bot threadBot = this.bot;

        // Joel comment 12/9 - I don't know threads well but I tried but it was not working.
        // Did not break anything though

        /*Thread botThread = new moveBotThread(this.bot);
        botThread.start();

        Thread guiThread = new GUIThread(this.bot, this.treasure, view);
        guiThread.start();*/

        /*Thread movementThread = new Thread(moveTask);
        movementThread.setDaemon(true);
        movementThread.start();

        moveTask.setOnSucceeded(e ->
        {
            view.getInstance().adjustBotAndTreasureLocations(this.bot, this.treasure);
        });*/

        this.bot.MoveRandomly();
            if(treasureIsFound())
                completeGame();

        Main.gameView.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }

    // actions on completion of the game
    private static void completeGame()
    {
        System.out.println("you found the treasure!");
        System.exit(69);
    }

    // condition checking for whether the bot is on the treasure location
    private boolean treasureIsFound()
    {
        return this.bot.getX() == this.treasure.getX() && this.bot.getY() == this.treasure.getY();
    }

    /*private Task<Void> moveTask = new Task<>() {
        @Override
        protected Void call()
        {
            threadBot.MoveRandomly();
            if(treasureIsFound())
                completeGame();
            return null;
        }
    };*/

    void moveUp()
    {
        this.bot.Move(MoveEnum.Up);

        if(treasureIsFound())
            completeGame();

        Main.gameView.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }

    void moveDown()
    {
        this.bot.Move(MoveEnum.Down);

        if(treasureIsFound())
            completeGame();

        Main.gameView.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }

    void moveLeft()
    {
        this.bot.Move(MoveEnum.Left);

        if(treasureIsFound())
            completeGame();

        Main.gameView.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }

    void moveRight()
    {
        this.bot.Move(MoveEnum.Right);

        if(treasureIsFound())
            completeGame();

        Main.gameView.adjustBotAndTreasureLocations(this.bot, this.treasure);
    }
}
