Feature: As a customer I want to register new manufacturers with devices
  and upload proof so that I am an authorised representative for an organisation when
  so that I am granted access to that and can then register overseas manufacturers on their behalf


  @regression @mdcm-14 @mdcm-39 @mdcm-496 @sprint3 @sprint5
  Scenario Outline: Users should be able to register new manufacturers with devices
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | Blood                |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | customMade             | <customMade>         |
      | relatedDeviceSterile   | true                 |
      | relatedDeviceMeasuring | true                 |
    And Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
    Examples:
      | user              | accountType   | countryName | deviceType             | deviceType             | customMade | riskClassification | notifiedBody |
      | manufacturerAuto  | manufacturer  | Bangladesh  | General Medical Device | General Medical Device | true       |                    |              |
      | authorisedRepAuto | authorisedRep | Bangladesh  | General Medical Device | General Medical Device | false      | class1             | NB 0086 BSI  |


  @regression @mdcm-162 @mdcm-485 @mdcm-374 @mdcm-186 @sprint2 @sprint5 @wip
  Scenario Outline: Users should be able to register new manufacturers with devices  and verify devices are added
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | <gmdn>               |
      | customMade             | <customMade>         |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | relatedDeviceSterile   | true                 |
      | relatedDeviceMeasuring | true                 |
    And Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    And I view new task with link "New Manufacturer Registration Request" for the new account
    Then Check task contains correct devices "<gmdn>" and other details
    And I assign the task to me and "approve" the generated task
    When I logout of the application
    And I am logged into appian as "<user>" user
#    And I go to list of manufacturers page
    When I go to list of manufacturers page and click on stored manufacturer
#    Then Verify devices displayed and other details are correct
    And I should be able to view products related to stored devices
    Examples:
      | user              | logBackInAas | accountType   | countryName | deviceType             | customMade | gmdn               | riskClassification | notifiedBody |
      | manufacturerAuto  | businessAuto | manufacturer  | Bangladesh  | General Medical Device | true       | Blood donor set    |                    |              |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | General Medical Device | false      | Blood vessel sizer | class1             | NB 0086 BSI  |

  @regression @mdcm-39 @sprint5
  Scenario Outline: Verify manufacturers landing page contents
    Given I am logged into appian as "<user>" user
    When I go to portal page
    Then Landing page displays correct title and contact name
    Examples:
      | user              | accountType   | countryName | deviceType             | deviceType             | customMade | riskClassification | notifiedBody |
      | manufacturerAuto  | manufacturer  | Bangladesh  | General Medical Device | General Medical Device | true       |                    |              |
      | authorisedRepAuto | authorisedRep | Bangladesh  | General Medical Device | General Medical Device | false      | class1             | NB 0086 BSI  |