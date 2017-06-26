Feature: Users should be able to add remove and edit for CFS new manufacturer application

  @5672 @_sprint20
  Scenario Outline: Manufacturers should be able to remove devices from a CFS application
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Desiccating chamber                |
      | customMade     | false                              |
      | notifiedBody   | NB 0086 BSI                        |
      | productName    | FordHybrid2                         |
      | productModel   | FocusYeah3                          |
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | <deviceToRemove>       |
      | customMade           | false                  |
      | riskClassification   | Class2a                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    When I remove device called "<deviceToRemove>" from list of devices
    Then I should see correct device data in the review page
    When I go back to the CE certificates page
    Then I should see all the certificates previously uploaded
    Examples:
      | user              | deviceToRemove       |
      | manufacturerAuto  | Blood weighing scale |
      | authorisedRepAuto | Blood weighing scale  |


  @5679 @1954 @5680 @_sprint20 @5593 @5665 @_sprint21 @5673 @5674 @_sprint22
  Scenario: Business users should be able to view cfs new manufacturer and its devices and complete application
    Given I am logged into appian as "manufacturerAuto" user
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
    Then I should see the correct cfs manufacturer details
    And I should see correct device details
    When I view device with gmdn code "Blood weighing scale"
    Then I should see all the correct product and certificate details
    When I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" without completing the application
    And I approve all the cfs devices
    Then I all the device status should update to "Approved"
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Free Sale"


  @1986 @5593 @_sprint21 @5673 @5674 @_sprint22
  Scenario: Business users should be able to view cfs new manufacturer and its devices and able to change approval decisions
    Given I am logged into appian as "manufacturerAuto" user
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
    And I assign the AWIP page task to me and "approve" without completing the application
    Then I should see information related to the approver
    Then I should see the option to "Change decision"
    When I approve a single cfs devices
    And I click on change decision
    Then I should see the option to "Approve manufacturer"

