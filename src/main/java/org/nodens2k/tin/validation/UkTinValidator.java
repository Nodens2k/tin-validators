package org.nodens2k.tin.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UkTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new UkTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^([0-9]{10})|([A-CEF-HJ-PW-Z][A-CEF-HJ-NPW-Z][0-9]{6}[ABCD]?)$");

  private static final Set<String> INVALID_PREFIXES = new HashSet<>(Arrays.asList("GB", "NK", "TN", "ZZ"));

  private UkTinValidator() {
    super("GB", "GBR", "826");
  }

  @Override
  public boolean isValid(String tin) {
    tin = sanitise(tin, "GB");
    if (tin == null) {
      return false;
    }

    Matcher m = TIN_REGEX.matcher(tin);
    if (m.matches()) {
      if (m.group(1) != null) {
        return true;
      }

      if (m.group(2) != null) {
        String prefix = m.group(2).substring(0, 2);
        return !INVALID_PREFIXES.contains(prefix);
      }
    }
    return false;
  }
}
