package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class EstoniaTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new EstoniaTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{11}$");

  private static final int[] WEIGHTS_1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};

  private static final int[] WEIGHTS_2 = {3, 4, 5, 6, 7, 8, 9, 1, 2, 3};

  private EstoniaTinValidator() {
    super("EE", "EST", "233");
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    tin = sanitise(tin);
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    int check = checksum(tin, WEIGHTS_1);
    if (check == 10) {
      check = checksum(tin, WEIGHTS_2);
      if (check == 10) {
        check = 0;
      }
    }

    int control = tin.charAt(10) - '0';
    return control == check;
  }

  @Contract(pure = true)
  private int checksum(String tin, int[] weights) {
    int sum = 0;
    for (int i = 0; i < 10; i++) {
      sum += (tin.charAt(i) - '0') * weights[i];
    }

    return sum % 11;
  }
}
