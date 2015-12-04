import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.io.IOException;
import org.newdawn.slick.Color;


public class Game {

    public static void main(String[] args) throws LWJGLException, IOException {

        initGL(800, 600);
        Menu gameMenu = new Menu();
        MovementTest newgame = new MovementTest();
        TrueTypeFont menuFont = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), true);
        menuFont.drawString(Display.getWidth() - 100, 30, "Move with the arrow keys. Shoot with WASD.  ", Color.red);

        gameMenu.addItem("Play Cattack", newgame);

        gameMenu.addSpecial("Exit", Menu.DO_EXIT);

        Scene currScene = gameMenu;

            while (currScene.go()) {
                currScene = currScene.nextScene();

                if (currScene == null && newgame.getState() != 3 && newgame.getState() != 4) {
                    gameMenu = new Menu();
                    gameMenu.addItem("Resume current game", MovementTest.current);
                    gameMenu.addItem("Start new game", new MovementTest());
                    gameMenu.addSpecial("Exit", Menu.DO_EXIT);
                    currScene = gameMenu;
                }

                if (newgame.getState()==3)
                {
                    newgame.setState(0);

                    gameMenu = new Menu();
                    gameMenu.addItem("You won", new MovementTest());
                    gameMenu.addItem("Play Cattack", new MovementTest());

                    gameMenu.addSpecial("Exit", Menu.DO_EXIT);
                    currScene = gameMenu;

                }
                if (newgame.getState()==4)
                {
                    newgame.setState(0);

                    gameMenu = new Menu();
                    gameMenu.addItem("You lose", new MovementTest());
                    gameMenu.addItem("Play Cattack", new MovementTest());

                    gameMenu.addSpecial("Exit", Menu.DO_EXIT);
                    currScene = gameMenu;
                }
            }
        Display.destroy();
    }

    public static void initGL(int width, int height) throws LWJGLException {
        // open window of appropriate size
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.create();
        Display.setVSyncEnabled(true);

        // enable 2D textures
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // set "clear" color to black
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // set viewport to entire window
        GL11.glViewport(0, 0, width, height);

        // set up orthographic projectionr
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        // GLU.gluPerspective(90f, 1.333f, 2f, -2f);
        // GL11.glTranslated(0, 0, -500);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
}