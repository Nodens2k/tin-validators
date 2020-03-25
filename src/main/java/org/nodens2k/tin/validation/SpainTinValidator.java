package org.nodens2k.tin.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;

public final class SpainTinValidator extends AbstractCountryTinValidator {

  public static final CountryTinValidator INSTANCE = new SpainTinValidator();

  private static final Pattern CIF_REGEX = Pattern.compile("^([ABCDEFGHJNPQRSUVW])([0-9]{7})([A-J0-9])$");

  private static final Pattern NIF_REGEX = Pattern.compile("^([0-9KLMXYZ])([0-9]{7,8})([TRWAGMYFPDXBNJZSQVHLCKE])$");

  private static final char[] NIF_DIGITS = "TRWAGMYFPDXBNJZSQVHLCKE".toCharArray();

  private static final char[][] CIF_DIGITS = {"0123456789".toCharArray(), "JABCDEFGHI".toCharArray()};

  /**
   * Hidden constructor.
   */
  private SpainTinValidator() {
    super("ES", "ESP", "724");
  }

  @Contract(value = "null -> false", pure = true)
  @Override
  public boolean isValid(String tin) {
    if (tin == null) {
      return false;
    }

    String sanitisedTin = sanitise(tin);
    switch (sanitisedTin.charAt(0)) {
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
      case 'K':
      case 'L':
      case 'M':
      case 'X':
      case 'Y':
      case 'Z':
        return isValidNif(sanitisedTin);
      case 'A':
      case 'B':
      case 'C':
      case 'D':
      case 'E':
      case 'F':
      case 'G':
      case 'H':
      case 'J':
      case 'P':
      case 'Q':
      case 'R':
      case 'S':
      case 'U':
      case 'V':
      case 'N':
      case 'W':
        return isValidCif(sanitisedTin);
      default:
        return false;
    }
  }

  private boolean isValidCif(String nif) {
    Matcher m = CIF_REGEX.matcher(nif);
    if (m.matches()) {
      String num = m.group(2);

      int sum = 0;
      final int len = num.length();
      for (int i = 0; i < len; i++) {
        int tmp = (2 - i % 2) * (num.charAt(i) - '0');
        sum += tmp / 10 + tmp % 10;
      }
      int index = 10 - sum % 10;

      char type = m.group(1).charAt(0);
      char control = m.group(3).charAt(0);
      switch (type) {
        case 'A':
        case 'B':
        case 'E':
        case 'H':
          return control == CIF_DIGITS[0][index];
        case 'K':
        case 'P':
        case 'Q':
        case 'S':
          return control == CIF_DIGITS[1][index];
        default:
          return control == CIF_DIGITS[control >= '0' && control <= '9' ? 0 : 1][index];
      }
    }

    return false;
  }

  private boolean isValidNif(String nif) {
    Matcher m = NIF_REGEX.matcher(nif);
    if (m.matches()) {
      int number = parseNifNumber(m.group(1).charAt(0), m.group(2));
      char control = m.group(3).charAt(0);

      return control == NIF_DIGITS[number % NIF_DIGITS.length];
    }
    return false;
  }

  private int parseNifNumber(char type, String num) {
    String number;
    if (type >= '0' && type <= '9') {
      number = type + num;
    } else if (type == 'Y') {
      number = "1" + num;
    } else if (type == 'Z') {
      number = "2" + num;
    } else {
      number = num;
    }

    return parseInt(number);
  }
}
