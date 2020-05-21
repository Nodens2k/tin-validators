package org.nodens2k.tin.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.mockito.Mockito;

public final class TinValidatorTest {

  private final TinValidator validator = new TinValidator(true);

  @Test
  public void testRegister() {
    // Given
    CountryTinValidator mockValidator = Mockito.mock(CountryTinValidator.class);
    when(mockValidator.isValid("111", TinType.ANY)).thenReturn(true);
    TinValidator.register(mockValidator, "Extra", "XXX");

    // When
    boolean valid = validator.isValid("XXX", "111");

    // Then
    assertThat(valid).isTrue();
    verify(mockValidator).isValid("111", TinType.ANY);
  }

  @TestFactory
  public Stream<DynamicTest> testCommonFalseTins() {
    return validator.getSupportedCountries()
        .stream()
        .flatMap(code -> Stream.of(code + ",null,false", code + ",,false", code + ",---,false"))
        .map(definition -> {
          String[] tokens = definition.split(",");

          return DynamicTest.dynamicTest(definition, () -> {
            // Given
            String countryCode = parseNull(tokens[0]);
            String tin = parseNull(tokens[1]);
            boolean valid = "true".equals(tokens[2]);
            // When
            boolean isValid = validator.isValid(countryCode, tin);
            // Then
            assertThat(isValid).isEqualTo(valid);

          });
        });
  }

  @TestFactory
  public Stream<DynamicTest> testMultipleTins() {
    String[] tests = {
        "AT,931736581,true",

        "BE,00012511119,true",
        "BE,00012511148,true",

        "BG,7501010010,true",

        "CY,00123123T,true",
        "CY,99652156X,true",

        "DE,26954371827,true",
        "DE,65929970489,true",

        "DK,010111-1113,true",

        "EE,37102250382,true",
        "EE,32708101201,true",
        "EE,46304280206,true",

        "ES,62276484-M,true",
        "ES,X 9778222 W,true",
        "ES,F/7828980H,true",
        "ES,43.111.222-V,false",
        "ES,Q1593721B,true",
        "ES,E19928324,true",
        "ES,X5850457Q,true",
        "ES,Y4857483N,true",
        "ES,Z6998464Y,true",
        "ES,I19928324,false",
        "ES,A1992832478,false",
        "ES,999999990,false",

        "FI,131052-308T,true",

        "FR,30 23 217 600 053,true",
        "FR,11 22 333 444 555,false",

        "GB,1234567890,true",
        "GB,AA123456A,true",
        "GB,ZZ111111,false",
        "GB,DD123456A,false",
        "GB,AB1234567,false",

        "GR,123456789,true",
        "GR,12345678A,false",
        "GR,1234567890,false",

        "HR,94577403194,true",

        "IT,MRTMTT25D09F205Z,true",
        "IT,MRTMTT25D09F205X,false",
        "IT,MRTMTT25D09F20Z,false",
        "IT,FLRNTN63D28Z131Q,true",
        "IT,FRGLSE77S45F205S,true",
        "IT,ZTTLMR74S48F205Z,true",
        "IT,ITITDKWL65P43F205P,true",
        "IT,00000001230,false",
        "IT,00000011230,false",
        "IT,00000011205,true",

        "PT,1234,false",
        "PT,999999990,true",
        "PT,99.999.999-9,false",
        "PT,501442600,true",

        "RO,1800101221145,false",
        "RO,1800101221,false",
        "RO,1800101221144,true",

        "SI,15012557,true",

        "XX,111,true"
    };

    return Arrays.stream(tests)
        .map(definition -> {
          String[] tokens = definition.split(",");

          return DynamicTest.dynamicTest(definition, () -> {
            // Given
            String countryCode = parseNull(tokens[0]);
            String tin = parseNull(tokens[1]);
            boolean valid = "true".equals(tokens[2]);
            // When
            boolean isValid = validator.isValid(countryCode, tin);
            // Then
            assertThat(isValid).isEqualTo(valid);
          });
        });
  }

  private static String parseNull(String s) {
    return "null".equals(s) ? null : s;
  }
}
