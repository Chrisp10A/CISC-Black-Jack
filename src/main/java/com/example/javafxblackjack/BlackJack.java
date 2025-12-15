package com.example.javafxblackjack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.application.Platform;


import java.util.Timer;
import java.util.TimerTask;

// Back to GUI
public class BlackJack extends Application {
    // Imported from Main


    // Declare Variables
    int rounds;
    // Used in "newRound"
    int inputInt;
    boolean firstAction;
    char inputChar;
    boolean isValid;
    boolean validNumber;

    public boolean checkRoundExitConditions(int totalPlayer) {
        return ((inputChar == 'S') || (inputChar == 'D') || (21 < totalPlayer));
    }

    public boolean checkHit() {
        return ((inputChar == 'D') || (inputChar == 'H'));
    }

    public boolean checkDoubleDown() {
        return (inputChar == 'D');
    }

    public boolean checkBusted(int totalPlayer) {
        return (totalPlayer > 21);
    }

    Label arrowText;
    Label status;
    TextField inputArea;
    TextArea textHistory;
    static Label time;

    Button confirmButton;
    Button newGameButton;
    Button loadGameButton;
    Button saveGameButton;
    String name;
    enum BlackJackGameState {EnterName,DoYouWantToPlay, NewRound, DealerHasAce, HasBlackjack, StartRound};
    BlackJackGameState GameState;
    GameController gameController;
    Chips chips;
    ChipsManager ChipsManager;

    Clock2 clock2;

