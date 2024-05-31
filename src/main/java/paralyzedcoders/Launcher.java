package paralyzedcoders;

import paralyzedcoders.core.ILogic;
import paralyzedcoders.core.WindowManager;
import paralyzedcoders.engine.GameEngine;
import paralyzedcoders.engine.IGameLogic;
import paralyzedcoders.engine.Window;
import paralyzedcoders.game.DummyGame;

public class Launcher {
	
	
	private static IGameLogic gameLogic;
	
	
	

  public static void main(String[] args) {
    try {
        boolean vSync = true;
        gameLogic = new DummyGame();
        Window.WindowOptions opts = new Window.WindowOptions();
        opts.cullFace = false;
        opts.showFps = true;
        opts.compatibleProfile = true;
        opts.antialiasing = true;
        opts.frustumCulling = false;
        GameEngine gameEng = new GameEngine("GAME", vSync, opts, gameLogic);
        gameEng.run();
        
    } catch (Exception excp) {
        excp.printStackTrace();
        System.exit(-1);
    }
  }
  
  public static ILogic getGame() {
	  return (ILogic) gameLogic;
  }
  
  public static WindowManager getWindow() {
	  return null;
  }
  
  
}
