Feature: Tasks will need to be generated for CFS application
  So that business users can verify, approve and reject them accordingly

  Scenario: Remove me
    When I logout and log back into appian as "businessNoor" user
    And I search and view "ManufacturerRT01Test_20_6_877745" in AWIP page for the newly created manufacturer
    #Without approving
#    Then I should see the option to "Assign to myself"
#    And I should not see any option related to approving reject and completing the application
#    And I assign the generated AWIP page task to me
#    Then The task status in AWIP page should be "Assigned" for the newly created manufacturer
    #Complete application
#    And I assign the AWIP page task to me and "approve" the generated task
#    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
#    And I should received an email for stored manufacturer with heading "Application"
    #Assign to someone else
#    And I assign the task to another user called "Nobody"
#    When I search for task in AWIP page for the manufacturer
#    Then Verify the task is assigned to the correct user
#    And The task status in AWIP page should be "In Progress" for the newly created manufacturer


  @5679 @_sprint20
  Scenario: Users should be shown correct options at different stages of approving a task
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Medical Devices |
      | gmdnDefinition | Desiccating chamber                |
      | customMade     | false                              |
      | notifiedBody   | NB 0086 BSI                        |
      | productName    | FordHybrid                         |
      | productModel   | FocusYeah                          |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessNoor" user
    And I search and view new task in AWIP page for the newly created manufacturer
    Then I should see the option to "Assign to myself"
    And I should not see any option related to approving reject and completing the application
    And I assign the generated AWIP page task to me
    Then The task status in AWIP page should be "Assigned" for the newly created manufacturer


  @5679 @_sprint20 @5673 @5674 @_sprint22
  Scenario: Users should be able to approve and reject a cfs application
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Medical Devices |
      | gmdnDefinition | Desiccating chamber                |
      | customMade     | false                              |
      | notifiedBody   | NB 0086 BSI                        |
      | productName    | FordHybrid                         |
      | productModel   | FocusYeah                          |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessNoor" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Application"


  @5666 @_sprint20
  Scenario Outline: Users should be able to go to assign the cfs application to someone else
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Medical Devices |
      | gmdnDefinition | Desiccating chamber                |
      | customMade     | false                              |
      | notifiedBody   | NB 0086 BSI                        |
      | productName    | FordHybrid                         |
      | productModel   | FocusYeah                          |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessNoor" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the task to another user called "<assignTo>"
    Then I search for task in AWIP page for the manufacturer
    Then Verify the task is assigned to the correct user
    And The task status in AWIP page should be "<expectedStatus>" for the newly created manufacturer
    Examples:
      | user              | assignTo | expectedStatus |
      | manufacturerAuto  | Noor     | Assigned       |
      | authorisedRepAuto | Nobody   | In Progress    |