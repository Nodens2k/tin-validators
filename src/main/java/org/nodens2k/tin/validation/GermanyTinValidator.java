package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class GermanyTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new GermanyTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[1-9][0-9]{10}$");

  /**
   * Hidden constructor.
   */
  private GermanyTinValidator() {
    super("DE", "DEU", "276");
  }

  @Contract(value = "null,_ -> false", pure = true)
  @Override
  public boolean isValid(String tin, TinType acceptedType) {
    tin = sanitise(tin, "DE");
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    int sum = 0;

    for (int i = 0; i < 10; i++) {
      int c = tin.charAt(i) - '0';
      int tmp = (sum + c) % 10;
      sum = (tmp == 0) ? 9 : (2 * tmp) % 11;
    }

    int checksum = (11 - sum) % 10;
    return checksum == tin.charAt(10) - '0';
  }
}
