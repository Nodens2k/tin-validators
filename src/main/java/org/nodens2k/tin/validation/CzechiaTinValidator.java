package org.nodens2k.tin.validation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class CzechiaTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new CzechiaTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{9,10}$");

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuMMdd").withResolverStyle(ResolverStyle.STRICT);

  private CzechiaTinValidator() {
    super("CZ", "CZE", "203");
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    tin = sanitise(tin);
    if (tin == null || !TIN_REGEX.matcher(tin).matches()) {
      return false;
    }

    String date = tin.substring(0, 6);
    try {
      FORMATTER.parse(date);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }
}
