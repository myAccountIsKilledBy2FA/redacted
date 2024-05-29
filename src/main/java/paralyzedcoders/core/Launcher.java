package paralyzedcoders.core;


import paralyzedcoders.core.utils.Constants;
import paralyzedcoders.test.RedactedGame;
import paralyzedcoders.test.RedactedGame;


public class Launcher {
  private static WindowManager window;
  private static RedactedGame game;

  public static void main(String[] args){
    window = new WindowManager(Constants.TITLE,1600,900,false);
    game = new RedactedGame();
    EngineManager engine = new EngineManager();
    try {
      engine.start();
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  public static WindowManager getWindow() {
    return window;
  }
  public static RedactedGame getGame() {
    return game;
  }
}
