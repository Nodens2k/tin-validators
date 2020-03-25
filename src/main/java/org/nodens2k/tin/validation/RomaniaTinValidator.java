package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class RomaniaTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new RomaniaTinValidator();

  private static final Pattern CNP_REGEX = Pattern.compile("^[0-9]{13}$");

  private static final int[] WEIGHTS = {2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9};

  /**
   * Hidden constructor.
   */
  private RomaniaTinValidator() {
    super("RO", "ROU", "642");
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    tin = sanitise(tin);
    if (tin == null || !CNP_REGEX.matcher(tin).matches()) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 12; i++) {
      sum += (tin.charAt(i) - '0') * WEIGHTS[i];
    }

    int checksum = sum % 11;
    if (checksum == 10) {
      checksum = 1;
    }
    return (tin.charAt(12) - '0') == checksum;
  }
}
