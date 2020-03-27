package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class ItalyTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new ItalyTinValidator();

  private static final Pattern CODE_REGEX = Pattern.compile("^[A-Z]{6}[0-9]{2}[ABCDEHLMPRST][0-9]{2}[A-Z][0-9]{3}[A-Z]$");

  private static final int[][] NUMBER_MAP = {
      {1, 0, 5, 7, 9, 13, 15, 17, 19, 21},
      {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
  };

  private static final int[][] CHARACTER_MAP = {
      {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23},
      {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25}
  };

  /**
   * Hidden constructor.
   */
  private ItalyTinValidator() {
    super("IT", "ITA", "380");
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    tin = sanitise(tin);

    if (tin == null || !CODE_REGEX.matcher(tin).matches()) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 15; i++) {
      int c = tin.charAt(i);
      int[][] map;
      if (c >= '0' && c <= '9') {
        map = NUMBER_MAP;
        c = c - '0';
      } else {
        map = CHARACTER_MAP;
        c = c - 'A';
      }
      sum += map[i % 2][c];
    }

    char control = (char) ((sum % 26) + 'A');
    return tin.charAt(15) == control;
  }
}
