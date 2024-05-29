package paralyzedcoders.test;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import paralyzedcoders.core.ILogic;
import paralyzedcoders.core.Launcher;
import paralyzedcoders.core.RenderManager;
import paralyzedcoders.core.WindowManager;

public class RedactedGame implements ILogic{

  private int direction = 0;
  private float color = 0.0f;

  private final RenderManager renderer;
  private final WindowManager window;

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
      direction = 1;
    }else if(window.isKeyPressed(GLFW_KEY_DOWN)){
      direction = -1;
    }else{
      direction = 0;
    }
  }
  @Override
  public void update(){
    color+= direction*0.01f;
    if (color > 1 ) {
      color = 1.0f;
    }else if(color <= 0){
      color = 0.0f;
    }
  }
  @Override
  public void render(){
    if (window.isResized()) {
      glViewport(0,0,window.getWidth(),window.getHeight());
      window.setResized(true);
    }
    //main render
    // glEnableClientState(GL11.GL_VERTEX_ARRAY);
    // float[] vertices = {
    //     -10.0f, 0.0f, -10.0f,
    //     -10.0f, 0.0f, 10.0f,
    //     10.0f, 0.0f, 10.0f,
    //     10.0f, 0.0f, -10.0f
    // };
    // float[] colors = {
    //     0.0f, 1.0f, 0.0f,
    //     0.0f, 1.0f, 0.0f,
    //     0.0f, 1.0f, 0.0f,
    //     0.0f, 1.0f, 0.0f
    // };
    // float[] texCoords = {
    //     0.0f, 0.0f,
    //     0.0f, 1.0f,
    //     1.0f, 1.0f,
    //     1.0f, 0.0f
    // };

    // glVertexPointer(3, 0, vertices);
    // glColor3f(1.0f, 1.0f, 1.0f);
    // glColorPointer(3, 0, colors);
    // glTexCoordPointer(2, 0, texCoords);
    // glDrawArrays(GL_QUADS, 0, 4);
    // glDisableClientState(GL_VERTEX_ARRAY);

    //clear
    renderer.clear();
  }

  @Override
  public void cleanup() {
    renderer.cleanup();
  }


}
