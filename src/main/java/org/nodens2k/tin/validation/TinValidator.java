package org.nodens2k.tin.validation;

import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Tax Identification Number validation for several countries.
 *
 * <p>This class is thread-safe, but not immutable. Registering any extra validator, affects all
 * instances of this class.
 *
 * <p>For an immutable alternative to this class, it is better to use {@link DefaultTinValidatorFactory}.
 *
 * <p>Example of usage:
 *
 * <pre>
 *   TinValidator validator = new TinValidator(true);
 *   boolean valid = validator.isValid("ES", "12371896N");
 * </pre>
 */
public final class TinValidator implements CountryTinValidator {

  private static TinValidatorFactory globalFactory = DefaultTinValidatorFactory.INSTANCE;

  private final boolean defaultValidation;

  /**
   * Creates a new instance.
   *
   * @param defaultValidation What the validation result must be for unsupported countries
   */
  @Contract(pure = true)
  public TinValidator(boolean defaultValidation) {
    this.defaultValidation = defaultValidation;
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    return false;
  }

  @Contract(value = "null,_ -> false; _,null -> false", pure = true)
  @Override
  public boolean isValid(String tin, TinType acceptedType) {
    return false;
  }

  @Contract("null, _ -> false; _, null -> false")
  @Override
  public boolean isValid(String countryCode, String tin) {
    return isValid(countryCode, tin, TinType.ANY);
  }

  @Contract("null,_,_ -> false; _,null,_ -> false")
  @Override
  public boolean isValid(String countryCode, String tin, TinType acceptedType) {
    CountryTinValidator validator = globalFactory.getValidatorFor(countryCode);
    return (validator == null ? DefaultCountryTinValidator.getInstance(defaultValidation) : validator).isValid(tin, acceptedType);
  }

  @NotNull
  @Contract(pure = true)
  @Override
  @UnmodifiableView
  public Collection<String> getSupportedCountries() {
    return Collections.unmodifiableSet(globalFactory.getAllMappings().keySet());
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isCountrySupported(String countryCode) {
    return globalFactory.getAllMappings().containsKey(countryCode);
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
    globalFactory = globalFactory.toBuilder().with(validator).build();
  }

  /**
   * Registers a validator for specific countries.
   *
   * @param validator Validator to register
   * @param countries Countries to associate to the validator
   */
  public static void register(final CountryTinValidator validator, String... countries) {
    globalFactory = globalFactory.toBuilder().with(validator, countries).build();
  }
}
