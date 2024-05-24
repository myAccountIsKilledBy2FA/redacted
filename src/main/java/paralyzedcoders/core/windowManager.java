package paralyzedcoders.core;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryUtil;
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
    if (!GLFW.glfwInit()) throw new IllegalStateException("cringe");
    GLFW.glfwDefaultWindowHints();
    GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
    GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
    GLFW.glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR,3);
    GLFW.glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR,2);
    GLFW.glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_PROFILE);
    GLFW.glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

    boolean maximized = false;
    if (width == 0 || height == 0) {
      width = 100;
      height = 100;
      GLFW.glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
      maximized = true;
    }

    window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
    if(window == MemoryUtil.NULL) throw new RuntimeException("cringe-r = cinge");

    GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
      this.width = width;
      this.height = height;
      this.setResized(true);
    });
    GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
      if(key==GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) GLFW.glfwSetWindowShouldClose(window, true);
    })

  }
  public boolean getResized() {
    return this.resized;
  }

  public void setResized(boolean resized) {
    this.resized = resized;
  }
}