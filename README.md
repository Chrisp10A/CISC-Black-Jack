# CISC BLACKJACK
A program that plays Blackjack

# Java overview, JVM, OOP concepts
The project is organized into different classes for better readabilty

<img width="138" height="215" alt="image" src="https://github.com/user-attachments/assets/c127f884-cc95-46dc-9d1e-5ab09eb7218e" />

Classes call methods from other classes by creating an insatance.

<img width="313" height="98" alt="image" src="https://github.com/user-attachments/assets/5c8989b0-1d59-4168-bf52-1bee36e63785" />


# Variables, Types, & I/O<br>
We have used mutiple types such as Char, Boolean, Integers, and Strings to work with data.
Like in lines 15-32 of BlackJack.java

<img width="186" height="140" alt="image" src="https://github.com/user-attachments/assets/14c3f755-16fb-4fc4-b889-acbc588fc17f" />

Used enum Gamestate variable for which phase the game is on.

<img width="933" height="24" alt="image" src="https://github.com/user-attachments/assets/634ca2b9-8b07-49c0-adc3-39ca376f3348" />
      
# Control flow: `if`, `switch`, & Loops
If-else statements are used for most of the logic. Chips.java (133-153)

<img width="730" height="502" alt="image" src="https://github.com/user-attachments/assets/ff116eec-f9db-414f-8eca-feaeaebe0be3" />

The Gamestates are one big case statement that occurs whenever you hit "confirm" BlackJack.java (72-93)

<img width="769" height="504" alt="image" src="https://github.com/user-attachments/assets/a24f3228-04da-4c6c-a364-73e634ecdab2" />

The confirm button operates on a switch statement

For loops in Deck.java there is a for loop that creates all cards for the game (33-41)

<img width="506" height="280" alt="image" src="https://github.com/user-attachments/assets/d962303d-ffba-4f93-bbc0-a38c763e2c4e" />

# Exceptions (intro), debugging
All inputs are put into a try function incase of an exception. BlackJack.java (355-373)
<img width="473" height="434" alt="image" src="https://github.com/user-attachments/assets/a77e26a2-b7e1-4680-870e-32cb4c1974f6" />


# Methods, parameters, blocks, scope
* This project uses mutiple methods such as
* fullReset()

# Arrays & ArrayLists
Arrays and ArrayLists are used to list the cards and hands.
The deck is comprised of calls of the Card arrays in the class.

# Objects & classes
We use a varitey of classes such as:
public class Clock2 (Clock2.java)
public class BlackJack (BlackJack.java)
public class GameController (GameController.java)
public interface Chips (Chips.java)
public class ChipsManager (ChipsManager.java)

# Abstract classes & interfaces
Used flexible and reusable classes like Card and Deck

# Files
Used Chips.Manager to save and load the chip count located in player_chips.txt. 
This is done by using FileWriter and BuffredReader to edit the file directly.

<img width="694" height="503" alt="image" src="https://github.com/user-attachments/assets/84baa353-8edc-4bb3-9458-47b6bec5ab20" />


# JavaFX
Used Basic JavaFX GUI to create a textbox and buttons for the game.
All code that manages the GUI is in BlackJack.java or the main class of the project. 

The bulk of the setup is in lines 261-292

<img width="630" height="725" alt="image" src="https://github.com/user-attachments/assets/b7414442-cebf-42c6-bede-cb0767a32685" />

The buttons use event actions to trigger.
The confirm button is the largest due to how the function has to change for every possible action.
Here is some of the code 229-245:

<img width="563" height="406" alt="image" src="https://github.com/user-attachments/assets/93d0a8fc-ac10-44f9-9b1d-7a3b1bc8f9fe" />

# Robustness & coding standards
* We use lower camle case for all variables

# Multithreading
Mutlithreading was used so a clock would tick independent from the rest of the code. 
To do this we create the object clock and run a thread that updates about every second.
Most of the clock code is in the file clock2.java:
<img width="500" height="759" alt="image" src="https://github.com/user-attachments/assets/b61d555e-3686-4f37-b06a-f019e1d28df2" />