    public void ConfirmButtonPushed(ActionEvent event)
    {
        String text  = inputArea.getText();
        inputArea.setText("");
        switch(GameState) {
            case  BlackJackGameState.EnterName:
                // Asks the user for name
                if(text == "") {
                    name = "The Player";
                    println("Hello, player. Are you up for a game of Blackjack?");
                } else {
                    name = text;
                    println("Hello, " + name + ". Are you up for a game of Blackjack?");
                }
                println("Type 'Y' for yes (type anything else for no): ");
                GameState = BlackJackGameState.DoYouWantToPlay;
                break;
            case  BlackJackGameState.DoYouWantToPlay:
                // If they typed yes then reset all values and start the game
                if (text.equals("Y") || text.equals("y") || text.equals("yes") || text.equals("Yes")) {
                    println("Ok!");
                    println("Starting Game");
                    // Resets all vars and prompts user
                    fullReset();
                    GameState = BlackJackGameState.NewRound;

                } else {
                    println("Aw man :(");
                    println("Quitting game...");
                    Timer t = new Timer();
                    // Wait 1 second
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.exit(0);
                        }
                    },1000);
                }

                break;
            case BlackJackGameState.NewRound:
                validNumber = false;
                // Check if valid amount of chips to bet
                newRound(text, chips.getChips());
                if (validNumber) {
                    // If dealer has Ace
                    if (chips.checkDealerHasAce(gameController.DealerHasAce)) {
                        chips.dealerHasAce(this);
                        GameState = BlackJackGameState.DealerHasAce;
                        break;
                    } else {
                        // If dealer or player has Blackjack
                        if (gameController.checkHasBlackjack()) {
                            gameController.printIfPlayerHasBlackjack(this);
                            gameController.dealerPlay(this);
                            chips.HasBlackjack(gameController.totalDealer, gameController.totalPlayer, gameController.name, gameController.hasInsurance, this);
                            fullReset();
                            GameState = BlackJackGameState.NewRound;
                        } else {
                            // Start round if neither
                            setupRound();
                            GameState = BlackJackGameState.StartRound;
                        }
                    }
                }
                break;
            case BlackJackGameState.StartRound:
                try {
                    // Take only the first letter
                    inputChar = text.charAt(0);
                    inputChar = Character.toUpperCase(inputChar);
                    // Check if valid
                    startRound();
                    if (isValid) {
                        if (checkDoubleDown()) {
                            chips.doubleDown(this);
                        }
                        if (checkHit()) {
                            gameController.hit(this);
                        }
                        // Check if Busted/Stand/Doubledown
                        if (checkRoundExitConditions(gameController.totalPlayer)) {
                            // If busted
                            if (checkBusted(gameController.totalPlayer)) {
                                chips.loseBusted(gameController.totalPlayer, this);
                            } else {
                                // Dealer play
                                gameController.dealerPlay(this);
                                chips.endRound(gameController.totalPlayer, gameController.totalDealer, this);
                                chips.endResult(gameController.totalPlayer, gameController.totalDealer, this);
                            }
                            if (chips.checkLose()) {
                                println("Game Over! You ran out of chips");
                                // Sets chips to 100 for next game
                                ChipsManager.save(100, this);
                                Timer t = new Timer();
                                t.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        System.exit(0);
                                    }
                                },1000);
                                break;
                            }
                            if (chips.checkWin()) {
                                println("No way! You have over 1000 chips! You win!!!");
                                ChipsManager.save(100, this);
                                Timer t = new Timer();
                                t.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        System.exit(0);
                                    }
                                },1000);
                                break;
                            }
                            println("Next round!");
                            fullReset();
                            GameState = BlackJackGameState.NewRound;
                        }
                    }
                } catch (Exception e) {
                    println("ERROR: Please type a valid letter");
                }
                break;
            case BlackJackGameState.DealerHasAce:
                    inputChar = text.charAt(0);
                    inputChar = Character.toUpperCase(inputChar);
                if (inputChar == 'Y') {
                    // If the dealer has 21 sets the boolean true for bought insurance for later
                    if (gameController.totalDealer == 21) {
                        chips.HasBlackjack(gameController.totalDealer, gameController.totalDealer, gameController.name, true, this);
                        fullReset();
                        GameState = BlackJackGameState.NewRound;
                    }
                    // If bought insurance but Dealer did not have Blackjack
                    else {
                        // Lose amount bet for insurance and prints no insurance text
                        chips.totalChips = chips.totalChips - chips.betInsuranceChips;
                        println("Dealer does NOT have Blackjack");
                        println("You lost " + chips.betInsuranceChips + " chips");
                        setupRound();
                        GameState = BlackJackGameState.StartRound;
                    }
                }
                else {
                    println("Dealer does NOT have Blackjack");
                    setupRound();
                    fullReset();
                    GameState = BlackJackGameState.NewRound;
                }
                break;
        }
    }

    public void fullReset(){
        gameController.reset();
        resetTextAndPrompt();
    }

    public void NewGameButtonPushed(ActionEvent event)
    {
            textHistory.setText("New Game!");
            chips.totalChips = 100;
            println(" Chips set to " + chips.totalChips);
            status.setText("New game started!");
            println("Welcome! This is CISC-Black-Jack");
            println("Enter your name (leave blank for default name): ");
            GameState = BlackJackGameState.EnterName;
    }

    public void SaveGameButtonPushed(ActionEvent event)
    {
        ChipsManager.save(chips.totalChips, this);
        println("Save complete");
        status.setText("Game saved");
    }

    public void LoadGameButtonPushed(ActionEvent event)
    {
        chips.totalChips = ChipsManager.load(this);
        println("Chips set to " + chips.totalChips);
        println("Loaded from last save");
        status.setText("Game loaded!");
        rounds = 0;
        gameController.reset();
        resetTextAndPrompt();
        GameState = BlackJackGameState.NewRound;
    }



    @Override
    public void start(Stage stage) {
// Title
        stage.setTitle("CISC-BLACKJACK ");
//  header
        arrowText = new Label("Type in here ->");
        time = new Label("Time will show up here!");
// status label
        status = new Label("Please enter your name!");
// TextField
        inputArea = new TextField();
        confirmButton = new Button("Confirm");
// Add Button
        newGameButton = new Button("New Game");
        saveGameButton = new Button("Save Game");
        loadGameButton = new Button("Load Game");
        textHistory = new TextArea();
        textHistory.setEditable(false);
// Set up Confirm button.
        confirmButton.setOnAction(this::ConfirmButtonPushed);
// Set up ChipsManager button.
        newGameButton.setOnAction(this::NewGameButtonPushed);
        saveGameButton.setOnAction(this::SaveGameButtonPushed);
        loadGameButton.setOnAction(this::LoadGameButtonPushed);
// Set scene and show stage
        HBox input = new HBox(12, arrowText, inputArea, confirmButton);
        HBox reset = new HBox(newGameButton, saveGameButton, loadGameButton);
        VBox main = new VBox(12, status, input, reset, textHistory, time);
        Scene scene = new Scene(main, 420, 350);
        main.setPadding(new Insets(16));
        stage.setScene(scene);
        GameState = BlackJackGameState.EnterName;
        stage.show();

        //Make Instances
        gameController = new GameController();
        chips = new Chips();
        ChipsManager = new ChipsManager();
        // Okay Now it's time for stuff
        println("Welcome! This is CISC-Black-Jack");
        println("Enter your name (leave blank for default name): ");
        if (ChipsManager.load(this) != 100) {
            println("Save game detected! Press load continue save.");
        }
        clock2 = new Clock2(this);
        clock2.start();
        GameState = BlackJackGameState.EnterName;
    }




    public void print(String text) {
        textHistory.appendText(text);
    }

    public void println(String text) {
        textHistory.appendText(text + "\n");
    }

    // Imported from Main
    public void resetTextAndPrompt() {
        inputInt = 0;
        firstAction = true;
        validNumber = false;
        inputChar = 0;
        println("----------------------");
        if (rounds == 0) {
            println("How many chips are you betting? You have: " + chips.getChips() + " chips.");
        } else {
            println("How many chips are you betting? You have: " + chips.getChips() + " chips. (Type '0' to cash out)");
        }
    }

    public void tick(String time) {
        setTime(time);
    }

    public void setTime(String realTime){
        System.out.println(realTime);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                time.setText(realTime);
            }
        });

        //
         //
    }

    public void newRound(String input, int totalChips) {
        // Prompt user for chips
            try {
                inputInt = Integer.parseInt(input);
                if (inputInt > totalChips) {
                    println("ERROR: You do not have enough chips");
                    return;
                } else if (inputInt < 0) {
                    println("ERROR: Please enter a positive integer");
                    return;
                } else if (inputInt == 0) {
                    endGame(chips.totalChips);
                    return;
                    // Will add back once all finished
                    //endGame();
                }
            } catch (Exception e) {
                println("ERROR: Please type a number");
                return;
            }
        if ((0 <= inputInt) && (inputInt <= totalChips)) {
            println("");
            rounds++;
            println("Round " + (rounds) + ":");
            chips.printBet(inputInt, this);
            gameController.intialDraw(this, name);
            validNumber = true;
        }
    }


    public void setupRound() {
        // Local Variable
        inputChar = 0;
        boolean isValid;
        // If you can Double Down (Needs to be first time asking and have enough to bet)
        if ((chips.betChips * 2 <= chips.totalChips) && (firstAction)) {
            println("Do you Hit (H), Stand (S), or Double Down (D)?");
        } else {
            println("Do you Hit (H) or Stand (S)?");
        }
    }
    public void startRound() {

        // If it was a valid action
        if ((inputChar == 'H') || (inputChar == 'S') || ((inputChar == 'D') && (chips.betChips * 2 <= chips.totalChips) && (firstAction))) {
            firstAction = false;
            isValid = true;
        } else {
            println("ERROR: Please type a valid letter");
            isValid = false;
        }
        // Adds a space for readablity
        println("");
    }


    public void endGame(int totalChips) {
        // Ends the game, prints summary, and quits the program
        println("----------------------");
        if (rounds == 1) {
            println("You played 1 round");
        } else {
            println("You played " + rounds + " rounds");
        }
        println("You left with " + totalChips + " chips");
        if (totalChips < 100) {
            println("You lost " + (100 - totalChips) + " chips");
        } else if (totalChips == 100) {
            println("You did not gain nor lose any chips");
        } else {
            println("You won " + (totalChips - 100) + " chips!");
        }
        println("Thanks for playing! :)");

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        },1000);
    }
}