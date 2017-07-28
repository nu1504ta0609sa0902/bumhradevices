@cfs
Feature: Tasks will need to be generated for CFS application
  So that business users can verify, approve and reject them accordingly


  @5679 @_sprint20 @create_new_org
  Scenario: Users should be shown correct options at different stages of approving a task
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
      | productName    | Product1                  |
      | productModel   | Model1                    |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    Then I should see a button with the following text "Assign to myself"
    And I should not see any option related to approving reject and completing the application
    And I assign the generated AWIP page task to me
    Then The task status in AWIP page should be "Assigned" for the newly created manufacturer


  @5679 @_sprint20 @5673 @5674 @_sprint22 @_sprint24 @create_new_org
  Scenario Outline: Users should be able to approve and reject a cfs application
    Given I am logged into appian as "<user>" user
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
      | productName    | Product1                  |
      | productModel   | Model1                    |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "<expectedStatus>" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
    Examples:
      | user              | expectedStatus | approveReject |
      | manufacturerAuto  | Completed      | approve       |
      | authorisedRepAuto | Completed      | reject        |


  @smoke_test_cfs @5666 @5679 @_sprint20 @6867 @_sprint26 @create_new_org
  Scenario Outline: Users should be able to go to assign the cfs application to someone else
    Given I am logged into appian as "<user>" user
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
      | productName    | Product1                  |
      | productModel   | Model1                    |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the task to another user called "<assignTo>"
    Then I search for task in AWIP page for the manufacturer
    Then Verify the task is assigned to the correct user
    And The task status in AWIP page should be "<expectedStatus>" for the newly created manufacturer
    Examples:
      | user              | assignTo | expectedStatus |
      | manufacturerAuto  | Noor     | Assigned       |
      | authorisedRepAuto | Nobody   | In Progress    |

    @6859 @6878 @_sprint26 @create_new_org @wip
    Scenario Outline: Business to review and process CFS order application
    Given I am logged into appian as "<logInAs>" user
# Submit a new CFS manufacturer application
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
# Now apply for cfs
    Given I logout and log back into appian as "<logInAs>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I search for registered manufacturer ""
    And I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | <country> |
      | noOfCFS     | <noCFS>   |
    Then I should see the correct details in cfs order review page
    When I submit payment for the CFS
    And I should received an email with subject heading "WorldPay Payment"
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    Then I should see the correct cfs manufacturer details
    And I should see correct device details
    When I view device with gmdn code "Blood weighing scale"
    Then I should see all the correct product and certificate details
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
    Examples:
      | country    | noCFS | logInAs           | searchTerm |
      | Brazil     | 15    | manufacturerAuto  |            |
      | Bangladesh | 10    | authorisedRepAuto |            |