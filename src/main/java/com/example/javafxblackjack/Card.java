package com.example.javafxblackjack;
/**
 * An object of type Card represents a playing card from a
 * standard Poker deck, excluding Jokers.  The card has a suit, which
 * can be spades, hearts, diamonds, or clubs.  A spade, heart,
 * diamond, or club has one of the 13 values: ace, 2, 3, 4, 5, 6, 7,
 * 8, 9, 10, jack, queen, or king.  Note that "ace" is considered to be
 * the smallest value.
 */
public class Card {

    public final static int SPADES = 0;   // Codes for the 4 suits, plus Joker.
    public final static int HEARTS = 1;
    public final static int DIAMONDS = 2;
    public final static int CLUBS = 3;

    public final static int ACE = 1;      // Codes for the non-numeric cards.
    public final static int JACK = 11;    //   Cards 2 through 10 have their
    public final static int QUEEN = 12;   //   numerical values for their codes.
    public final static int KING = 13;

    /**
     * This card's suit, one of the constants SPADES, HEARTS, DIAMONDS, or CLUBS.
     * The suit cannot be changed after the card is constructed.
     */
    private final int suit;

    /**
     * The card's value. This is one of the values 1 through 13, with 1 representing ACE.
     * The value cannot be changed after the card is constructed.
     */
    private final int value;

    /**
     * Creates a card with a specified suit and value.
     * @param theValue the value of the new card.
     * The value must be in the range 1 through 13, with 1 representing an Ace.
     * You can use the constants Card.ACE, Card.JACK, Card.QUEEN, and Card.KING.
     * @param theSuit the suit of the new card.  This must be one of the values
     * Card.SPADES, Card.HEARTS, Card.DIAMONDS, Card.CLUBS.
     * @throws IllegalArgumentException if the parameter values are not in the
     * permissible ranges
     */
    public Card(int theValue, int theSuit) {
        if (theSuit != SPADES && theSuit != HEARTS && theSuit != DIAMONDS &&
                theSuit != CLUBS)
            throw new IllegalArgumentException("Illegal playing card suit");
        if (theValue < 1 || theValue > 13)
            throw new IllegalArgumentException("Illegal playing card value");
        value = theValue;
        suit = theSuit;
    }

    /**
     * Returns the suit of this card, which is one of the constants Card.SPADES,
     * Card.HEARTS, Card.DIAMONDS, Card.CLUBS, or Card.JOKER
     */
    public int getSuit() {
        return suit;
    }

    /**
     * Returns the value of this card.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns a String representation of the card's suit.
     */
    public String getSuitAsString() {
        return switch (suit) {
            case SPADES -> "♤";
            case HEARTS -> "♡";
            case DIAMONDS -> "♢";
            default -> "♧";
        };
    }

    /**
     * Returns a String representation of the card's value.
     * One of the strings "Ace", "2", "3", ..., "10", "Jack", "Queen", or "King".
     */
    public String getValueAsString() {
        return switch (value) {
            case 1 -> "A";
            case 2 -> "2";
            case 3 -> "3";
            case 4 -> "4";
            case 5 -> "5";
            case 6 -> "6";
            case 7 -> "7";
            case 8 -> "8";
            case 9 -> "9";
            case 10 -> "10";
            case 11 -> "J";
            case 12 -> "Q";
            default -> "K";
        };
    }

    /**
     * Returns a string representation of this card, including both
     * its suit and its value.
     */
    public String toString() {
        return getValueAsString() + getSuitAsString();
    }


} // end class Card