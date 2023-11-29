package com.mygdx.quiz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PlusEvent extends Event {

    public PlusEvent() {
        super(2); // Assuming 2 represents a Plus Event
        this.texture = new Texture(Gdx.files.internal("img/quarta_colonia.png")); // Add plus texture image
        this.defaultMessage = "You got a Plus Event! ";
        this.squaresToJump = new Dice().roll();
    }

    public Texture getPlusTexture() {
        return super.getTexture();
    }

    public int getSquaresToJump() {
        return squaresToJump;
    }

    @Override
    public String getMessage(Player player) {
        return defaultMessage; //+ "You'll jump ahead " + calculateActualSquaresToJump(player) + " squares.";
    }

    @Override
    protected int calculateActualSquaresToJump(Player player) {
        // Calculate actual squares to jump after boundary checks
        return Math.min(squaresToJump, 120 - player.position.getCurrent());
    }

    @Override
    public void applyEvent(Player player) {
        int newPosition = player.position.getCurrent() + getSquaresToJump();
        // Ensure the player doesn't jump out of boundaries

        player.move(Math.min(newPosition, 119) - player.position.getCurrent()); // functional programming in Java way :)
    }
}