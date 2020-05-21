package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class AustriaTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new AustriaTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{9}$");

  /**
   * Hidden constructor.
   */
  private AustriaTinValidator() {
    super("AT", "AUT", "040");
  }

  @Contract("null,_ -> false")
  @Override
  public boolean isValid(String tin, TinType acceptedType) {
    tin = sanitise(tin, "AT");
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 8; i++) {
      int tmp = (tin.charAt(i) - '0') * (1 + i % 2);
      sum += tmp / 10 + tmp % 10;
    }

    int checksum = (100 - sum) % 10;
    return tin.charAt(8) == '0' + checksum;
  }
}
