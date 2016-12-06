Feature: As a business user, I want a task to be created each time a customer submits a request for a new account
  So that I know an action to review the request is required by myself or another team member


  @regression @mdcm-10 @mdcm-41 @mdcm-178
  Scenario Outline: Create new account and approve tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
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
      | businessAuto | manufacturer  | approve       | 1     | United Kingdom |
      | businessAuto | authorisedRep | approve       | 1     | Netherland     |


  @regression @mdcm-41 @mdcm-178
  Scenario Outline: Create new account and reject tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and reject the task for following reason "<reason>"
    Then The task should be removed from tasks list
    And The task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName | reason                             |
      | businessAuto | manufacturer  | reject        | 0     | Turkey      | Account already exists             |
      | businessAuto | authorisedRep | reject        | 0     | Estonia     | No authorisation evidence provided |


  @regression @mdcm-41
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
      | businessAuto | manufacturer  | approve       | 1     | United Kingdom |
      | businessAuto | authorisedRep | approve       | 1     | Netherland     |
      | businessAuto | manufacturer  | reject        | 0     | Turkey         |
      | businessAuto | authorisedRep | reject        | 0     | Estonia        |

  @regression @mdcm-15
  Scenario Outline: Business users should be able to review and process manufacturer and device registration
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
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    Then I should see a new task with link "New Manufacturer Registration Request" for the new account
    When I download the letter of designation
    And Check devices displayed and other details are correct
    And I assign the task to me and "approve" the generated task
    Then The task with link "New Manufacturer Registration Request" should be removed from tasks list
    Examples:
      | user             | logBackInAas | accountType  | countryName | deviceType                         | gmdnDefinition | customMade | listOfProductNames |
      | manufacturerAuto | businessAuto | manufacturer | Bangladesh  | Active Implantable Medical Devices | Adhesive       | true       | setmeup            |



  @regression @mdcm-15
  Scenario Outline: Business users to review and process manufacturer and device registration for IVD List A
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to newly created manufacturer with following data
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
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    Then I should see a new task with link "New Manufacturer Registration Request" for the new account
    When I download the letter of designation
    And Check devices displayed and other details are correct
    And I assign the task to me and "approve" the generated task
    Then The task with link "New Manufacturer Registration Request" should be removed from tasks list
    Examples:
      | user             | logBackInAas | accountType  | countryName | deviceType                 | gmdnDefinition | riskClassification | listOfProductNames | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | manufacturerAuto | businessAuto | manufacturer | Bangladesh  | In Vitro Diagnostic Device | Glucose        | list a             | ford,hyundai       | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
#      | manufacturerAuto | businessAuto | manufacturer | Bangladesh  | In Vitro Diagnostic Device | Glucose        | list a             | ford,honda         | ford        | focus        | NB 0086 BSI  | true              | true       | false         |
