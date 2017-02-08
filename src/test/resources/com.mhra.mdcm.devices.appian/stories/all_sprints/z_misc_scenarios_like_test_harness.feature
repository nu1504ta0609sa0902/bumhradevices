Feature: Aa a user I would like to verify features which are not related to devices and manufacturers
  So that I can be reassured my tests will not fail due to unexpected circumstances

  @2399 @readonly @sprint7
  Scenario Outline: Verify the new countries list in business test harness uses autosuggestion
    Given I am logged into appian as "<user>" user
    When I go to test harness page
    And I enter "<searchTerm>" in the new country field
    Then I should see following "<matches>" returned by autosuggests
    Examples:
      | user         | searchTerm | matches          |
      | businessNoor | ZZ         | No results found |
      | businessNoor | Ba         | Bangladesh       |
      | businessNoor | Au         | Australia        |

  @2399 @3365 @readonly @sprint7
  Scenario Outline: Verify the new countries list in MANUFACTURER test harness uses autosuggestion
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    And I enter "<searchTerm>" in the new country field in manufacturer test harness
    Then I should see following "<matches>" returned by manufacturer test harness autosuggests
    Examples:
      | user              | searchTerm | matches          |
      | manufacturerNoor  | Au         | Australia        |
      | manufacturerNoor  | ZZ         | No results found |
      | authorisedRepNoor | Au         | Australia        |
      | authorisedRepNoor | ZZ         | No results found |
