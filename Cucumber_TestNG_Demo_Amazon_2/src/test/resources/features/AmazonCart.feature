Feature: Amazon Shopping Cart

  Scenario: Search and add item to cart
    Given User is on Amazon homepage "https://www.amazon.com"
    And User handles the initial interstitial if present
    When User searches for "Fakanhui satin pants for women"
    And User selects the first product from results
    And User adds the product to the cart
    Then User should be able to navigate to the cart page