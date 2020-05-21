package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class CanadaTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new CanadaTinValidator();

  private static final Pattern REGEX = Pattern.compile("^[0-9]{9}$");

  private CanadaTinValidator() {
    super("CA", "CAN", "124");
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    tin = sanitise(tin, "CA");
    if (tin == null || !REGEX.matcher(tin).matches()) {
      return false;
    }

    return luhn(tin);
  }
}
