Feature: Amazon Shopping Cart Suite

  Scenario Outline: Search and add high-value items to cart
    Given User is on Amazon homepage "https://www.amazon.com"
    And User handles the initial interstitial if present
    When User searches for <product_name>
    And User selects the first product from results
    And User adds the product to the cart
    And User dismisses the protection plan if offered
    Then User should see the item in the shopping cart

    Examples:
      | product_name   |
      | "B0FV4LVJQK"   |