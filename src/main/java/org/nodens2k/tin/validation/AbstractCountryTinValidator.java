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

  private final Set<String> countries;

  protected AbstractCountryTinValidator(@NotNull String... countries) {
    assert countries.length > 0 : "No languages supported?";
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
   * Removes any symbol character in the incoming TIN number.
   *
   * @param tin TIN number to sanitise
   * @return Sanitised TIN number
   */
  @Contract(value = "null -> null; !null -> !null", pure = true)
  protected final String sanitise(String tin) {
    return tin == null ? null : INVALID_CHARS.matcher(tin).replaceAll("").toUpperCase(Locale.ROOT);
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
}
