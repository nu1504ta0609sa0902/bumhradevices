Feature: As an account holder with access to the Device Registration Service
  I want to register a manufacturer and declare devices being manufacturer
  so that they can place the device for sale on the EU market

  @regression @mdcm-14
  Scenario Outline: Users should be able to view already registered manufacturers
    Given I am logged into appian as "<user>" user
    And I go to manufacturer registration page
    When I click on a registered manufacturer
    Then I should see the correct manufacturer details
    Examples:
      | user              |
      | manufacturerAuto  |
      | authorisedRepAuto |

  @regression @mdcm-14
  Scenario Outline: Verify correct options are displayed on add devices page
    Given I am logged into appian as "<user>" user
    And I go to register another manufacturer page
    When I select a random manufacturer from list
    When I go to add devices page for the stored manufacturer
    Then I should see correct device types
    When I add a device to selected manufacturer with following data
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
      | manufacturerAuto | General Medical Device     | Adhesive       | false      | true          | true            | class1             | NB 0086 BSI  |                  |                   |
      | manufacturerAuto | General Medical Device     | Adhesive       | true       | true          | true            |                    |              |                  |                   |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        |            |               |                 | ivd general        |              |                  |                   |
      | manufacturerAuto | System or Procedure Pack   | Air sampling   | true       | true          | true            |                    |              | true             | true              |


  @regression @mdcm-14
  Scenario Outline: Users should be able to register new manufacturers with devices
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see stored manufacturer appear in the manufacturers list
    When I select the stored manufacturer
    Then I should see the following link "Declare devices for"
    When I add a device to stored manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | Adhesive     |
      | customMade             | true          |
      | relatedDeviceSterile   | true          |
      | relatedDeviceMeasuring | true          |
    Then I should see option to add another device
    Examples:
      | user             | accountType  | countryName | deviceType             | deviceType             |
      | manufacturerAuto | manufacturer | Bangladesh  | General Medical Device | General Medical Device |
      | authorisedRepAuto | authorisedRep | Bangladesh  | General Medical Device | General Medical Device |


  @regression @mdcm-14
  Scenario Outline: Users should be able to add devices to existing manufacturers
    Given I am logged into appian as "<user>" user
    And I go to register another manufacturer page
    When I select a random manufacturer from list
    When I go to add devices page for the stored manufacturer
    Then I should see correct device types
    When I add a device to selected manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | Adhesive     |
      | customMade             | true          |
      | relatedDeviceSterile   | true          |
      | relatedDeviceMeasuring | true          |
    Then I should see option to add another device
    Examples:
      | user             | deviceType             |
      | manufacturerAuto | General Medical Device |

