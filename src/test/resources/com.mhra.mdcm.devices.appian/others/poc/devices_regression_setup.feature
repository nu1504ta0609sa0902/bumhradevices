Feature: Write PC for devices


  @setup
  Scenario Outline: Provide Indication of Devices Made By Manufacturer
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And Provide indication of devices made
    And I am logged into appian as "<logBackInAas>" user
    Then I view new task with link "New Service Request" for the new account
    When I assign the task to me and "approve" the generated task
    Examples:
      | user             | logBackInAas | accountType  | countryName | deviceType                         | gmdnDefinition | customMade | listOfProductNames |
      | manufacturerAuto | businessAuto | manufacturer | Bangladesh  | Active Implantable Medical Devices | Blood       | true       | setmeup            |


  @setup
  Scenario Outline: AuthorisedRep update country to UK
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I update the organisation details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my accounts page
    Examples:
      | user              | keyValuePairs   |
      | authorisedRepAuto | org.country |

  @setup
  Scenario Outline: Provide Indication of Devices Made By AuthorisedRep
    Given I am logged into appian as "<user>" user
    And I go to register a new authorisedRep page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And Provide indication of devices made
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    Then I view new task with link "New Service Request" for the new account
    When I download the letter of designation
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    Examples:
      | user              | logBackInAas | accountType   | countryName | deviceType                         | gmdnDefinition | customMade | listOfProductNames |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Medical Devices | Blood       | true       | setmeup            |


  @setup
  Scenario Outline: Business users should be able to review manufacturer registration 1
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | listOfProductNames | <listOfProductNames> |
    And Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    Then I view new task with link "New Manufacturer Registration Request" for the new account
    When I download the letter of designation
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    #And The task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user             | logBackInAas | accountType  | countryName    | deviceType                         | gmdnDefinition | customMade | listOfProductNames |
      | manufacturerAuto | businessAuto | manufacturer | United Kingdom | Active Implantable Medical Devices | Blood       | true       | setmeup            |


  @setup
  Scenario Outline: Business users should be able to review authorisedRep registration 1
    Given I am logged into appian as "<user>" user
    And I go to register a new authorisedRep page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | listOfProductNames | <listOfProductNames> |
    And Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    Then I view new task with link "New Manufacturer Registration Request" for the new account
    When I download the letter of designation
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    #And The task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user              | logBackInAas | accountType   | countryName | deviceType                         | gmdnDefinition | customMade | listOfProductNames |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Medical Devices | Blood       | true       | ford,hyundai       |


#  @setup
#  Scenario Outline: Business users should be able to review manufacturer registration 2
#    Given I am logged into appian as "<user>" user
#    And I go to register a new manufacturer page
#    When I create a new manufacturer using manufacturer test harness page with following data
#      | accountType | <accountType> |
#      | countryName | <countryName> |
#    Then I should see stored manufacturer appear in the manufacturers list
#    Examples:
#      | user             | user2        | accountType  | countryName | deviceType             | deviceType             |
#      | manufacturerAuto | businessAuto | manufacturer | Bangladesh  | General Medical Device | General Medical Device |
#      #| authorisedRepAuto | businessAuto | manufacturer | Bangladesh  | General Medical Device | General Medical Device |