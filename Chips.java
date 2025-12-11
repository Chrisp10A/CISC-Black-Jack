import java.util.Scanner;

public class Chips {
    int totalChips = 100;
    int betChips;
    int betInsuranceChips;
    public Chips() {
        System.out.println("Chips!");
    }
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

    public void printBet(int inputInt) {
        betChips = inputInt;
        if (totalChips == betChips) {
            System.out.print("YOU GO ALL IN! ");
        }
        System.out.println("You bet " + inputInt);
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

    public void dealerHasAce(Scanner input, int totalDealer, boolean hasInsurance){
        // Prompts user for if they want insurance
        char inputChar = 0;
        System.out.println("The Dealer has an Ace, do you wish to buy insurance? (Y/N) (" + betInsuranceChips + " chips)");
        do {
            inputChar = input.next().charAt(0);
            inputChar = Character.toUpperCase(inputChar);
        } while (!((inputChar == 'Y') || (inputChar == 'N')));
        if (inputChar == 'Y') {
            // If the dealer has 21 sets the boolean true for bought insurance for later
            if (totalDealer == 21) {
                hasInsurance = (true);
            }
            // If bought insurance but Dealer did not have Blackjack
            else {
                // Lose amount bet for insurance and prints no insurance text
                totalChips = totalChips - betInsuranceChips;
                System.out.println("Dealer does NOT have Blackjack");
                System.out.println("You lost " + betInsuranceChips + " chips");
            }
        }
    }

    public void HasBlackjack(int totalDealer, int totalPlayer, String name, boolean hasInsurance){
        // Check if Dealer had Blackjack
        if (totalDealer == 21) {
            if (totalPlayer == 21) {
                System.out.println("The Dealer also had Blackjack!");
            }
            else {
                System.out.println("The Dealer has Blackjack!");
            }
        }
        // Check if both Dealer and Player have Blackjack
        if ((totalDealer == 21) && (totalPlayer == 21)) {
            System.out.println("Stand-off. Both Player and Dealer hava a total of 21.");
            if (hasInsurance) {
                System.out.println("Insurance! You won " + betInsuranceChips + " chips");
                totalChips = totalChips + betInsuranceChips;
            }
            else {
                System.out.println("No change");
            }
        }
        // Checks if Dealer has Blackjack for outcome
        else {
            if (totalDealer == 21) {
                totalChips = totalChips - betChips;
                System.out.println("You lost " + betChips + " chips");
                if (hasInsurance) {
                    totalChips = totalChips + betInsuranceChips * 2;
                    System.out.println("Insurance! You won back your " + betInsuranceChips + " chips");
                }
            }
            else {
                totalChips = totalChips + betChips + (int) ((Math.floor(betChips / 2)));
                System.out.println("Win! You had " + totalPlayer + " while the dealer had " + totalDealer);
                System.out.println("You won " + betChips + " chips and " + (Math.round(Math.floor((betChips / 2)))) + " bonus chips for getting Blackjack");
            }
        }
    }

    public void doubleDown() {
        betChips = betChips * 2;
        System.out.println("Double Down! Bet raised to " + (betChips));
        hit();
    }
}
