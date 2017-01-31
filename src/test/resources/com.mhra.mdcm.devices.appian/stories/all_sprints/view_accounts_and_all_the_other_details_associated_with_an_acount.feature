Feature: As a business user I want to view all the organisations associated with an account, not just the account holder details
  so that I can understand relationships between account holders and organisations, and retrieve information quickly

  @mdcm-126 @readonly @sprint1
  Scenario Outline: As a business user I should be able to view all accounts
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    Examples:
      | user         | link     | pageHeading | columns                                                                                                          |
      | businessAuto | Accounts | Accounts    | Organisation name,Account number,Organisation role,Contact name,Organisation address,Organisation country,Status |

  @regression @mdcm-23 @mdcm-126 @readonly @sprint1 @sprint6
  Scenario Outline: As a business user I should be able to view all organisation page
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    Examples:
      | user         | link              | pageHeading       | columns                                       |
      | businessAuto | All Organisations | All Organisations | Name,Role,Contact name,Address,Country,Status |

  @mdcm-126 @mdcm-23 @readonly @sprint1 @sprint6 @bug
  Scenario Outline: By default list of accounts should be displayed in a to z order
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And The items in "<pageHeading>" page are displayed in alphabetical order
    Examples:
      | user         | link              | pageHeading       |
      | businessAuto | All Organisations | All Organisations |
      | businessAuto | Accounts          | Accounts          |


  @mdcm-126 @mdcm-23 @readonly @sprint1 @sprint6
  Scenario Outline: Users should be able to filter and sort by headings
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    When I filter items in "<pageHeading>" page by organisation role "<organisationType>"
    And I sort items in "<pageHeading>" page by "<tableHeading>"
    Then I should see only see organisation of type "<organisationType>" in "<pageHeading>" page
    Examples:
      | user         | link              | pageHeading       | organisationType | tableHeading      |
      | businessAuto | All Organisations | All Organisations | Authorised       | Name              |
      | businessAuto | All Organisations | All Organisations | Manufacturer     | Name              |
      | businessAuto | Accounts          | Accounts          | Authorised       | Organisation name |
      | businessAuto | Accounts          | Accounts          | Manufacturer     | Organisation name |


  @mdcm-23 @readonly @sprint6
  Scenario Outline: As a business user I should be able to search for an existing organisation
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    And I search for a "<existing>" organisation
    Then The all organisation search result should return <count> matches
    Examples:
      | user         | link              | existing     | count |
      | businessAuto | All Organisations | existing     | 1     |
      | businessAuto | All Organisations | non existing | 0     |