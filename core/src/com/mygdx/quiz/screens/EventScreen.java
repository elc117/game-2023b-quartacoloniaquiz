package com.mygdx.quiz.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.quiz.Board;
import com.mygdx.quiz.Player;
import com.mygdx.quiz.Sounds;
import com.mygdx.quiz.events.Event;
import com.mygdx.quiz.events.Quiz;

public class EventScreen implements Screen{
    public Event event;
    public SpriteBatch batch;
    public BitmapFont font;
    public Player player;
    public Board board;
    public GameScreen gameScreen;
    public OrthographicCamera camera;
    private final Sounds sounds;
    private final Stage stage;

    public EventScreen(Player player, Board board, GameScreen gameScreen){
        this.event = board.squares[player.position.getCurrent()].getEvent();
        this.player = player;
        this.board = board;
        this.gameScreen = gameScreen;
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1365, 700);
        sounds = new Sounds();
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(100, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        if (event instanceof Quiz){
            Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/button04.png"))));
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.up = drawable;
            textButtonStyle.down = drawable;
            textButtonStyle.font = font;

            final Quiz quiz = (Quiz) event;
            font.draw(batch, quiz.getQuestion(), 10, Gdx.graphics.getHeight() - 40);

            int y = Gdx.graphics.getHeight() - 550;
            for (final String option : quiz.getOptions()){
                TextButton optionsButton = new TextButton(option, textButtonStyle);
                optionsButton.setPosition(50,  y);
                optionsButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Gdx.input.setInputProcessor(null);
                        if (option.equals(quiz.getCorrectOption())){
                            sounds.rightSound.play();
                        }
                        else {
                            sounds.wrongSound.play();
                            player.position.setPositions(player.position.getPrevious(), player.position.getPrevious());
                        }
                        ScreenManager.setScreen(gameScreen);
                    }
                });

                stage.addActor(optionsButton);
                y += 100;
            }
            Gdx.input.setInputProcessor(stage);

            stage.act(delta);
            stage.draw();
        }
        else {
            font.draw(batch, event.getMessage(player), 10, Gdx.graphics.getHeight() - 100);
            if (Gdx.input.justTouched()){
                ScreenManager.setScreen(gameScreen);
            }
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        sounds.wrongSound.dispose();
        sounds.rightSound.dispose();
        sounds.eventSound.dispose();
    }
}
