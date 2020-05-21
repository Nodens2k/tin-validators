package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class ItalyTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new ItalyTinValidator();

  private static final Pattern CODE_REGEX = Pattern.compile("^[A-Z]{6}[0-9]{2}[ABCDEHLMPRST][0-9]{2}[A-Z][0-9]{3}[A-Z]$");

  private static final Pattern CARD_REGEX = Pattern.compile("^[0-9]{11}$");

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

  @Contract(value = "null,_ -> false", pure = true)
  @Override
  public boolean isValid(String tin, TinType acceptedType) {
    tin = sanitise(tin, "IT");

    if (tin != null) {
      if (acceptedType != TinType.PERSONAL && CODE_REGEX.matcher(tin).matches()) {
        return isValidCode(tin);
      }
      if (acceptedType != TinType.COMPANY && CARD_REGEX.matcher(tin).matches()) {
        return isValidCard(tin);
      }
    }
    return false;
  }

  /**
   * Validates a Fiscal code.
   *
   * @param tin Fiscal code to be validated
   * @return {@code true} if it is valid; {@code false} otherwise
   */
  private boolean isValidCode(@NotNull String tin) {
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

  /**
   * Validates an Identity Card number.
   *
   * @param tin Identity card number to validate
   * @return {@code true} if it is valid; {@code false} otherwise
   */
  private boolean isValidCard(@NotNull String tin) {
    if ("0000000".equals(tin.substring(0, 7))) {
      return false;
    }

    int suffix = Integer.parseInt(tin.substring(7, 10));
    if (suffix == 0 || (suffix > 100 && suffix != 120 && suffix != 121)) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 10; i++) {
      int tmp = (tin.charAt(i) - '0') * (i % 2 + 1);
      sum += tmp % 10 + tmp / 10;
    }
    sum = 10 - sum % 10;

    int control = tin.charAt(10) - '0';
    return sum % 10 == control;
  }
}
