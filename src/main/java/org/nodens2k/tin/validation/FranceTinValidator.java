package org.nodens2k.tin.validation;

import java.util.regex.Pattern;

public final class FranceTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new FranceTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{13}$");

  private FranceTinValidator() {
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

    long value = parseLong(tin.substring(0, 10));
    long control = parseLong(tin.substring(10));

    long checksum = value % 511;
    return checksum == control;
  }
}
