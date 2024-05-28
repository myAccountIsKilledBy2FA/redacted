package paralyzedcoders.core;


import paralyzedcoders.core.utils.Constants;


public class Launcher {
  private static EngineManager engine;


  public static void main(String[] args){
    window = new WindowManager(Constants.TITLE,1600,900,false);
    engine = new EngineManager();
    try {
      engine.start();
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  // public static WindowManager getWindow() {
  //   return window;
  // }

}
