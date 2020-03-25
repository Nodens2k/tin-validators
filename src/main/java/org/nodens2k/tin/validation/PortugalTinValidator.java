package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class PortugalTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new PortugalTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^(:?[123568][0-9]{8})|(:?(:?45|7[0124579]|9[0189])[0-9]{7})$");

  /**
   * Hidden constructor.
   */
  private PortugalTinValidator() {
    super("PT", "PRT", "620");
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    tin = sanitise(tin);
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    int total = 0;
    for (int i = 0; i < 8; i++) {
      total += (tin.charAt(i) - '0') * (9 - i);
    }

    int mod11 = total % 11;
    char control = (char) ('0' + (mod11 < 2 ? 0 : 11 - mod11));
    return control == tin.charAt(8);
  }
}
