Feature: As a user, I want to view devices and product details
  so that I can quickly verify correct device and related product information is correct

  @regression @1924 @wip
  Scenario Outline: As a business user I should be able to verify device and product details related to a manufacturer
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
    And I filter by "Registered status" for the value "REGISTERED" in "<page>" page
    And I click on a random organisation link "<searchTerm>" in "<page>" page
    Then I should see business manufacturer details page for the manufacturer
    When I click on link "product details" and go to "devices" page
    Then I should see device table with devices
    Examples:
      | user         | page              | searchTerm                       | count |
      | businessAuto | All Organisations | AuthorisedRepRT01Test_24_2_85237 | 1     |


  @regression @1924 @wip
  Scenario Outline: As a business user I should be able to create new manufacturers and verify device and product details related to a manufacturer
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I view new task with link "New Manufacturer Registration Request" for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
    And I click on a random organisation link "<searchTerm>" in "<page>" page
    Then I should see business manufacturer details page for the manufacturer
    Examples:
      | user              | logBackInAs  | accountType   | approveReject | count | countryName | page              | searchTerm       |
      #| manufacturerAuto  | businessAuto | manufacturer  | approve       | 1     | Brazil       |All Organisations | ManufacturerRT01 |
      | authorisedRepAuto | businessAuto | authorisedRep | approve       | 0     | Belarus     | All Organisations | ManufacturerRT01 |