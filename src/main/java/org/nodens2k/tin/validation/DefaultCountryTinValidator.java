package org.nodens2k.tin.validation;

import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

public final class DefaultCountryTinValidator implements CountryTinValidator {

  private static final CountryTinValidator ALWAYS_TRUE = new DefaultCountryTinValidator(true);

  private static final CountryTinValidator ALWAYS_FALSE = new DefaultCountryTinValidator(false);

  private final boolean value;

  @Contract(pure = true)
  private DefaultCountryTinValidator(boolean value) {
    this.value = value;
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    return isValid(tin, TinType.ANY);
  }

  @Override
  public boolean isValid(String tin, TinType acceptedType) {
    return tin != null && value;
  }

  @Contract(value = "null, _ -> false; _, null -> false", pure = true)
  @Override
  public boolean isValid(String countryCode, String tin) {
    return isValid(countryCode, tin, TinType.ANY);
  }

  @Override
  public boolean isValid(String countryCode, String tin, TinType acceptedType) {
    return countryCode != null && tin != null && value;
  }

  @NotNull
  @Contract(pure = true)
  @Override
  @UnmodifiableView
  public Collection<String> getSupportedCountries() {
    return Collections.emptySet();
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isCountrySupported(String countryCode) {
    return countryCode != null;
  }

  @Contract(pure = true)
  public static CountryTinValidator getInstance(boolean defaultValidation) {
    return defaultValidation ? ALWAYS_TRUE : ALWAYS_FALSE;
  }
}
