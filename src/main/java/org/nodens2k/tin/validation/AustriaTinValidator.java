package org.nodens2k.tin.validation;

import java.util.regex.Pattern;

public final class AustriaTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new AustriaTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{9}$");

  /**
   * Hidden constructor.
   */
  private AustriaTinValidator() {
  }

  @Override
  public boolean isValid(String tin) {
    if (tin == null) {
      return false;
    }

    tin = sanitise(tin);

    if (!TIN_REGEX.matcher(tin).matches()) {
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
