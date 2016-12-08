Feature: As a customer I want to register manufacturers
  and upload proof so that I am an authorised representative for an organisation when
  so that I am granted access to that and can then register overseas manufacturers on their behalf


  @regression @mdcm-161 @mdcm-232 @wip
  Scenario Outline: Users should be able to upload proof they are authorised reps
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see stored manufacturer appear in the manufacturers list
    When I am logged into appian as "<user2>" user
    Then I should see a new task for the new account
    When I download the letter of designation
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    And The task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user              | user2        | accountType  | countryName |
      | manufacturerAuto  | businessAuto | manufacturer | Bangladesh  |
      | authorisedRepAuto | businessAuto | manufacturer | Bangladesh  |


  @regression @mdcm-292 @wip
  Scenario Outline: Verify new product id is generated for each product submitted by manufacturer
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
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
      | user              | accountType   | approveReject | count | countryName    |
      | manufacturerAuto  | manufacturer  | approve       | 1     | United Kingdom |
      | authorisedRepAuto | authorisedRep | approve       | 1     | Netherland     |