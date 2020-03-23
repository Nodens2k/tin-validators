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

public class TinValidatorTest {

  @Test
  public void testRegister() {
    // Given
    CountryTinValidator mockValidator = Mockito.mock(CountryTinValidator.class);
    when(mockValidator.isValid("111")).thenReturn(true);
    TinValidator.register(mockValidator, "Extra", "XXX");
    TinValidator validator = new TinValidator(true);

    // When
    boolean valid = validator.isValid("XXX", "111");

    // Then
    assertThat(valid).isTrue();
    verify(mockValidator).isValid("111");
  }

  @TestFactory
  public Stream<DynamicTest> testMultipleTins() {
    String[] tests = {
        "AT,null,false",
        "AT,931736581,true",

        "BE,null,false",
        "BE,00012511119,true",
        "BE,00012511148,true",

        "BG,7501010010,true",

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
        "ES,null,false",

        "FR,30 23 217 600 053,true",

        "IT,MRTMTT25D09F205Z,true",
        "IT,MRTMTT25D09F205X,false",
        "IT,MRTMTT25D09F20Z,false",
        "IT,null,false",

        "PT,null,false",
        "PT,1234,false",
        "PT,999999990,true",
        "PT,99.999.999-9,false",
        "PT,501442600,true",

        "RO,null,false",
        "RO,1800101221145,false",
        "RO,1800101221,false",
        "RO,1800101221144,true",

        "XX,111,true"
    };

    return Arrays.stream(tests)
        .map(definition -> {
          String[] tokens = definition.split(",");
          final String countryCode = parseNull(tokens[0]);
          final String tin = parseNull(tokens[1]);
          final boolean valid = "true".equals(tokens[2]);

          return DynamicTest.dynamicTest(definition, () -> {
            // Given
            TinValidator validator = new TinValidator(true);
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
