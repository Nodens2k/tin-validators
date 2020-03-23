package org.nodens2k.tin.validation;

import java.util.HashMap;
import java.util.Map;

/**
 * Tax Identification Number validation for several countries.
 */
public class TinValidator {

  /**
   * Registered validators.
   */
  private static final Map<String, CountryTinValidator> VALIDATORS = new HashMap<>();

  static {
    VALIDATORS.put("AT", AustriaTinValidator.INSTANCE);
    VALIDATORS.put("BE", BelgiumTinValidator.INSTANCE);
    VALIDATORS.put("BG", BulgariaTinValidator.INSTANCE);
    VALIDATORS.put("ES", SpainTinValidator.INSTANCE);
    VALIDATORS.put("FR", FranceTinValidator.INSTANCE);
    VALIDATORS.put("IT", ItalyTinValidator.INSTANCE);
    VALIDATORS.put("PT", PortugalTinValidator.INSTANCE);
    VALIDATORS.put("RO", RomaniaTinValidator.INSTANCE);
  }

  /**
   * Validator to be used for unsupported countries.
   */
  private final CountryTinValidator defaultValidator;

  /**
   * Creates a new instance.
   *
   * @param defaultValidation What the validation result must be for unsupported countries
   */
  public TinValidator(boolean defaultValidation) {
    defaultValidator = DefaultCountryTinValidator.getInstance(defaultValidation);
  }

  /**
   * Validates a TIN for the specified country.
   *
   * @param countryCode ISO 3166-2 country code
   * @param tin TIN to validate
   * @return {@code true} if the TIN is valid; {@code false} otherwise
   */
  public boolean isValid(String countryCode, String tin) {
    return VALIDATORS.getOrDefault(countryCode, defaultValidator).isValid(tin);
  }

  /**
   * Sets the validator for the specified countries.
   *
   * <p>This method provides the means for registering custom validators for additional
   * countries, as well as register existing validators for other country codes.
   *
   * @param validator The validator instance to register
   * @param countries One or more country codes
   */
  public static void register(CountryTinValidator validator, String... countries) {
    if (validator != null) {
      for (String country : countries) {
        VALIDATORS.put(country, validator);
      }
    }
  }
}
