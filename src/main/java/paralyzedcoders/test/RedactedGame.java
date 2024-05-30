package paralyzedcoders.test;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import paralyzedcoders.core.ILogic;
import paralyzedcoders.core.Launcher;
import paralyzedcoders.core.RenderManager;
import paralyzedcoders.core.WindowManager;

public class RedactedGame implements ILogic{

  private float color = 0.0f;

  private final RenderManager renderer;
  private final WindowManager window;

  private int playerX, playerY;

  public RedactedGame(){
    renderer = new RenderManager();
    window = Launcher.getWindow();

  }

  @Override
  public void init() throws Exception{
    renderer.init();
  }

  @Override
  public void input(){
    if(window.isKeyPressed(GLFW_KEY_UP)){
      playerX+=1;
    }else if(window.isKeyPressed(GLFW_KEY_DOWN)){
      playerX-=1;
    }
  }
  @Override
  public void update(){

  }
  @Override
  public void render(){
    if (window.isResized()) {
      glViewport(0,0,window.getWidth(),window.getHeight());
      window.setResized(true);
    }
    //main render
    glPushMatrix();
    glTranslatef(playerX, playerY, 0);

    glColor3f(1f, 0f, 0f); // Set color to red

    glBegin(GL_QUADS);
    glVertex2f(-10f, -10f); // Bottom-left corner
    glVertex2f(-10f, 10f); // Top-left corner
    glVertex2f(10f, 10f); // Top-right corner
    glVertex2f(10f, -10f); // Bottom-right corner
    glEnd();

    glPopMatrix();

    //clear
    renderer.clear();
  }

  @Override
  public void cleanup() {
    renderer.cleanup();
  }


}
