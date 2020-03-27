package org.nodens2k.tin.validation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

public final class DenmarkTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new DenmarkTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{10}$");

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("ddMMuu").withResolverStyle(ResolverStyle.STRICT);

  private static final int[] WEIGHTS = {4, 3, 2, 7, 6, 5, 4, 3, 2};

  private DenmarkTinValidator() {
    super("DK", "DNK", "208");
  }

  @Override
  public boolean isValid(String tin) {
    tin = sanitise(tin);
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 9; i++) {
      sum += (tin.charAt(i) - '0') * WEIGHTS[i];
    }

    int checksum = sum % 11;
    if (checksum == 1) {
      return false;
    }

    checksum = checksum == 0 ? 0 : 11 - checksum;
    int control = tin.charAt(9) - '0';
    if (checksum != control) {
      return false;
    }

    try {
      FORMATTER.parse(tin.substring(0, 6));
    } catch (DateTimeParseException e) {
      return false;
    }

    int year = parseInt(tin.substring(4, 6));
    if (year >= 37 && year <= 57) {
      int serial = parseInt(tin.substring(6));
      return serial <= 5000 || serial >= 8999;
    }
    return true;
  }
}
