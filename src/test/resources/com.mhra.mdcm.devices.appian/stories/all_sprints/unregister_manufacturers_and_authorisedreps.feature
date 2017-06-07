Feature: As a user, I want to be able to unregister manufacturers when they are no longer required


  @2179 @_sprint18
  Scenario Outline: As a business user I should be able to unregister organisations
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    And I perform a search for "<searchTerm>" in "<link>" page
    And I filter by "<filterBy>" for the value "<filterValue>" in "<pageHeading>" page
    And I click on a random organisation link "<searchTerm>" in "<link>" page
    Then I should see business manufacturer details page for the manufacturer
    When I unregister the manufacturer for the following reason "<unregisteredReason>"
    And I confirm "yes" to unregister the manufacturer
    Then The manufacturer should no longer be registered
    Examples:
      | user         | link          | pageHeading   | filterBy          | filterValue | searchTerm        | unregisteredReason    |
      | businessAuto | Organisations | Organisations | Registered status | Registered  | AuthorisedRepRT01 | Ceased Trading        |
      | businessAuto | Organisations | Organisations | Registered status | Registered  | AuthorisedRepRT01 | No Longer Represented |


  @4368 @2268 @_sprint18
  Scenario Outline: As a manufacturer and authorisedRep users I should be able to unregister organisations
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I view a random manufacturer with status "<status>"
    Examples:
      | user              | status     | searchTerm        | unregisteredReason    |
      | authorisedRepNoor | Registered | AuthorisedRepRT01 | Ceased Trading        |
      | manufacturerNoor  | Registered | AuthorisedRepRT01 | No Longer Represented |