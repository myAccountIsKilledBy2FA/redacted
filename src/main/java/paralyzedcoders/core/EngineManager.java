package paralyzedcoders.core;

import org.lwjgl.glfw.GLFWErrorCallback;
import paralyzedcoders.core.ILogic;
import paralyzedcoders.core.utils.Constants;

import static org.lwjgl.glfw.GLFW.*;

public class EngineManager {
  public static final long nano = 1000000000L;
  public static final float FRAMERATE = 1000;

  private static int fps;
  private static float frameTime = 1.0f/ FRAMERATE;

  private boolean isRunning;
  
  private WindowManager window;
  private GLFWErrorCallback errorCallback;
  private ILogic gameLogic;
  
  private void init() throws Exception {
    glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
    gameLogic = Launcher.getGame();
    window = Launcher.getWindow();
    window.init();
    gameLogic.init();
  }
  public void start() throws Exception {
    init();
    if (isRunning) {
      return;
    }
    run();
  }
  private void run(){
    this.isRunning = true;
    int frames = 0;
    long frameCounter = 0;
    long lastTime = System.nanoTime();
    double unprocessedTime = 0;
    
    while (isRunning) {
      boolean render = false;
      long startTime = System.nanoTime();
      long passedTime = startTime - lastTime;
      lastTime = startTime;
      unprocessedTime += passedTime/(double)nano;
      frameCounter += passedTime;
       
      input();

      while(unprocessedTime>frameTime) {
        render = true;
        unprocessedTime -=frameTime;
        if(window.isClosed()){
          stop();
        }if (frameCounter > nano) {
          setFps(frames);
          window.setTitle(Constants.TITLE+" FPS:"+getFps());
          frames = 0;
          frameCounter = 0;
        }
      }
      if (render) {
        update();
        render();
        frames++;

      }
    }
    clean();
  }
  private void stop(){
    if(!isRunning) {
      return;
    }
    isRunning = false;
  }
  private void input(){
    gameLogic.input();
  }
  private void render(){
    gameLogic.render();
    window.update();
  }
  private void update(){
    gameLogic.update();
  }
  private void clean(){
    window.cleanup();
    gameLogic.cleanup();
    errorCallback.free();
    glfwTerminate();
  }
  public int getFps() {
    return fps;
  }
  public void setFps(int fps) {
    EngineManager.fps = fps;
  }
}
