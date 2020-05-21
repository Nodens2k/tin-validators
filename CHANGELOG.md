# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased (1.2.0-SNAPSHOT)

### Added

- Support for validating only company or personal TINs (only some countries)

## 1.1.0 - 2020-05-21

### Added

- New interface TinValidatorFactory with default implementation
- Now TinValidator implements CountryTinValidator
- New methods in CountryTinValidator interface
- Implementations for Canada, Croatia, Cyprus, Czech Republic, Denmark, Estonia, Finland, Germany and Greece
- Added validation for Italian _Carta d'Identità_
- Test suite improvements

## 1.0.0 - 2020-03-23

### Added

- Public API classes: TinValidator and CountryTinValidator.
- Implementation for several countries: Austria, Belgium, Bulgaria, France, Italy,
  Portugal, Romania and Spain.
- Unit tests
