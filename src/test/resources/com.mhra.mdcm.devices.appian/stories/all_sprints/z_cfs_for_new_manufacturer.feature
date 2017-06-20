Feature: Users should be able to add remove and edit for CFS new manufacturer application

  @5672 @_sprint20
  Scenario Outline: Users should be able to remove devices from a CFS application
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
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | <deviceToRemove>       |
      | customMade           | false                  |
      | riskClassification   | Class2a                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    When I remove device called "<deviceToRemove>" from list of devices
#    Then The delete button should be disabled
    Then I should see correct device data in the review page
    When I go back to the CE certificates page
    Then I should see all the certificates previously uploaded
    Examples:
      | user              | deviceToRemove       |
      | manufacturerAuto  | Blood weighing scale |
#      | authorisedRepAuto | Desiccating chamber  |