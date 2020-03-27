package org.nodens2k.tin.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Tax Identification Number validation for several countries.
 */
public final class TinValidator implements CountryTinValidator {

  /**
   * Registered validators.
   */
  private static final Map<String, CountryTinValidator> VALIDATORS = new HashMap<>();

  /**
   * Supported countries.
   */
  private static final Set<String> COUNTRIES = new HashSet<>();

  static {
    register(AustriaTinValidator.INSTANCE);
    register(BelgiumTinValidator.INSTANCE);
    register(BulgariaTinValidator.INSTANCE);
    register(CroatiaTinValidator.INSTANCE);
    register(CyprusTinValidator.INSTANCE);
    register(CzechiaTinValidator.INSTANCE);
    register(DenmarkTinValidator.INSTANCE);
    register(EstoniaTinValidator.INSTANCE);
    register(FinlandTinValidator.INSTANCE);
    register(FranceTinValidator.INSTANCE);
    register(GermanyTinValidator.INSTANCE);
    register(GreeceTinValidator.INSTANCE);
    register(ItalyTinValidator.INSTANCE);
    register(PortugalTinValidator.INSTANCE);
    register(RomaniaTinValidator.INSTANCE);
    register(SpainTinValidator.INSTANCE);
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
  @Contract(pure = true)
  public TinValidator(boolean defaultValidation) {
    defaultValidator = DefaultCountryTinValidator.getInstance(defaultValidation);
  }

  /**
   * @param tin Tax identification number
   */
  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    return false;
  }

  @Contract("null, _ -> false; _, null -> false")
  @Override
  public boolean isValid(String countryCode, String tin) {
    return VALIDATORS.getOrDefault(countryCode, defaultValidator).isValid(tin);
  }

  @NotNull
  @Contract(pure = true)
  @Override
  @UnmodifiableView
  public Collection<String> getSupportedCountries() {
    return Collections.unmodifiableSet(COUNTRIES);
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isCountrySupported(String countryCode) {
    return COUNTRIES.contains(countryCode);
  }

  /**
   * Sets the validator for the specified countries.
   *
   * <p>This method provides the means for registering custom validators for additional
   * countries, as well as register existing validators for other country codes.
   *
   * @param validator The validator instance to register
   */
  public static void register(final CountryTinValidator validator) {
    if (validator != null) {
      for (String country : validator.getSupportedCountries()) {
        VALIDATORS.put(country, validator);
        COUNTRIES.add(country);
      }
    }
  }

  /**
   * Registers a validator for specific countries.
   *
   * @param validator Validator to register
   * @param countries Countries to associate to the validator
   */
  public static void register(final CountryTinValidator validator, String... countries) {
    if (validator != null) {
      for (String country : countries) {
        VALIDATORS.put(country, validator);
        COUNTRIES.add(country);
      }
    }
  }
}
