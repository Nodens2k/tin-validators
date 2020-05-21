package org.nodens2k.tin.validation;

import java.util.Map;

/**
 * Factory for {@link CountryTinValidator} instances.
 *
 * <p>Usage example:
 *
 * <pre>
 *   TinValidatorFactory factory = DefaultTinValidatorFactory.INSTANCE;
 *   boolean valid = factory.getValidatorFor("ES").isValid("72431799S");
 * </pre>
 *
 * <p>Any implementation of this interface SHOULD be thread-safe and immutable.
 *
 * @see DefaultTinValidatorFactory
 */
public interface TinValidatorFactory {

  /**
   * Gets a copy of the configured mappings.
   *
   * <p>This method may return an incomplete map (for example, if validators were lazily
   * discovered on-demand).
   *
   * <p>This method is intended to be used by {@link Builder#withAllFrom(TinValidatorFactory)}, and
   * should not to be usually called by user code.
   *
   * @return A Map of country codes to validators
   */
  Map<String, CountryTinValidator> getAllMappings();

  /**
   * Gets a validator instance for the specified country code.
   *
   * @param countryCode Country code
   * @return A {@link CountryTinValidator} instance supporting the specified country code, or null
   */
  CountryTinValidator getValidatorFor(String countryCode);

  /**
   * Gets a {@link Builder} instance pre-configured as this factory.
   *
   * @return A pre-configured builder
   */
  Builder toBuilder();

  /**
   * Factory builder specification.
   */
  interface Builder {

    /**
     * Builds the factory.
     *
     * @return The factory
     */
    DefaultTinValidatorFactory build();

    /**
     * Maps the specified validator to all its supported country codes.
     *
     * <p>Previous mappings to the country codes supported by the validator are overwritten.
     *
     * @param validator Validator instance
     * @return This builder
     */
    Builder with(CountryTinValidator validator);

    /**
     * Maps the specified validator to the specified list of country codes.
     *
     * <p>Previous mappings to the specified country codes are overwritten.
     *
     * @param validator Validator instance
     * @param countryCodes Country codes to map validator to
     * @return This builder
     */
    Builder with(CountryTinValidator validator, String... countryCodes);

    /**
     * Copies all validator mappings from the specified factory.
     *
     * @param factory Factory instance to copy validators from
     * @return This builder
     */
    Builder withAllFrom(TinValidatorFactory factory);

    /**
     * Specified the validator to be returned if there is no mapping for a given country code.
     *
     * @param validator Validator to be returned by default
     * @return This builder
     */
    Builder withDefault(CountryTinValidator validator);
  }
}
