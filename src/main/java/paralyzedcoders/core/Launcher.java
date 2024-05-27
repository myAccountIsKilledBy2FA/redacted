package paralyzedcoders.core;


import paralyzedcoders.core.utils.Constants;


public class Launcher {

  private static WindowManager window;

  public static void main(String[] args){
    window = new WindowManager(Constants.TITLE,1600,900,false);
    window.init();
    while (!window.isClosed()) {
      window.update();
    }
    window.cleanup();
    System.out.println("exiting...");
  }

  public static WindowManager getWindow() {
    return window;
  }

}
