package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class CroatiaTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new CroatiaTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{11}$");

  private CroatiaTinValidator() {
    super("HR", "HRV", "191");
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    tin = sanitise(tin, "HR");
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    int sum = 10;

    for (int i = 0; i < 10; i++) {
      int c = tin.charAt(i) - '0';
      int subtotal = (sum + c) % 10;
      subtotal = (subtotal == 0) ? 10 : subtotal;
      sum = (2 * subtotal) % 11;
    }

    int checksum = sum == 1 ? 0 : 11 - sum;
    int control = tin.charAt(10) - '0';
    return control == checksum;
  }
}
