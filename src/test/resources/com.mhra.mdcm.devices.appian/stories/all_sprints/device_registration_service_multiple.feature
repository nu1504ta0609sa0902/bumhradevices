Feature: As a customer I want to be able to enter more than one device at the point of registration
  So that I don't have to return to add further devices post-registration

  @3104 @_sprint8 @ignore @create_new_org
  Scenario Outline: Users should be able to add all 4 device type to NEWLY registered organisations
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | <device1>              |
      | customMade     | true                   |
    When I add another device to SELECTED manufacturer with following data
      | deviceType         | In Vitro Diagnostic Device |
      | gmdnDefinition     | <device2>                  |
      | riskClassification | list a                     |
      | notifiedBody       | NB 0086 BSI                |
      | productName        | PremierLeague              |
      | productMake        | britishsky                 |
      | productModel       | wtf1                       |
      | subjectToPerfEval  | true                       |
      | newProduct         | true                       |
      | conformsToCTS      | true                       |
    When I add another device to SELECTED manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | <device3>                 |
      | customMade     | true                      |
      | productName    | lordhelpme                |
    When I add another device to SELECTED manufacturer with following data
      | deviceType             | System or Procedure Pack |
      | gmdnDefinition         | <device4>                |
      | riskClassification     | class1                   |
      | notifiedBody           | NB 0086 BSI              |
      | customMade             | true                     |
      | relatedDeviceSterile   | true                     |
      | relatedDeviceMeasuring | true                     |
      | isBearingCEMarking     | false                    |
      | devicesCompatible      | true                     |
      | productName            | lordhelpme               |
    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    And All the gmdn codes or terms are "displayed" in summary section
    When Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    Then I should see correct device details
#    Then Check task contains correct stored devices and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    Then The task should be removed from tasks list
    Examples:
      | user              | accountType   | countryName | logBackInAs  | device1              | device2 | device3 | device4 |
      | manufacturerAuto  | manufacturer  | Brazil      | businessAuto | Blood weighing scale | Blood   | Sinus   | Recept  |
      | authorisedRepAuto | authorisedRep | Bangladesh  | businessAuto | Blood weighing scale | Blood   | Sinus   | Recept  |

  @regression @mdcm-143 @2283 @_sprint1 @isReadyForRT @mdcm-134 @2290 @_sprint6
  Scenario Outline: Users should be able to add all 4 device type to EXISTING organisations
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | <device1>              |
      | customMade     | true                   |
    When I add another device to SELECTED manufacturer with following data
      | deviceType         | In Vitro Diagnostic Device |
      | gmdnDefinition     | <device2>                  |
      | riskClassification | list a                     |
      | notifiedBody       | NB 0086 BSI                |
      | productName        | premierLeague              |
      | productMake        | britishsky                 |
      | productModel       | wtf1                       |
      | subjectToPerfEval  | true                       |
      | newProduct         | true                       |
      | conformsToCTS      | true                       |
    When I add another device to SELECTED manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | <device3>                 |
      | customMade     | true                      |
      | productName    | lordhelpme                |
    When I add another device to SELECTED manufacturer with following data
      | deviceType             | System or Procedure Pack |
      | gmdnDefinition         | <device4>                |
      | riskClassification     | class1                   |
      | notifiedBody           | NB 0086 BSI              |
      | customMade             | true                     |
      | relatedDeviceSterile   | true                     |
      | relatedDeviceMeasuring | true                     |
      | isBearingCEMarking     | false                    |
      | devicesCompatible      | true                     |
      | productName            | lordhelpme               |
    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    And All the gmdn codes or terms are "displayed" in summary section
    When Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    And I search and view new task in AWIP page for the newly created manufacturer
#    Then Check task contains correct stored devices and other details
    Then I should see correct device details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    Then The task should be removed from tasks list
    Examples:
      | user              | logBackInAs  | device1              | device2 | device3 | device4 |
      | manufacturerAuto  | businessAuto | Blood weighing scale | Blood   | Sinus   | Recept  |
      | authorisedRepAuto | businessAuto | Blood weighing scale | Blood   | Sinus   | Recept  |

