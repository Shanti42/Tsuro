package tsuro;

import javax.swing.*;
import java.awt.*;

public class PlayerHand {
/*
    int handSize;

    //the JPanel holding the players hand of TsuroButtons
    private JPanel pannelPlayer;

    //holds the array of TsuroButtons that make up the player's hand
    private TsuroButton[] hand;

    public PlayerHand(int handSize) {
        this.handSize = handSize;
        pannelPlayer = new JPanel(new GridLayout(1, handSize));
        Container container = getContentPane();
        container.add(pannelPlayer, "Center");
        hand = new TsuroButton[handSize + 1];

        *//*
         * initilizes the TsuroButtons in the players hand
         *//*
        for (int i = 1; i < handsize + 1; i++){
            hand[i] = new TsuroButton();
            hand[i].setConnections(hand[i].makeRandomConnectArray());
            hand[i].addStone(stoneColor, stoneLocation);
            hand[i].addActionListener(Player.this);
            pannelPlayer.add(hand[i]);
        }
        *//**
         * transfering aspects of player into player hand to abstract it
         * is it possible to just take contentpain to competly sidconnect aspects
         * could also have a value for stone info, but I feel like player should have that
         * some repeting code that should be put in methods in tsuro operations
         * game does not end when player dies, need better checks, but only work on
         * that after proper compartmentalization
         *
         *//*
    }*/
}
