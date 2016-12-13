Feature: As a customer I want to register new manufacturers with devices
  and upload proof so that I am an authorised representative for an organisation when
  so that I am granted access to that and can then register overseas manufacturers on their behalf


  @regression @mdcm-14 @mdcm-39
  Scenario Outline: Users should be able to register new manufacturers with devices
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | Blood     |
      | customMade             | <customMade> |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
    And Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
    Examples:
      | user              | accountType   | countryName | deviceType             | deviceType             | customMade |
      | manufacturerAuto  | manufacturer  | Bangladesh  | General Medical Device | General Medical Device | true       |
      | authorisedRepAuto | authorisedRep | Bangladesh  | General Medical Device | General Medical Device | false      |

