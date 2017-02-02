Feature: As an account holder with access to the Device Registration Service
  I want to register a manufacturer and declare devices being manufacturer
  so that they can place the device for sale on the EU market

  @regression @readonly @mdcm-14 @mdcm-21 @sprint3 @sprint5
  Scenario Outline: Users should be able to view already registered manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    When I view a random manufacturer with status "<status>"
    Then I should see the correct manufacturer details
    Examples:
      | user              | status     |
      | manufacturerAuto  | Registered |
      | authorisedRepAuto | Registered |

  @regression @readonly @mdcm-14 @mdcm-21 @sprint3 @sprint5
  Scenario Outline: Verify correct device type options are displayed on add devices page
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    Then I should see correct device types
    Examples:
      | user              |
      | manufacturerAuto  |
      | authorisedRepAuto |


  @regression @mdcm-14 @mdcm-467 @mdcm-21 @sprint3 @sprint5
  Scenario Outline: Verify correct options are displayed on add devices page
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
#    And I click on random manufacturer with status "<status>"
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
    And The gmdn code or term is "displayed" in summary section
    Examples:
      | user             | status     | deviceType                 | gmdnDefinition                    | customMade | deviceSterile | deviceMeasuring | riskClassification | notifiedBody | packIncorporated | devicesCompatible |
      | manufacturerAuto | Registered | General Medical Device     | Blood weighing scale              | false      | true          | true            | class1             | NB 0086 BSI  |                  |                   |
      | manufacturerAuto | Registered | General Medical Device     | Blood                             | true       | true          | true            |                    |              |                  |                   |
      | manufacturerAuto | Registered | In Vitro Diagnostic Device | Androgen receptor IVD, calibrator |            |               |                 | ivd general        |              |                  |                   |
      | manufacturerAuto | Registered | System or Procedure Pack   | Desiccating chamber                      |            | true          | true            | class1             | NB 0086 BSI  | false            | true              |
      | manufacturerAuto | Registered | System or Procedure Pack   | Desiccating chamber                      |            | false         | true            |                    | NB 0086 BSI  | false            | true              |


  @regression @mdcm-14 @mdcm-489 @sprint3 @sprint5 @mdcm-134 @sprint6
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


  @regression @mdcm-134 @sprint6 @bug
  Scenario Outline: Users should be able to remove devices from manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | <gmdn1>      |
      | customMade             | true         |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
    And I add another device to SELECTED manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | <gmdn2>      |
      | customMade             | true         |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
    Then I should see option to add another device
    When I remove ALL the stored device with gmdn code or definition
    Then I should see option to add another device
    Examples:
      | user             | deviceType             | gmdn1                | gmdn2           |
      | manufacturerAuto | General Medical Device | Blood weighing scale | Autopsy measure |
#      | authorisedRepAuto | General Medical Device |


  @regression @mdcm-489 @sprint5 @mdcm-148 @sprint7
  Scenario Outline: Verify product details after adding IVD products
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | riskClassification | <riskClassification> |
      | notifiedBody       | <notifiedBody>       |
      | productMake        | <productMake>        |
      | productModel       | <productModel>       |
      | subjectToPerfEval  | <subjectToPerfEval>  |
      | newProduct         | <newProduct>         |
      | conformsToCTS      | <conformsToCTS>      |
      | listOfProductNames | <listOfProductNames> |
    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    And Verify name make model and other details are correct
    Examples:
      | user              | deviceType                 | gmdnDefinition        | riskClassification | listOfProductNames | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | manufacturerAuto  | In Vitro Diagnostic Device | Androgen receptor IVD | list a             |                    | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | authorisedRepAuto | In Vitro Diagnostic Device | Androgen receptor IVD | list a             |                    | ford        | focus        | NB 0086 BSI  | true              | true       | false         |
      | authorisedRepAuto | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | General Motors     | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-489 @sprint5 @mdcm-134 @sprint6 @mdcm-148 @sprint7
  Scenario Outline: Users should be able to add and remove devices using GMDN code from existing manufacturers
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
#    When I remove the device with gmdn "<gmdnCodeD2>" code
    When I remove the stored device with gmdn code or definition
#    Then I should see option to add another device
    Then The gmdn code or term is "removed" in summary section
    Examples:
      | user             | deviceType             | gmdnDefinitionD1 | gmdnCodeD1 | gmdnDefinitionD2  | gmdnCodeD2 |
      | manufacturerAuto | General Medical Device | Blood            |            |                   | 10003      |
      | manufacturerAuto | General Medical Device |                  | 10003      | Housekeeping soap |            |

  @regression @mdcm-485 @mdcm-374 @sprint5 @wip @bug
  Scenario Outline: Users should be able to add devices to existing manufacturers and verify devices are added
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    And I view a random manufacturer with status "<status>"
    And I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | <gmdn>               |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | customMade             | <customMade>         |
      | relatedDeviceSterile   | <deviceSterile>      |
      | relatedDeviceMeasuring | <deviceMeasuring>    |
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
      | user              | logBackInAas | deviceType             | customMade | deviceSterile | deviceMeasuring | status     | gmdn                 | riskClassification | notifiedBody |
      | authorisedRepAuto | businessAuto | General Medical Device | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  |
      | manufacturerAuto  | businessAuto | General Medical Device | true       | false         | false           | Registered | Blood weighing scale |                    |              |


  @regression @mdcm-485 @sprint5 @wip
  Scenario Outline: Users should be able to VIEW additional info related to registered manufacturers
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    And I view a random manufacturer with status "<status>"
    Then I should see organisation details
    And I should see contact person details
    Examples:
      | user              | status     |
      | manufacturerAuto  | Registered |
      | authorisedRepAuto | Registered |