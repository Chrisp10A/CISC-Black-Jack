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
        return totalChips;
    }
    public int getBetInsuranceChips() {
        return totalChips;
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
}
