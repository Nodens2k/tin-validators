package org.nodens2k.tin.validation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Default {@link TinValidatorFactory} implementation.
 *
 * <p>This implementation registers pre-existing validator instances, and does not perform any
 * {@link CountryTinValidator} implementation discovery. This is on purpose, in order to keep this library free from external dependencies.
 *
 * <p>If automatic discovery of other implementations is needed, it should be easy to develop
 * a custom implementation based on Spring Framework, Guava, Reflections or any other library with class scanning features.
 *
 * <p>Instances of this class can be constructed through its {@link #builder()} static method,
 * or by getting a builder from a pre-existing instance, like this:
 *
 * <pre>
 *   // Custom factory built from scratch
 *   TinValidatorFactory factory = DefaultTinValidatorFactory.builder()
 *       .with(SpainTinValidator.INSTANCE)
 *       .withDefault(DefaultCountryTinValidator.getInstance(true))
 *       .build();
 *
 *   // Custom factory based on the default instance
 *   TinValidatorFactory factory = DefaultTinValidatorFactory.INSTANCE.toBuilder()
 *       .withDefault(DefaultCountryTinValidator.getInstance(true))
 *       .build();
 * </pre>
 *
 * <p>Instances of this class are immutable and thread-safe.
 */
public final class DefaultTinValidatorFactory implements TinValidatorFactory {

  /**
   * Singleton factory instance with all implemented validators already mapped to their supported country codes.
   */
  public static final TinValidatorFactory INSTANCE = DefaultTinValidatorFactory.builder()
      .with(AustriaTinValidator.INSTANCE)
      .with(BelgiumTinValidator.INSTANCE)
      .with(BulgariaTinValidator.INSTANCE)
      .with(CanadaTinValidator.INSTANCE)
      .with(CroatiaTinValidator.INSTANCE)
      .with(CyprusTinValidator.INSTANCE)
      .with(CzechiaTinValidator.INSTANCE)
      .with(DenmarkTinValidator.INSTANCE)
      .with(EstoniaTinValidator.INSTANCE)
      .with(FinlandTinValidator.INSTANCE)
      .with(FranceTinValidator.INSTANCE)
      .with(GermanyTinValidator.INSTANCE)
      .with(GreeceTinValidator.INSTANCE)
      .with(ItalyTinValidator.INSTANCE)
      .with(PortugalTinValidator.INSTANCE)
      .with(RomaniaTinValidator.INSTANCE)
      .with(SpainTinValidator.INSTANCE)
      .with(UkTinValidator.INSTANCE)
      .build();

  private final Map<String, CountryTinValidator> validators;

  private final CountryTinValidator defaultValidator;

  /**
   * Hidden constructor.
   *
   * @param validators Map of countries to validators
   * @param defaultValidator Default validator if no mapping is defined
   */
  private DefaultTinValidatorFactory(Map<String, CountryTinValidator> validators, CountryTinValidator defaultValidator) {
    this.validators = validators;
    this.defaultValidator = defaultValidator;
  }

  @Override
  public Map<String, CountryTinValidator> getAllMappings() {
    return Collections.unmodifiableMap(validators);
  }

  @Override
  public CountryTinValidator getValidatorFor(String countryCode) {
    return validators.getOrDefault(countryCode, defaultValidator);
  }

  @Override
  public TinValidatorFactory.Builder toBuilder() {
    return builder().withAllFrom(this).withDefault(defaultValidator);
  }

  public static TinValidatorFactory.Builder builder() {
    return new DefaultTinValidatorFactory.DefaultBuilder();
  }

  /**
   * Implementation of the Builder interface.
   */
  private static final class DefaultBuilder implements TinValidatorFactory.Builder {

    /**
     * Map of validators for the factory being built.
     */
    private final Map<String, CountryTinValidator> validators = new HashMap<>();

    /**
     * Default validator to be returned if there is no mapping for a country code.
     */
    private CountryTinValidator defaultValidator;

    /**
     * Hidden constructor.
     */
    private DefaultBuilder() {
    }

    @Override
    public DefaultTinValidatorFactory build() {
      return new DefaultTinValidatorFactory(validators, defaultValidator);
    }

    @Override
    public Builder withAllFrom(TinValidatorFactory factory) {
      if (factory != null) {
        validators.putAll(factory.getAllMappings());
      }
      return this;
    }

    @Override
    public Builder with(CountryTinValidator validator) {
      if (validator != null) {
        for (String country : validator.getSupportedCountries()) {
          validators.put(country, validator);
        }
      }
      return this;
    }

    @Override
    public Builder with(CountryTinValidator validator, String... countryCodes) {
      if (validator != null) {
        for (String code : countryCodes) {
          validators.put(code, validator);
        }
      }
      return this;
    }

    @Override
    public Builder withDefault(CountryTinValidator validator) {
      defaultValidator = validator;
      return this;
    }
  }
}
