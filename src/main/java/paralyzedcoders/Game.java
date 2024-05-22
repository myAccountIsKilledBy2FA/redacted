package paralyzedcoders;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.joml.Vector3f;
import org.joml.Matrix4f;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Game {
    private long window;
    private int width = 800;
    private int height = 600;

    private float playerRotation = 0.0f;

    private Vector3f cameraPos = new Vector3f(0.0f, 1.0f, 0.0f);
    private float cameraYaw = -90.0f;
    private float cameraPitch = 0.0f;
    private float lastX = width / 2.0f;
    private float lastY = height / 2.0f;
    private boolean firstMouse = true;

    private Vector3f playerPos = new Vector3f(0.0f, 0.0f, 0.0f);
    private float playerSpeed = 0.05f;

    private Matrix4f projectionMatrix = new Matrix4f();
    private Matrix4f viewMatrix = new Matrix4f();

    public void run() {
        init();
        loop();
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, "Simple 3D Game", NULL, NULL);
        if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            this.width = width;
            this.height = height;
            glViewport(0, 0, width, height);
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glEnable(GL_DEPTH_TEST);

        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
            handleMouseMovement(xpos, ypos);
        });

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }


    private void loop() {
        
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            handleInput();
            updateCamera();
            renderScene();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void handleMouseMovement(double xpos, double ypos) {
        if (firstMouse) {
            lastX = (float) xpos;
            lastY = (float) ypos;
            firstMouse = false;
        }

        float xoffset = (float) xpos - lastX;
        float yoffset = lastY - (float) ypos;
        lastX = (float) xpos;
        lastY = (float) ypos;

        float sensitivity = 0.1f;
        xoffset *= sensitivity;
        yoffset *= sensitivity;

        cameraYaw += xoffset;
        cameraPitch += yoffset;

        if (cameraPitch > 89.0f) cameraPitch = 89.0f;
        if (cameraPitch < -89.0f) cameraPitch = -89.0f;
    }
    
    private void handleInput() {
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            playerPos.x += (float) Math.sin(Math.toRadians(cameraYaw)) * playerSpeed;
            playerPos.z -= (float) Math.cos(Math.toRadians(cameraYaw)) * playerSpeed;
        }
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            playerPos.x -= (float) Math.sin(Math.toRadians(cameraYaw)) * playerSpeed;
            playerPos.z += (float) Math.cos(Math.toRadians(cameraYaw)) * playerSpeed;
        }
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            playerPos.x -= (float) Math.cos(Math.toRadians(cameraYaw)) * playerSpeed;
            playerPos.z -= (float) Math.sin(Math.toRadians(cameraYaw)) * playerSpeed;
        }
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            playerPos.x += (float) Math.cos(Math.toRadians(cameraYaw)) * playerSpeed;
            playerPos.z += (float) Math.sin(Math.toRadians(cameraYaw)) * playerSpeed;
        }
    }

    private void updateCamera() {
        Vector3f direction = new Vector3f();
        direction.x = (float) Math.cos(Math.toRadians(cameraYaw)) * (float) Math.cos(Math.toRadians(cameraPitch));
        direction.y = (float) Math.sin(Math.toRadians(cameraPitch));
        direction.z = (float) Math.sin(Math.toRadians(cameraYaw)) * (float) Math.cos(Math.toRadians(cameraPitch));
        direction.normalize();

        cameraPos.set(playerPos).add(direction.x, direction.y, direction.z);

        projectionMatrix.setPerspective((float) Math.toRadians(45.0f), (float) width / (float) height, 0.1f, 100.0f);
        viewMatrix.setLookAt(cameraPos, playerPos, new Vector3f(0.0f, 1.0f, 0.0f));

        glMatrixMode(GL_PROJECTION);
        glLoadMatrixf(projectionMatrix.get(BufferUtils.createFloatBuffer(16)));

        glMatrixMode(GL_MODELVIEW);
        glLoadMatrixf(viewMatrix.get(BufferUtils.createFloatBuffer(16)));
    }

    private void renderScene() {
        glColor3f(0.2f, 0.6f, 0.2f); // Green color for the ground
        glTranslatef(playerPos.x, playerPos.y, playerPos.z);
        glRotatef(playerRotation, 0.0f, 1.0f, 0.0f);

        // Render the player character (e.g., a cube)
        glBegin(GL_QUADS);
        glVertex3f(-0.5f, -0.5f, -0.5f);
        glVertex3f(-0.5f, 0.5f, -0.5f);
        glVertex3f(0.5f, 0.5f, -0.5f);
        glVertex3f(0.5f, -0.5f, -0.5f);

        glVertex3f(-0.5f, -0.5f, 0.5f);
        glVertex3f(-0.5f, 0.5f, 0.5f);
        glVertex3f(0.5f, 0.5f, 0.5f);
        glVertex3f(0.5f, -0.5f, 0.5f);

        glVertex3f(-0.5f, -0.5f, -0.5f);
        glVertex3f(-0.5f, -0.5f, 0.5f);
        glVertex3f(0.5f, -0.5f, 0.5f);
        glVertex3f(0.5f, -0.5f, -0.5f);

        glVertex3f(-0.5f, 0.5f, -0.5f);
        glVertex3f(-0.5f, 0.5f, 0.5f);
        glVertex3f(0.5f, 0.5f, 0.5f);
        glVertex3f(0.5f, 0.5f, -0.5f);

        glVertex3f(-0.5f, -0.5f, -0.5f);
        glVertex3f(-0.5f, 0.5f, -0.5f);
        glVertex3f(-0.5f, 0.5f, 0.5f);
        glVertex3f(-0.5f, -0.5f, 0.5f);

        glVertex3f(0.5f, -0.5f, -0.5f);
        glVertex3f(0.5f, 0.5f, -0.5f);
        glVertex3f(0.5f, 0.5f, 0.5f);
        glVertex3f(0.5f, -0.5f, 0.5f);
        glEnd();
    }

    public static void main(String[] args) {
        new Game().run();
    }
}