package org.nodens2k.tin.validation;

public final class DefaultCountryTinValidator implements CountryTinValidator {

  private static final CountryTinValidator ALWAYS_TRUE = new DefaultCountryTinValidator(true);

  private static final CountryTinValidator ALWAYS_FALSE = new DefaultCountryTinValidator(false);

  private final boolean value;

  private DefaultCountryTinValidator(boolean value) {
    this.value = value;
  }

  @Override
  public boolean isValid(String tin) {
    return value;
  }

  public static CountryTinValidator getInstance(boolean defaultValidation) {
    return defaultValidation ? ALWAYS_TRUE : ALWAYS_FALSE;
  }
}
