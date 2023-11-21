package com.mygdx.quiz;

import com.badlogic.gdx.graphics.Texture;

public class PlusEvent extends Event {

    public PlusEvent() {
        super(1); // Assuming 1 represents a Plus Event
        this.texture = null; // Add plus texture image
        this.defaultMessage = "You got a Plus Event! ";
        this.squaresToJump = (int) (Math.random() * 10) + 1; // Random number of squares to jump, from 1 to 10
    }

    public Texture getPlusTexture() {
        return super.getTexture();
    }

    public int getSquaresToJump() {
        return squaresToJump;
    }

    @Override
    public String getMessage(Player player) {
        return defaultMessage + "You'll jump ahead " + calculateActualSquaresToJump(player) + " squares.";
    }

    @Override
    protected int calculateActualSquaresToJump(Player player) {
        // Calculate actual squares to jump after boundary checks
        return Math.min(squaresToJump, 120 - player.getPosition());
    }

    @Override
    public void applyEvent(Player player) {
        int newPosition = player.getPosition() + getSquaresToJump();
        // Ensure the player doesn't jump out of boundaries
        player.move(Math.min(newPosition, 120) - player.getPosition()); // functional programming in Java way :)
        System.out.println(getMessage(player)); // Display the message
    }
}