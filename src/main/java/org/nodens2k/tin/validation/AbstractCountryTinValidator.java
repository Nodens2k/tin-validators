package org.nodens2k.tin.validation;

import java.util.Locale;
import java.util.regex.Pattern;

public abstract class AbstractCountryTinValidator implements CountryTinValidator {

  private static final Pattern INVALID_CHARS = Pattern.compile("[^0-9A-Za-z]+");

  /**
   * Removes any symbol character in the incoming TIN number.
   *
   * @param tin TIN number to sanitise
   * @return Sanitised TIN number
   */
  protected final String sanitise(String tin) {
    return INVALID_CHARS.matcher(tin).replaceAll("").toUpperCase(Locale.UK);
  }

  /**
   * Fast parse of numbers in decimal base.
   *
   * @param s String containing the number to parse
   * @return The parsed number
   */
  protected final int parseInt(String s) {
    final char[] chars = s.toCharArray();
    int value = 0;
    for (char c : chars) {
      value = 10 * value + (c - '0');
    }
    return value;
  }

  /**
   * Fast parse of numbers in decimal base.
   *
   * @param s String containing the number to parse
   * @return The parsed number
   */
  protected final long parseLong(String s) {
    final char[] chars = s.toCharArray();
    long value = 0;
    for (char c : chars) {
      value = 10L * value + (c - '0');
    }
    return value;
  }
}
