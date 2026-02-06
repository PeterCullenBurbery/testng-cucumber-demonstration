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
      | "Victorinox Huntsman Swiss Army Knife, 15 Functions, Swiss Made Pocket Knife with Large Blade, Screwdriver, Scissors and Wood Saw - Red"           |
