Feature: As a business user, I want a task to be created each time a customer submits a request for a new account
  So that I know an action to review the request is required by myself or another team member


  @regression @mdcm-10 @mdcm-41 @mdcm-178 @sprint1 @sprint2 @bug
  Scenario Outline: Create new account as business user and approve tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task with link "<link>" should be removed from tasks list
    And The completed task status of new account should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    | link                |
      | businessNoor | manufacturer  | approve       | 1     | United Kingdom | New Account Request |
      | businessNoor | authorisedRep | approve       | 1     | Netherland     | New Account Request |


  @regression @mdcm-41 @mdcm-178 @sprint2 @bug
  Scenario Outline: Create new account as business user and reject tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and reject the task for following reason "<reason>"
#    Then The task should be removed from tasks list
    Then The task with link "<link>" should be removed from tasks list
    And The completed task status of new account should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see no account matches
    Examples:
      | user         | accountType   | count | countryName | reason                             | link                |
      | businessNoor | manufacturer  | 0     | Turkey      | Account already exists             | New Account Request |
      | businessNoor | authorisedRep | 0     | Estonia     | No authorisation evidence provided | New Account Request |

  @regression @3761 @sprint9 @ignore @wip
  Scenario Outline: Register my organisation button is displayed to UK Manufacturers who are not registered yet
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task with link "<link>" should be removed from tasks list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I goto list of manufacturers page again
    And I go to register my organisations page
    Examples:
      | user         | accountType  | logBackInAs      | approveReject | count | countryName    | link                |
      | businessNoor | manufacturer | manufacturerNoor | approve       | 1     | United Kingdom | New Account Request |
      | businessNoor | authorisedRep | authorisedRepNoor |approve       | 1     | Netherland     | New Account Request |


  @regression @mdcm-15 @mdcm-21 @mdcm-39 @mdcm-186 @mdcm-240 @sprint4 @sprint2 @sprint3 @sprint5 @bug
  Scenario Outline: Business users should be able to review and process manufacturer and device registration tasks
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
    #Then I should see stored manufacturer appear in the manufacturers list
    Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    Then I view new task with link "New Manufacturer Registration Request" for the new account
#    When I download the letter of designation
    And Check task contains correct devices "<gmdnDefinition>" and other details
    And I assign the task to me and "approve" the generated task
    Then The completed task status should update to "Completed"
    Examples:
      | user              | logBackInAas | accountType   | countryName | deviceType                         | gmdnDefinition      | customMade | listOfProductNames |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil  | Active Implantable Medical Devices | Desiccating chamber          | true       | setmeup1           |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Medical Devices | Desiccating chamber | true       | setmeup2           |


  @regression @mdcm-15 @mdcm-21 @mdcm-39 @mdcm-186 @sprint2 @sprint3 @sprint5 @3755 @sprint11 @bug
  Scenario Outline: Business users to review and process manufacturer and device registration task for IVD List A
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
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
    And The gmdn code or term is "displayed" in summary section
    And Proceed to payment and confirm submit device details
    #Then I should see stored manufacturer appear in the manufacturers list
#    Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    Then I view new task with link "New Manufacturer Registration Request" for the new account
    #When I download the letter of designation
    And Check task contains correct devices "<gmdnDefinition>" and other details
    And I assign the task to me and "approve" the generated task
    And The completed task status should update to "Completed"
    Examples:
      | user              | logBackInAas | accountType   | countryName | deviceType                 | gmdnDefinition        | riskClassification | listOfProductNames | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | ford,hyundai       | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | ford,honda         | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-161 @mdcm-232 @mdcm-164 @mdcm-240 @sprint4 @sprint6 @wip
  Scenario Outline: Register manufacturer as authorisedRep and verify status of letter of designation is correct
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
    When I logout of the application
    And I login to appian as "<logBackInAas>" user
    Then I view new task with link "<link>" for the new account
    And The status of designation letter should be "Awaiting Review"
    Examples:
      | user              | logBackInAas | accountType   | countryName | deviceType                         | gmdnDefinition      | customMade | listOfProductNames | link                                  |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Medical Devices | Desiccating chamber | true       | ford               | New Manufacturer Registration Request |
#      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Medical Devices | Blood vessel sizer | false      | fiesta             | New Manufacturer Registration Request |


  @regression @mdcm-41 @sprint2
  Scenario Outline: Verify WIP section shows newly created tasks and users can approve reject tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account in WIP page
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from WIP tasks list
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    |
      | businessNoor | authorisedRep | approve       | 1     | Netherland     |
      | businessNoor | manufacturer  | approve       | 1     | United Kingdom |
      | businessNoor | manufacturer  | reject        | 0     | Turkey         |
      | businessNoor | authorisedRep | reject        | 0     | Estonia        |


  @regression @mdcm-178 @sprint2
  Scenario Outline: Create new account and verify WIP task details are correct
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I should see a new task for the new account in WIP page
    Then validate task is displaying correct new account details
    Examples:
      | user         | accountType   | count | countryName | reason                             | link                |
      | businessNoor | manufacturer  | 0     | Turkey      | Account already exists             | New Account Request |
      | businessNoor | authorisedRep | 0     | Estonia     | No authorisation evidence provided | New Account Request |


  @regression @mdcm-263 @sprint6  @4088 @sprint11 @2185 @sprint8 @wip
  Scenario Outline: Verify only 1 task is created when we create NEW manufacturer with multiple devices
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType> |
      | gmdnDefinition | <gmdn1>      |
      | customMade     | true         |
    And I add another device to SELECTED manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | <gmdn2>      |
      | customMade             | false        |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
      | riskClassification     | class1       |
      | notifiedBody           | NB 0086 BSI  |
    And Proceed to payment and confirm submit device details
    #Then I should see stored manufacturer appear in the manufacturers list
    Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I go to WIP tasks page
    Then Verify the WIP entry details for the new account is correct
    When I view task for the new account in WIP page
    Then Task contains correct devices and products and other details for "<deviceType>"
    And Task shows devices which are arranged by device types
    And I assign the task to me and "<approveReject>" the generated task
#    Then The task should be removed from WIP tasks list
    Then The completed task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user              | logBackInAs  | accountType   | countryName | gmdn1                | gmdn2           | approveReject | deviceType             |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      | Blood weighing scale | Autopsy measure | approve       | General Medical Device |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Blood weighing scale | Autopsy measure | approve       | General Medical Device |


  @regression @4090 @sprint13
  Scenario Outline: Users should be able to search and filter tasks
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | <gmdn>                 |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
#    Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I go to WIP tasks page
    And I filter WIP tasks by "<filterBy>"
    And I filter WIP tasks by "<taskType>"
    Then Check the WIP entry details for the "<taskType>" task is correct
    When I view task for the new account in WIP page
    And I assign the task to me and "approve" the generated task
    Then The completed task status should update to "Completed"
    Examples:
      | user              | logBackInAs  | gmdn                 | filterBy | taskType |
      | manufacturerAuto | businessAuto | Blood weighing scale | orgName       | taskType |
      | authorisedRepAuto | businessAuto | Blood weighing scale | orgName  | taskType |
