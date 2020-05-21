package org.nodens2k.tin.validation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

public abstract class AbstractCountryTinValidator implements CountryTinValidator {

  private static final Pattern INVALID_CHARS = Pattern.compile("[^0-9A-Za-z]+");

  private final Pattern invalidChars;

  private final Set<String> countries;

  protected AbstractCountryTinValidator(@NotNull String... countries) {
    this(INVALID_CHARS, countries);
  }

  protected AbstractCountryTinValidator(Pattern invalidChars, @NotNull String... countries) {
    assert countries.length > 0 : "No languages supported?";
    this.invalidChars = invalidChars;
    this.countries = new HashSet<>(Arrays.asList(countries));
  }

  @Contract(pure = true)
  @NotNull
  @Override
  @UnmodifiableView
  public final Collection<String> getSupportedCountries() {
    return Collections.unmodifiableSet(countries);
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public final boolean isCountrySupported(String countryCode) {
    return countries.contains(countryCode);
  }

  @Contract(value = "null, _ -> false; _, null -> false", pure = true)
  @Override
  public final boolean isValid(String countryCode, String tin) {
    return countries.contains(countryCode) && isValid(tin);
  }

  /**
   * Removes the optional country code from the beginning of the passed TIN number and sanitises it.
   *
   * <p>This optional country code is usually present in TIN numbers of the European Union, when
   * used outside their origin countries.
   *
   * @param tin TIN number to sanitise
   * @param countryCode Country code to remove from the TIN number if present
   * @return Sanitised TIN number
   */
  @Contract(value = "null -> null; !null -> !null", pure = true)
  protected final String sanitise(String tin, String countryCode) {
    String sanitised = tin == null ? null : invalidChars.matcher(tin).replaceAll("").toUpperCase(Locale.ROOT);
    if (tin != null && sanitised.startsWith(countryCode)) {
      sanitised = sanitised.substring(2);
    }
    return sanitised;
  }

  /**
   * Fast parse of numbers in decimal base.
   *
   * @param s String containing the number to parse
   * @return The parsed number
   */
  @Contract(pure = true)
  protected final int parseInt(@NotNull String s) {
    final char[] chars = s.toCharArray();
    int value = 0;
    for (char c : chars) {
      value = 10 * value + (c - '0');
    }
    return value;
  }

  /**
   * Fast parse of numbers in decimal base.
   *
   * @param s String containing the number to parse
   * @return The parsed number
   */
  @Contract(pure = true)
  protected final long parseLong(@NotNull final String s) {
    final char[] chars = s.toCharArray();
    long value = 0;
    for (char c : chars) {
      value = 10L * value + (c - '0');
    }
    return value;
  }

  /**
   * Performs the Luhn algorithm on the specified string.
   *
   * <p>This method assumes that the passed string only contains numeric characters.
   *
   * @param s The string to validate
   * @return {@code true} if the number passes the Luhn test; {@code false} otherwise
   */
  protected final boolean luhn(final String s) {
    int sum = 0;
    for (int i = 0; i < s.length(); i++) {
      int digit = s.charAt(i) - '0';
      int tmp = digit * (i % 2 + 1);
      sum += tmp / 10 + tmp % 10;
    }

    return sum % 10 == 0;
  }
}
