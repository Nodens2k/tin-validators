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
  <version>1.0.0</version>
</dependency>
```

or, for the current snapshot:

```xml
<dependency>
  <groupId>org.nodens2k</groupId>
  <artifactId>tin-validators</artifactId>
  <version>1.1.0-SNAPSHOT</version>
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

| Country  | ISO Codes    | Version        | Notes   |
|----------|------------- |----------------|---------|
| Austria  | AT, AUT, 040 | 1.0.0          |         |
| Belgium  | BE, BEL, 056 | 1.0.0          |         |
| Bulgaria | BG, BGR, 100 | 1.0.0          |         |
| Croatia  | HR, HRV, 191 | 1.1.0-SNAPSHOT |         |
| Cyprus   | CY, CYP, 196 | 1.1.0-SNAPSHOT |         |
| Czechia  | CZ, CZE, 203 | 1.1.0-SNAPSHOT | Only best effort, the algorithm is not public |
| Denmark  | DK, DNK, 208 | 1.1.0-SNAPSHOT |         |
| Estonia  | EE, EST, 233 | 1.1.0-SNAPSHOT |         |
| Finland  | FI, FIN, 246 | 1.1.0-SNAPSHOT |         |
| France   | FR, FRA, 250 | 1.0.0          |         |
| Germany  | DE, DEU, 276 | 1.0.0          |         |
| Greece   | GR, GRC, 300 | 1.1.0-SNAPSHOT          |         |
| Italy    | IT, ITA, 380 | 1.0.0          | Some checks still missing |
| Portugal | PT, PRT, 620 | 1.0.0          | Support for *CIVIL ID No* and *TAX No* |
| Romania  | RO, ROU, 642 | 1.0.0          |         |
| Spain    | ES, ESP, 724 | 1.0.0          | Support for *NIF*, *NIE* and *CIF* |

## Planned but not supported yet

The following countries will be added to the supported list in the future:

- Hungary
- Ireland
- Latvia
- Lithuania
- Luxembourg
- Malta
- Netherlands
- Poland
- Slovakia
- Slovenia
- Sweden
- UK

