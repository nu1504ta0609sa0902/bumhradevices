Feature: As an account holder with access to the Device Registration Service
  I want to register a manufacturer and declare devices being manufacturer
  so that they can place the device for sale on the EU market

  @regression @mdcm-14 @mdcm-21 @sprint3 @sprint5
  Scenario Outline: Users should be able to view already registered manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    When I click on random manufacturer with status "<status>"
    Then I should see the correct manufacturer details
    Examples:
      | user              | status     |
      | manufacturerAuto  | REGISTERED |
      | authorisedRepAuto | REGISTERED |

  @regression @mdcm-14 @mdcm-21 @sprint3 @sprint5
  Scenario Outline: Verify correct device type options are displayed on add devices page
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    Then I should see correct device types
    Examples:
      | user              |
      | manufacturerAuto  |
      | authorisedRepAuto |


  @regression @mdcm-14 @mdcm-485 @mdcm-467 @mdcm-21 @sprint3 @sprint5
  Scenario Outline: Verify correct options are displayed on add devices page
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | <gmdnDefinition>     |
      | customMade             | <customMade>         |
      | relatedDeviceSterile   | <deviceSterile>      |
      | relatedDeviceMeasuring | <deviceMeasuring>    |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | packIncorporated       | <packIncorporated>   |
      | devicesCompatible      | <devicesCompatible>  |
    Then I should see option to add another device
    Examples:
      | user             | deviceType                 | gmdnDefinition | customMade | deviceSterile | deviceMeasuring | riskClassification | notifiedBody | packIncorporated | devicesCompatible |
      | manufacturerAuto | General Medical Device     | Blood          | false      | true          | true            | class1             | NB 0086 BSI  |                  |                   |
      | manufacturerAuto | General Medical Device     | Blood          | true       | true          | true            |                    |              |                  |                   |
      | manufacturerAuto | In Vitro Diagnostic Device | Laboratory     |            |               |                 | ivd general        |              |                  |                   |
      | manufacturerAuto | System or Procedure Pack   | Air sampling   | false       | true          | true            |  class1                  | NB 0086 BSI  | true             | true              |
      | manufacturerAuto | System or Procedure Pack   | Air sampling   | true       | true          | true            |                    | NB 0086 BSI  | true             | true              |


  @regression @mdcm-14 @mdcm-489 @sprint3 @sprint5
  Scenario Outline: Users should be able to add devices to existing manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | Blood        |
      | customMade             | true         |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    Examples:
      | user             | deviceType             |
      | manufacturerAuto | General Medical Device |


  @regression @mdcm-489 @sprint5
  Scenario Outline: Users should be able to add devices using GMDN code to existing manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnCode               | 10003        |
      | customMade             | true         |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    Examples:
      | user             | deviceType             |
      | manufacturerAuto | General Medical Device |


  @regression @mdcm-489 @sprint5
  Scenario Outline: Users should be able to add and remove devices using GMDN code to existing manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType>       |
      | gmdnDefinition         | <gmdnDefinitionD1> |
      | gmdnCode               | <gmdnCodeD1>       |
      | customMade             | true               |
      | relatedDeviceSterile   | true               |
      | relatedDeviceMeasuring | true               |
    And I add another device to SELECTED manufacturer with following data
      | deviceType             | <deviceType>       |
      | gmdnDefinition         | <gmdnDefinitionD2> |
      | gmdnCode               | <gmdnCodeD2>       |
      | customMade             | true               |
      | relatedDeviceSterile   | true               |
      | relatedDeviceMeasuring | true               |
#    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    When I remove the device with gmdn "<gmdnCodeD2>" code
#    Then I should see option to add another device
    Then The gmdn code or term is "removed" in summary section
    Examples:
      | user             | deviceType             | gmdnDefinitionD1 | gmdnCodeD1 | gmdnDefinitionD2 | gmdnCodeD2 |
      | manufacturerAuto | General Medical Device | Blood            |            |                  | 10003      |
      | manufacturerAuto | General Medical Device |                  | 10003      | Blood            |            |

  @regression @mdcm-162 @mdcm-485 @mdcm-374 @sprint5 @wip
  Scenario Outline: Users should be able to add devices to existing manufacturers and verify devices are added
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on random manufacturer with status "<status>"
    And I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | <gmdn>               |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | customMade             | <customMade>         |
      | relatedDeviceSterile   | true                 |
      | relatedDeviceMeasuring | true                 |
    And Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    And I view new task with link "Update Manufacturer Registration Request" for the new account
    Then Check task contains correct devices "<gmdn>" and other details
    And I assign the task to me and "approve" the generated task
    When I logout of the application
    And I am logged into appian as "<user>" user
#    And I go to list of manufacturers page
    When I go to list of manufacturers page and click on stored manufacturer
    Then Verify devices displayed and other details are correct
    And I should be able to view products related to stored devices
    Examples:
      | user             | logBackInAas | deviceType             | customMade | status     | gmdn            | riskClassification | notifiedBody |
      | manufacturerAuto | businessAuto | General Medical Device | true       | REGISTERED | Blood donor set |                    |              |
#      | authorisedRepAuto | businessAuto | General Medical Device | false       | REGISTERED | Blood vessel sizer | class1             | NB 0086 BSI  |
