package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class FranceTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new FranceTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{13}$");

  private FranceTinValidator() {
    super("FR", "FRA", "250");
  }

  @Contract(value = "null,_ -> false", pure = true)
  @Override
  public boolean isValid(String tin, TinType acceptedType) {
    tin = sanitise(tin, "FR");
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    long value = parseLong(tin.substring(0, 10));
    long control = parseLong(tin.substring(10));

    long checksum = value % 511;
    return checksum == control;
  }
}
