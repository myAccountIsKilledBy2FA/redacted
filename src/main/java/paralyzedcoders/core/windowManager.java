package paralyzedcoders.core;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;
import org.joml.Vector3f;
import org.joml.Matrix4f;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
/**
 * windowManager
 */
public class windowManager {

  public static final float FOV = (float) Math.toRadians(60);
  public static final float Z_NEAR = 0.01f;
  public static final float Z_FAR = 10000f;

  private final String title;

  private int width, height;
  private long window;
  private boolean resized,vSync;

  private final Matrix4f projectionMatrix;

  public windowManager(String title, int width, int height, boolean vSync) {
    this.title = title;
    this.width = width;
    this.height = height;
    this.vSync = vSync;
    projectionMatrix = new Matrix4f();
  }
  public void init() {
    GLFWErrorCallback.createPrint(System.err).set();
    if (!glfwInit()) throw new IllegalStateException("cringe");
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR,3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR,2);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_PROFILE);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

    boolean maximized = false;
    if (width == 0 || height == 0) {
      width = 100;
      height = 100;
      glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
      maximized = true;
    }

    window = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
    if(window == MemoryUtil.NULL) throw new RuntimeException("cringe-r = cinge");

    glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
      this.width = width;
      this.height = height;
      this.setResized(true);
    });
    glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
      if(key==GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
        glfwSetWindowShouldClose(window, true);
    });
    if (maximized) {glfwMaximizeWindow(window);}
    else {
      GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
      glfwSetWindowPos(window, (vidMode.width() -width)/2,
                            (vidMode.height() -height)/2);
    }
    glfwMakeContextCurrent(window);
    if(isvSync()){glfwSwapInterval(1);}
    glfwShowWindow(window);
    GL.createCapabilities();
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_STENCIL_TEST);
    glEnable(GL_CULL_FACE);
    glEnable(GL_BACK);
  }

  public void update(){
    glfwSwapBuffers(window);
    glfwPollEvents();
  }

  public boolean isKeyPressed(int code){
    return glfwGetKey(window, code) == GLFW_PRESS;

  }


  public String getTitle() { return this.title;}

  public void setTitle(String title) { glfwSetWindowTitle(window, title); }
  
  public boolean getResized() {return this.resized;}

  public void setResized(boolean resized) {this.resized = resized;}

  public boolean isvSync() { return this.vSync;}

  public void setvSync(boolean vSync) {this.vSync = vSync;}


  public int getWidth() {
    return this.width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return this.height;
  }

  public void setHeight(int height) {
    this.height = height;
  }


  public long getWindow() {
    return this.window;
  }

  public void setWindow(long window) {
    this.window = window;
  }

  public Matrix4f getProjectionMatrix() {
    return this.projectionMatrix;
  }


}