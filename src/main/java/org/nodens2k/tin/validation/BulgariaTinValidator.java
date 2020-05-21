package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class BulgariaTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new BulgariaTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{10}$");

  private static final int[] WEIGHT = {2, 4, 8, 5, 10, 9, 7, 3, 6};

  private BulgariaTinValidator() {
    super("BG", "BGR", "100");
  }

  @Contract(value = "null,_ -> false", pure = true)
  @Override
  public boolean isValid(String tin, TinType acceptedType) {
    tin = sanitise(tin, "BG");
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 9; i++) {
      sum += (tin.charAt(i) - '0') * WEIGHT[i];
    }

    int checksum = (sum % 11) % 10;
    int control = tin.charAt(9) - '0';

    return control == checksum;
  }
}
