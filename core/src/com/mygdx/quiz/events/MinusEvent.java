package com.mygdx.quiz.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.quiz.Player;

public class MinusEvent extends Event {

    public MinusEvent() {
        super(3); // Assuming 3 represents a Minus Event
        super.texture = new Texture(Gdx.files.internal("img/bagualosaurus.png")); // Add minus texture image
        super.defaultMessage = "Oh no! You got a Minus Event. ";
        super.squaresToJump = (int) (Math.random() * 10) + 1; // Random number from 1 to 10
    }

    public Texture getMinusTexture() {
        return super.getTexture();
    }

    public int getSquaresToJump() {
        return squaresToJump;
    }

    @Override
    public String getMessage(Player player) {
        return defaultMessage;// + "You'll jump back " + calculateActualSquaresToJump(player) + " squares.";
    }

    // @Override
    // protected int calculateActualSquaresToJump(Player player) {
    // return Math.max(player.position.getCurrent() - squaresToJump, 0);
    // }

    @Override
    public void applyEvent(Player player) {
        int newPosition = player.position.getCurrent() - getSquaresToJump();
        // Ensure the player doesn't jump out of boundaries
        if (newPosition < 0) {
            newPosition = 0;
        }
        player.move(newPosition);
    }
}