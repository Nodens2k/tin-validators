package org.nodens2k.tin.validation;

import java.util.Collection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Contract definition for all country-specific TIN validators.
 *
 * <p>For global validation, refer to {@link TinValidator}.
 */
public interface CountryTinValidator {

  /**
   * Determines if the specified TIN is a valid one.
   *
   * @param tin Tax identification number
   * @return {@code true} if the passed TIN is valid; {@code false} otherwise
   */
  @Contract(value = "null -> false", pure = true)
  boolean isValid(String tin);

  /**
   * Determines if the specified TIN is a valid one for the specified country.
   *
   * <p>This method allows the creation of multi-country validators, as well as
   * supporting different country code standards.
   *
   * @param countryCode Country code
   * @param tin Tax identification number
   * @return {@code true} if the passed TIN is valid; {@code false} otherwise
   */
  @Contract(value = "null,_ -> false; _,null -> false", pure = true)
  boolean isValid(String countryCode, String tin);

  /**
   * Gets all the countries supported by this validator instance.
   *
   * @return A Collection containing the codes of the supported countries
   */
  @NotNull
  @Contract(pure = true)
  @UnmodifiableView
  Collection<String> getSupportedCountries();

  /**
   * Gets if the specified country is supported by this validator.
   *
   * @param countryCode Country code
   * @return {@code true} if the country is supported; {@code false} otherwise
   */
  @Contract(value = "null -> false", pure = true)
  boolean isCountrySupported(String countryCode);
}
