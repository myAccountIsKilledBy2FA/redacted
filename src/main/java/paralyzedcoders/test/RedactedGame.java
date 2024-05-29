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
    window.setClearColor(color,color,color,0.0f);

    //clear
    renderer.clear();
  }

  @Override
  public void cleanup() {
    renderer.cleanup();
  }


}
