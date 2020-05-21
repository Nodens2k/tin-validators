package org.nodens2k.tin.validation;

/**
 * The different types of TIN to be validated.
 */
public enum TinType {
  /**
   * Any TIN valid document.
   */
  ANY,
  /**
   * TIN for a physical person.
   */
  PERSONAL,
  /**
   * TIN for a company.
   */
  COMPANY
}
