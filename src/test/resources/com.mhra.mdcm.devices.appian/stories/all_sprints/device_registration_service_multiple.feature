Feature: As an account holder with access to the device registration service
  I want to be able to enter more than one device at the point of registration
  So that I don't have to return to add further devices post-registration

  @regression @mdcm-143 @_sprint1 @isReadyForRT @mdcm-134 @_sprint6
  Scenario Outline: Users should be able to add all 4 device type
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
      | deviceType     | Active Implantable Medical Devices |
      | gmdnDefinition | <device3>                          |
      | customMade     | true                               |
      | productName    | lordhelpme                         |
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
    Then I should see option to add another device
#    And The gmdn code or term is "displayed" in summary section
#    And All the gmdn codes or terms are "displayed" in summary section
    When Proceed to payment and confirm submit device details
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    And I view new task with link "Update Manufacturer Registration Request" for the new account
    Then Check task contains correct stored devices and other details
    And I assign the task to me and "approve" the generated task
    #Then The completed task status should update to "Completed"
    Then The task should be removed from tasks list
    Examples:
      | user              | logBackInAas | device1              | device2 | device3 | device4 |
      | manufacturerAuto  | businessAuto | Blood weighing scale | Blood   | Sinus   | Recept  |
      | authorisedRepAuto | businessAuto | Blood weighing scale | Blood   | Sinus   | Recept  |

