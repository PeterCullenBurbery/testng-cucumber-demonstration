Feature: Amazon Shopping Cart Suite

  Scenario Outline: Search and add various items to cart
    Given User is on Amazon homepage "https://www.amazon.com"
    And User handles the initial interstitial if present
    When User searches for <product_name>
    And User selects the first product from results
    And User adds the product to the cart
    Then User should be able to navigate to the cart page

    Examples:
      | product_name                               |
      | "Fakanhui satin pants for women"           |
      | "EVALESS pleated v neck blouse light pink" |