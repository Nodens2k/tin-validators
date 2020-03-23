package org.nodens2k.tin.validation;

import java.util.regex.Pattern;

public final class BelgiumTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new BelgiumTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{11}$");

  private BelgiumTinValidator() {
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

    int value1 = parseInt(tin.substring(0, 9));
    int value2 = 2000000000 + value1;
    int control = parseInt(tin.substring(9));

    int checksum1 = 97 - value1 % 97;
    int checksum2 = 97 - value2 % 97;
    return checksum1 == control || checksum2 == control;
  }
}
