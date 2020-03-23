package org.nodens2k.tin.validation;

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
  boolean isValid(String tin);
}
