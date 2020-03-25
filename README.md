# tin-validators

Tax and Personal Identification Number validation for several countries.

All algorithms for European countries are based on the [Public Algorithms](https://ec.europa.eu/taxation_customs/tin/specs/FS-TIN%20Algorithms-Public.docx)
published by the European Commission.

## Quick Start

For Maven projects, add the following dependency to your pom.xml file:

```xml
<dependency>
  <groupId>org.nodens2k</groupId>
  <artifactId>tin-validators</artifactId>
  <version>1.0</version>
</dependency>
```

Afterwards, you can use the library through its main entry-point:

```java
import org.nodens2k.tin.validation.CountryTinValidator;
import org.nodens2k.tin.validation.TinValidator;

public class Example {
  public static void main(String... args) {
    CountryTinValidator validator = new TinValidator(true);

    String country = "ES";
    String tin = "39245072-B";

    System.out.println("Valid: " + validator.isValid(country, tin));
  }
}
```

Or, if your application is only used in one of the supported countries, by its country-specific class:

```java
import org.nodens2k.tin.validation.PortugalTinValidator;

public class Example {
  public static void main(String... args) {
    String tin = "123456789";

    System.out.println("Valid: " + PortugalTinValidator.INSTANCE.isValid(tin));
  }
}
```

## Supported Countries

- Austria
- Belgium
- Bulgaria
- Croatia
- Cyprus
- Czechia (only best effort, as the check algorithm is not public)
- France
- Germany
- Italy
- Portugal (support for *CIVIL ID No* and *TAX No*)
- Romania
- Spain (support for *NIF*, *NIE* and *CIF*)
