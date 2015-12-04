import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


/**
 * Created by FATHER on 11/30/2015.
 */
public class Powerup extends Entity{
    private Texture texture;
    private float imageWidth;
    private float imageHeight;

    private float x;
    private float y;

    public Powerup(String imgpath, int x, int y, int width, int height) {
        super(x, y, width, height);
        try
        {
            GL11.glColor3f(0,0,0);

            texture = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream(imgpath));
            imageWidth = (float)texture.getImageWidth() / texture.getTextureWidth()  ;
            imageHeight = (float)texture.getImageHeight() / texture.getTextureHeight() ;
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
            System.err.println("cannot open resource");
        }
    }

    public void init()
    {

    }

    public void destroy()
    {

    }

    public void update(float delta) {
    }
    public void draw()
    {
        GL11.glColor3f(1,1,1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        int x = hitbox.getX();
        int y = hitbox.getY();
        int w = hitbox.getWidth();
        int h = hitbox.getHeight();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(x, y);

        GL11.glTexCoord2f(imageWidth, 0);
        GL11.glVertex2f(x + w, y);

        GL11.glTexCoord2f(imageWidth, imageHeight);
        GL11.glVertex2f(x + w, y + h);

        GL11.glTexCoord2f(0, imageHeight);
        GL11.glVertex2f(x, y + h);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL11.glEnd();
    }

    public void onCollision(Entity other)
    {

    }
}
