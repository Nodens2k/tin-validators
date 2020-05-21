package org.nodens2k.tin.validation;

import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class GreeceTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new GreeceTinValidator();

  private static final Pattern TIN_REGEX = Pattern.compile("^[0-9]{9}$");

  private GreeceTinValidator() {
    super("GR", "GRC", "300");
  }

  @Contract(value = "null,_ -> false", pure = true)
  @Override
  public boolean isValid(String tin, TinType acceptedType) {
    tin = sanitise(tin, "GR");
    return tin != null && TIN_REGEX.matcher(tin).matches();
  }
}
