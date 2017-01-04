Feature: As an account holder with access to the device registration service
  I want to be able to enter more than one device at the point of registration
  So that I don't have to return to add further devices post-registration

  @regression @mdcm-143 @sprint1 @isReadyForRT
  Scenario Outline: Users should be able to add all 4 device type
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType             | General Medical Device |
      | gmdnDefinition         | Surgical Guillotine    |
      | customMade             | true                   |
      | relatedDeviceSterile   | true                   |
      | relatedDeviceMeasuring | true                   |
    When I add another device to SELECTED manufacturer with following data
      | deviceType             | System or Procedure Pack |
      | gmdnDefinition         | Acetabular Shell         |
      | riskClassification     | class1                   |
      | notifiedBody           | NB 0086 BSI              |
      | customMade             | true                     |
      | relatedDeviceSterile   | true                     |
      | relatedDeviceMeasuring | true                     |
      | packIncorporated       | false                    |
      | devicesCompatible      | true                     |
    When I add another device to SELECTED manufacturer with following data
      | deviceType     | Active Implantable Medical Devices |
      | gmdnDefinition | Housekeeping soap                  |
      | customMade     | true                               |
      | productName    | lordhelpme                         |
    When I add another device to SELECTED manufacturer with following data
      | deviceType         | In Vitro Diagnostic Device |
      | gmdnDefinition     | Needle introducer          |
      | riskClassification | list a                     |
      | notifiedBody       | NB 0086 BSI                |
      | productName        | premierLeague              |
      | productMake        | britishsky                 |
      | productModel       | wtf1                       |
      | subjectToPerfEval  | true                       |
      | newProduct         | true                       |
      | conformsToCTS      | true                       |
    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    When Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    And I view new task with link "Update Manufacturer Registration Request" for the new account
    Then Check task contains correct devices "<devices>" and other details
    #And I assign the task to me and "approve" the generated task
    #Then The completed task status should update to "Completed"
    Examples:
      | user             | logBackInAas | devices                                                                  |
      | manufacturerAuto | businessAuto | Surgical Guillotine,Acetabular Shell,Housekeeping soap,Needle introducer |

