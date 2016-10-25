@Records_All_Organisations
Feature: As a business user I want to view a list of all Manufacturers and Authorized Representatives regardless of account association
  so that I can quickly retrieve information for them

  @mdcm_23
  Scenario Outline: As a business user I should be able to view all organisation page
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    Examples:
      | user         | link              | pageHeading       | columns                                                      |
      | businessAuto | All Organisations | All Organisations | Name,Account Number,Role,Contact Name,Address,Country,Status |


  @mdcm_23
  Scenario Outline: As a business user I should be able to search for an existing organisation
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    And I search for a "<existing>" organisation
    Then The search result should "<not>" the organisation
    Examples:
      | user         | link              | existing     | not |
      | businessAuto | All Organisations | existing     | contain     |
      | businessAuto | All Organisations | non existing | not contain |


  @mdcm_23 @ignore @todo
  Scenario Outline: As a business user I should be able to search for a newly added organisation
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    And I search for a "<existing>" organisation
    Then The search result should "<not>" the organisation
    Examples:
      | user         | link              | existing     | not |
      | businessAuto | All Organisations | existing     |     |
      | businessAuto | All Organisations | non existing | not |