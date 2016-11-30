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
      | user             |
      | manufacturerAuto |
      | authorisedRepAuto |

  @regression @mdcm-14 @wip
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
    Examples:
      | user             | deviceType                 | gmdnDefinition | customMade | deviceSterile | deviceMeasuring | riskClassification | notifiedBody | packIncorporated | devicesCompatible |
      | manufacturerAuto | General Medical Device     | Adhesive       | false      | true          | true            | class1             | NB 0086 BSI  |                  |                   |
      | manufacturerAuto | General Medical Device     | Adhesive       | true       | true          | true            |                    |              |                  |                   |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        |            |               |                 | ivd general        |              |                  |                   |
      | manufacturerAuto | System or Procedure Pack   | Air sampling   | true       | true          | true            |                    |              | true             | true              |


  @regression @mdcm-14 @wip
  Scenario Outline: Users should be able to register new manufacturers with devices
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see stored manufacturer appear in the manufacturers list
    When I select the stored manufacturer
    #Then I should see the following link "Declare devices for"
    #When I go to add devices page for the stored manufacturer
    #Then I should see correct device types
    When I add a device to stored manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | Adhesive     |
      | customMade             | yes          |
      | relatedDeviceSterile   | yes          |
      | relatedDeviceMeasuring | yes          |
    Examples:
      | user             | accountType  | countryName | deviceType             |deviceType             |
      | manufacturerAuto | manufacturer | Bangladesh  | General Medical Device |General Medical Device |
      #| manufacturerAuto | authorisedRep | Netherland     |


  @regression @mdcm-14 @wip
  Scenario Outline: Users should be able to add devices to existing manufacturers
    Given I am logged into appian as "<user>" user
    And I go to register another manufacturer page
    When I select a random manufacturer from list
    When I go to add devices page for the stored manufacturer
    Then I should see correct device types
    When I add a device to selected manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | Adhesive     |
      | customMade             | yes          |
      | relatedDeviceSterile   | yes          |
      | relatedDeviceMeasuring | yes          |
    Examples:
      | user             | deviceType             |
      | manufacturerAuto | General Medical Device |


  @regression @mdcm-292 @wip
  Scenario Outline: Verify new product id is generated for each product submitted by manufacturer
    Given I am logged into appian as "<user>" user
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    And The task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    |
      | manufacturerAuto | manufacturer  | approve       | 1     | United Kingdom |
      | authorisedRepAuto | authorisedRep | approve       | 1     | Netherland     |


