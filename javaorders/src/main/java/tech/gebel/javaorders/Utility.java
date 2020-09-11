package tech.gebel.javaorders;

public final class Utility {

  private Utility() {}

  public static Object optionallyReplace(Object receiver, Object provider) {
    if (provider == null) return receiver; else return provider;
  }

  public static long optionallyReplace(long receiver, long provider) {
    if (provider == 0) return receiver; else return provider;
  }

  public static double optionallyReplace(double receiver, double provider) {
    if (provider == 0.0d) return receiver; else return provider;
  }
}
