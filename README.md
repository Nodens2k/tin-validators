# tin-validators

![release](https://img.shields.io/maven-central/v/io.github.nodens2k/tin-validators)
![snapshot](https://img.shields.io/nexus/s/io.github.nodens2k/tin-validators?color=green&server=https%3A%2F%2Foss.sonatype.org&style=flat)
![last commit](https://img.shields.io/github/last-commit/nodens2k/tin-validators)

Tax and Personal Identification Number validation for several countries.

All algorithms for European countries are based on the
[Public Algorithms](https://ec.europa.eu/taxation_customs/tin/specs/FS-TIN%20Algorithms-Public.docx)
published by the European Commission.

## Quick Start

For Maven projects, add the following dependency to your pom.xml file:

```xml
<dependency>
  <groupId>io.github.nodens2k</groupId>
  <artifactId>tin-validators</artifactId>
  <version>1.1.0</version>
</dependency>
```

or, for the current snapshot (hosted in Sonatype Nexus repository):

```xml
<dependency>
  <groupId>io.github.nodens2k</groupId>
  <artifactId>tin-validators</artifactId>
  <version>1.2.0-SNAPSHOT</version>
</dependency>
```

Afterwards, you can use the library through its main entry-point (TinValidatorFactory):

```java
TinValidatorFactory factory = DefaultTinValidatorFactory.INSTANCE;
boolean valid = factory.getValidatorFor("ES").isValid("72431799S");
```

Or, if your application is only used in one of the supported countries, by its
country-specific class:

```java
boolean valid = PortugalTinValidator.INSTANCE.isValid("123456789"));
```

## Features

### Supported Countries

| Country  | ISO Codes    | Version        | Notes   |
|----------|------------- |----------------|---------|
| Austria  | AT, AUT, 040 | 1.0.0          |         |
| Belgium  | BE, BEL, 056 | 1.0.0          |         |
| Bulgaria | BG, BGR, 100 | 1.0.0          |         |
| Canada   | CA, CAN, 124 | 1.1.0          |         |
| Croatia  | HR, HRV, 191 | 1.1.0          |         |
| Cyprus   | CY, CYP, 196 | 1.1.0          |         |
| Czechia  | CZ, CZE, 203 | 1.1.0          | Only best effort, the algorithm is not public |
| Denmark  | DK, DNK, 208 | 1.1.0          |         |
| Estonia  | EE, EST, 233 | 1.1.0          |         |
| Finland  | FI, FIN, 246 | 1.1.0          |         |
| France   | FR, FRA, 250 | 1.0.0          |         |
| Germany  | DE, DEU, 276 | 1.0.0          |         |
| Greece   | GR, GRC, 300 | 1.1.0          |         |
| Italy    | IT, ITA, 380 | 1.0.0          | Some checks still missing |
| Portugal | PT, PRT, 620 | 1.0.0          | Support for *CIVIL ID No* and *TAX No* |
| Romania  | RO, ROU, 642 | 1.0.0          |         |
| Spain    | ES, ESP, 724 | 1.0.0          | Support for *NIF*, *NIE* and *CIF* |
| UK       | GB, GBR, 826 | 1.1.0          |         |

### Planned but not supported yet

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

## Contributing

If you have information on any unsupported country algorithm, or if you find
a bug, please submit a GitHub issue. Please search the existing issues before
submitting, in order to prevent duplicates.

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of
conduct, and the process for submitting pull requests to us.

## Contact

If you have any questions, feel free to open an issue here on GitHub.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE)
file for details.
