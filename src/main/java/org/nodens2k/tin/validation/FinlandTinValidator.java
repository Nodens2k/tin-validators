package org.nodens2k.tin.validation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

public final class FinlandTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new FinlandTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{6}[A+-][0-9]{3}[0-9A-Z]$");

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("ddMMuu").withResolverStyle(ResolverStyle.STRICT);

  private static final String MAP = "0123456789ABCDEFHJKLMNPRSTUVWXY";

  private FinlandTinValidator() {
    super(Pattern.compile("[^0-9A-Za-z+-]"), "FI", "FIN", "246");
  }

  @Override
  public boolean isValid(String tin, TinType acceptedType) {
    tin = sanitise(tin, "FI");
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    String token1 = tin.substring(0, 6);
    String token2 = tin.substring(7, 10);

    try {
      FORMATTER.parse(token1);
    } catch (DateTimeParseException e) {
      return false;
    }

    int checksum = parseInt(token1 + token2) % 31;
    char control = tin.charAt(10);
    return MAP.charAt(checksum) == control;
  }
}
