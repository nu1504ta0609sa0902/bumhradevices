Feature: As a business user, I want to access a list of organisations with an account
  so that I can quickly confirm if they are known to the MHRA and retrieve key contact information

  @mdcm-126 @readonly
  Scenario Outline: As a business user I should be able to view all accounts
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    Examples:
      | user         | link     | pageHeading | columns                                                                                                          |
      | businessAuto | Accounts | Accounts    | Organisation name,Account number,Organisation role,Contact name,Organisation address,Organisation country,Status |

  @regression @mdcm_126 @readonly
  Scenario Outline: As a business user I should be able to view all organisation page
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    Examples:
      | user         | link              | pageHeading       | columns                                       |
      | businessAuto | All Organisations | All Organisations | Name,Role,Contact name,Address,Country,Status |

  @mdcm-126 @readonly
  Scenario Outline: By default list of accounts should be displayed in a to z order
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And The items are displayed in alphabetical order
    Examples:
      | user         | link     | pageHeading |
      | businessAuto | Accounts | Accounts    |


