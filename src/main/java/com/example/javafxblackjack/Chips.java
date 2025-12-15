package com.example.javafxblackjack;
import java.util.Scanner;

public class Chips {
    int totalChips = 100;
    int betChips;
    int betInsuranceChips;
    /*
    public Chips() {
        System.out.println("Chips!");
    }
    */
    public int getChips() {
        return totalChips;
    }
    public int getBetChips() {
        return betChips;
    }
    public int getBetInsuranceChips() {
        return betInsuranceChips;
    }
    public void addChips(int chips) {
        totalChips = totalChips + chips;
    }
    public void subtractChips(int chips) {
        totalChips = totalChips - chips;
    }
    public void betChips(int chips) {
        betChips = chips;
    }
    
    public boolean checkLose(){
        if (totalChips == 0) {
            return (true);
        }
        else {
            return (false);
        }
    }

    public boolean checkWin(){
        if (totalChips >= 1000) {
            return (true);
        }
        else {
            return (false);
        }
    }

    public void printBet(int inputInt, BlackJack blkj) {
        betChips = inputInt;
        if (totalChips == betChips) {
            blkj.println("YOU GO ALL IN! ");
        }
        blkj.println("You bet " + inputInt);
    }

    public boolean checkDealerHasAce(boolean DealerHasAce) {
        // If the Player has enough chips and Dealer has an ace (insurance)
        if ((DealerHasAce) && ((int) (Math.floor(betChips / 2)) + betChips) <= totalChips) {
            betInsuranceChips = (int) (Math.floor(betChips / 2));
            return (true);
        }
        else {
            return (false);
        }
    }

    public void dealerHasAce(BlackJack blkj){
        // Prompts user for if they want insurance
        blkj.println("The Dealer has an Ace, do you wish to buy insurance? (Y/N) (" + betInsuranceChips + " chips)");
    }

    public void HasBlackjack(int totalDealer, int totalPlayer, String name, boolean hasInsurance, BlackJack blkj){
        // Check if Dealer had Blackjack
        if (totalDealer == 21) {
            if (totalPlayer == 21) {
                blkj.println("The Dealer also had Blackjack!");
            }
            else {
                blkj.println("The Dealer has Blackjack!");
            }
        }
        // Check if both Dealer and Player have Blackjack
        if ((totalDealer == 21) && (totalPlayer == 21)) {
            blkj.println("Stand-off. Both Player and Dealer hava a total of 21.");
            if (hasInsurance) {
                blkj.println("Insurance! You won " + betInsuranceChips + " chips");
                totalChips = totalChips + betInsuranceChips;
            }
            else {
                blkj.println("No change");
            }
        }
        // Checks if Dealer has Blackjack for outcome
        else {
            if (totalDealer == 21) {
                totalChips = totalChips - betChips;
                blkj.println("You lost " + betChips + " chips");
                if (hasInsurance) {
                    totalChips = totalChips + betInsuranceChips * 2;
                    blkj.println("Insurance! You won back your " + betInsuranceChips + " chips");
                }
            }
            else {
                totalChips = totalChips + betChips + (int) ((Math.floor(betChips / 2)));
                blkj.println("Win! You had " + totalPlayer + " while the dealer had " + totalDealer);
                blkj.println("You won " + betChips + " chips and " + (Math.round(Math.floor((betChips / 2)))) + " bonus chips for getting Blackjack");
            }
        }
    }

    public void doubleDown(BlackJack blkj) {
        betChips = betChips * 2;
        blkj.println("Double Down! Bet raised to " + (betChips));
    }

    public void endRound(int totalPlayer, int totalDealer, BlackJack blkj){
        // In chips class because other methods change chips
        // Could probably use a case statement here
        // If player busted
        // Prints total value of player
        blkj.println("Stand Total: " + (totalPlayer));
        // Adds a '!' if exactly 21
        if (totalPlayer == 21) {
            blkj.println("!");
        }
        else {
            blkj.println("");
        }
        }

    public void endResult(int totalPlayer, int totalDealer, BlackJack blkj) {
        // If Dealer busted
        if (21 < totalDealer) {
            winRound(totalPlayer, totalDealer, blkj);
        }
        else {
            // If Dealer and Player tied
            if (totalDealer == totalPlayer) {
                tieRound(totalPlayer, blkj);
            }
            else {
                // Check if Player is higher than Dealer, if yes then win
                if (totalDealer < totalPlayer) {
                    winRound(totalPlayer, totalDealer, blkj);
                }
                else {
                    loseRound(totalPlayer, totalDealer, blkj);
                }
            }
        }
    }

    public void winRound(int totalPlayer, int totalDealer, BlackJack blkj){
        // Prints win text and increases chips by amount bet, also accounts for if the Dealer busted instead comparing
        totalChips = totalChips + betChips;
        if (21 < totalDealer) {
            blkj.println("Busted " + totalDealer + " is over 21.");
            blkj.println("Win! You had " + totalPlayer + " while the Dealer busted!");
        }
        else {
            blkj.println("Win! You had " + totalPlayer + " while the dealer had " + totalDealer);
        }
        blkj.println("You won " + betChips + " chips");
    }
    
    public void loseBusted(int totalPlayer, BlackJack blkj){
        // Prints lose text on screen and decreases chips by amount bet
        blkj.println("Busted. " + totalPlayer + " is over 21");
        totalChips = totalChips - betChips;
        blkj.println("You lost " + betChips + " chips");
    }

    public void tieRound(int totalPlayer, BlackJack blkj){
        // Prints text if tied
        blkj.println("Stand-off. Both dealer and player had " + totalPlayer);
        blkj.println("No change");
    }

    public void loseRound(int totalPlayer, int totalDealer, BlackJack blkj){
        // Prints lose text on screen, decreases chips by amount bet, and prints both values
        blkj.println("Loss. You had " + totalPlayer + " while the dealer had " + totalDealer);
        totalChips = totalChips - betChips;
        blkj.println("You lost " + betChips + " chips");
    }
}
