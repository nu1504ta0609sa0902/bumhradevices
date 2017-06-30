Feature: Reset all the information related to a manufacturer and authorisedRep


#  DON'T USE THE AUTOMATION ACCOUNT WITH THIS, UNLESS YOU WANT ALL THE DATA TO BE CLEANED
  @setup
  Scenario Outline: Reset manufacturer and authorisedRep account using Business Test Harness
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType           | <accountType>           |
      | accountNameBeginsWith | <accountNameBeginsWith> |
      | countryName           | <countryName>           |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task with link "<link>" should be removed from tasks list
    #And The completed task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    | link                | accountNameBeginsWith    |
      | businessNoor | manufacturer  | approve       | 1     | United Kingdom | New Account Request | ManufacturerAccountRT00  |
      | businessNoor | authorisedRep | approve       | 1     | Netherland     | New Account Request | AuthorisedRepAccountRT00 |


  @setup
  Scenario Outline: AuthorisedRep update country to UK
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I update the organisation details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my accounts page
    Examples:
      | user              | keyValuePairs |
      | manufacturerAuto  | org.country   |
      | authorisedRepNoor | org.country   |


  @setup
  Scenario Outline: Provide Indication of Devices Made By Manufacturer and AuthorisedRep
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And Provide indication of devices made
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    Then I view new task with link "New Service Request" for the new account
    When I assign the task to me and "approve" the generated task
    Examples:
      | user              | logBackInAs |
      | manufacturerNoor  | businessNoor |
      | authorisedRepNoor | businessNoor |


  @setup
  Scenario Outline: Able to register and Provide Indication of Devices Made By AuthorisedRep
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And Provide indication of devices made
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    Then I view new task with link "New Service Request" for the new account
    When I download the letter of designation
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    Examples:
      | user              | logBackInAs | accountType   | countryName |
      | authorisedRepNoor | businessNoor | authorisedRep | Bangladesh  |

  @setup
  Scenario Outline: Able to register and Provide Indication of Devices Made By Manufacturers
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And Provide indication of devices made
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    Then I view new task with link "New Service Request" for the new account
    When I download the letter of designation
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    Examples:
      | user             | logBackInAs | accountType  | countryName |
      | manufacturerNoor | businessNoor | manufacturer | Bangladesh  |


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
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And Check task contains correct devices "<gmdnDefinition>" and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user             | logBackInAs | accountType  | countryName    | deviceType                | gmdnDefinition      | customMade | listOfProductNames |
      | manufacturerAuto | businessAuto | manufacturer | United Kingdom | Active Implantable Device | Desiccating chamber | true       | setmeup            |


  @setup
  Scenario Outline: Business users should be able to review authorisedRep registration 1
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
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And Check task contains correct devices "<gmdnDefinition>" and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user              | logBackInAs | accountType   | countryName | deviceType                | gmdnDefinition      | customMade | listOfProductNames |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Device | Desiccating chamber | true       | ford,hyundai       |


  @setup
  Scenario Outline: Setup an account so we can modifiy the organisation name
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    When I search for account with following text "<searchTerm>"
    Then I should see at least 1 account matches
    When I view a randomly searched account and update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in the account page
    Examples:
      | user         | link     | searchTerm        | keyValuePairs |
      | businessAuto | Accounts | ManufacturerRT00  | org.name      |
      | businessAuto | Accounts | AuthorisedRepRT00 | org.name      |


  Scenario Outline: Clear up task residue
    Given I am logged into appian as "<user>" user
    When I go to tasks page
    And I clear up any old taks with name "<searchTerm1>"
    And I clear up any old taks with name "<searchTerm2>"
    Examples:
      | user         | link     | searchTerm       | searchTerm2       |
      | businessAuto | Accounts | ManufacturerRT01 | AuthorisedRepRT01 |
