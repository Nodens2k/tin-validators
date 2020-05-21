package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class BelgiumTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new BelgiumTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{11}$");

  private BelgiumTinValidator() {
    super("BE", "BEL", "056");
  }

  @Contract(value = "null,_ -> false", pure = true)
  @Override
  public boolean isValid(String tin, TinType acceptedType) {
    tin = sanitise(tin, "BE");
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
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
