import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.LWJGLException;



public class Game {

    public static void main(String[] args) throws LWJGLException {

        initGL(1024, 768);

        Menu gameMenu = new Menu();
        //gameMenu.addItem("PathTest", new PathTest());
        gameMenu.addItem("MovementTest", new MovementTest());
        gameMenu.addSpecial("Exit", Menu.DO_EXIT);

        Scene currScene = gameMenu;

        while ( currScene.go()  )
        {
             // if nextScene() returns null (the default) reload the menu
            currScene = currScene.nextScene();

            if (currScene == null)
            {
                currScene = gameMenu;
            }

            System.out.println("Changing Scene: " + currScene);
        }


        Display.destroy();

    }


    public static void initGL(int width, int height) throws LWJGLException
    {
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
        GL11.glViewport(0,0,width,height);
         
        // set up orthographic projectionr
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        // GLU.gluPerspective(90f, 1.333f, 2f, -2f);
        // GL11.glTranslated(0, 0, -500);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
}
