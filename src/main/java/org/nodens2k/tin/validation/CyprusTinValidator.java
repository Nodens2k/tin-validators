package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class CyprusTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new CyprusTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{8}[A-Z]$");

  private static final int[][] MAP = {
      {1, 0, 5, 7, 9, 13, 15, 17, 19, 21},
      {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
  };

  private CyprusTinValidator() {
    super("CY", "CYP", "196");
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 8; i++) {
      int c = tin.charAt(i) - '0';
      sum += MAP[i % 2][c];
    }

    int checksum = sum % 26;
    int control = tin.charAt(8) - 'A';
    return checksum == control;
  }
}
