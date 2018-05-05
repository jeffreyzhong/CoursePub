package edu.brown.cs.termproject.autocorrect;

/**
 * ACControl Class: This class handles all of the on/off switches for all of the
 * suggestion options.
 *
 * @author Jeffreyzhong
 */
public class ACControl {

  private boolean prefixOn = false;
  private boolean whiteSpaceOn = false;
  private int levenshteinDist = 0;
  private boolean smartOn = false;

  /**
   * Turn prefix on.
   *
   * @return boolean
   */
  public boolean prefixOn() {
    return prefixOn;
  }

  /**
   * Get whitespace switch value.
   *
   * @return boolean
   */
  public boolean whiteSpaceOn() {
    return whiteSpaceOn;
  }

  /**
   * Get levenshtein edit distance. .
   *
   * @return boolean
   */
  public int levenshteinDist() {
    return levenshteinDist;
  }

  /**
   * Get smart ranking switch value.
   *
   * @return boolean
   */
  public boolean smartOn() {
    return smartOn;
  }

  /**
   * Turn prefix on.
   *
   * @param status
   *          On or off
   */
  public void setPrefix(boolean status) {
    prefixOn = status;
  }

  /**
   * Turn whitespace on/off.
   *
   * @param status
   *          On or off
   */
  public void setWhiteSpace(boolean status) {
    whiteSpaceOn = status;
  }

  /**
   * Set levenshtein edit distance.
   *
   * @param distance
   *          Distance to set
   */
  public void setLevenshtein(int distance) {
    levenshteinDist = distance;
  }

  /**
   * Set smart on/off.
   *
   * @param status
   *          on or off
   */
  public void setSmart(boolean status) {
    smartOn = status;
  }

}
