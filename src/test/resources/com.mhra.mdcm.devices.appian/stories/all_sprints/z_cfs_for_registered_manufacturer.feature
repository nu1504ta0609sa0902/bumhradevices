Feature: Able to add CFS for products and devices that are already registered
  So that manufacturers can export products outside EU without going through MHRA again


  @1992 @_sprint21
  Scenario: Users should be able to approve and reject a cfs application
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Desiccating chamber       |
      | customMade     | false                     |
      | notifiedBody   | NB 0086 BSI               |
      | productName    | FordHybrid                |
      | productModel   | FocusYeah                 |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Application"


  @1992 @_sprint21
  Scenario Outline: Customers can order CFS for already registered manufacturers
    Given I am logged into appian as "<logInAs>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I search for registered manufacturer "<searchTerm>"
    And I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | <country> |
      | noOfCFS     | <noCFS>   |
    Then I should see the correct details in cfs review page
    When I submit payment for the CFS
    Examples:
      | country | noCFS | logInAs          | searchTerm                       |
      | Brazil  | 15    | manufacturerAuto | ManufacturerRT01Test_22_6_298400 |
#      | Bangladesh | 10    | authorisedRepAuto | AutorisedRepRT01Test |


  @1992 @_sprint21 @1992 @_sprint21 @e2e
  Scenario Outline: Users should be able to approve and reject a cfs application
    Given I am logged into appian as "<logInAs>" user
# Submit a new CFS manufacturer application
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Desiccating chamber       |
      | customMade     | false                     |
      | notifiedBody   | NB 0086 BSI               |
      | productName    | FordHybrid                |
      | productModel   | FocusYeah                 |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Application"
# Now apply for cfs
    Given I am logged into appian as "<logInAs>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I search for registered manufacturer ""
    And I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | <country> |
      | noOfCFS     | <noCFS>   |
    Then I should see the correct details in cfs review page
    When I submit payment for the CFS
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Application"
    Examples:
      | country | noCFS | logInAs          | searchTerm |
      | Brazil  | 15    | manufacturerAuto |            |
#      | Bangladesh | 10    | authorisedRepAuto |  |