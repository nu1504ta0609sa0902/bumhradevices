Feature: Write PC for devices

  @setup
  Scenario Outline: Business users should be able to review manufacturer registration 1
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to newly created manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | listOfProductNames | <listOfProductNames> |
    Then I should see stored manufacturer appear in the manufacturers list
    #When I select the stored manufacturer
    #Then I should see the following link "Declare devices for"
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    Then I should see a new task with link "New Manufacturer Registration Request" for the new account
    When I assign the task to me and "approve" the generated task
    When I download the letter of designation
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    And The task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user             | logBackInAas | accountType  | countryName | deviceType                         | gmdnDefinition | customMade | listOfProductNames |
      | manufacturerAuto | businessAuto | manufacturer | Bangladesh  | Active Implantable Medical Devices | Adhesive       | true       | setmeup            |
      #| authorisedRepAuto | businessAuto | manufacturer | Bangladesh   | Active Implantable Medical Devices | Adhesive       | true       | ford,hyundai       |


  @setup
  Scenario Outline: Business users should be able to review manufacturer registration 2
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see stored manufacturer appear in the manufacturers list
    Examples:
      | user             | user2        | accountType  | countryName | deviceType             | deviceType             |
      | manufacturerAuto | businessAuto | manufacturer | Bangladesh  | General Medical Device | General Medical Device |
      #| authorisedRepAuto | businessAuto | manufacturer | Bangladesh  | General Medical Device | General Medical Device |