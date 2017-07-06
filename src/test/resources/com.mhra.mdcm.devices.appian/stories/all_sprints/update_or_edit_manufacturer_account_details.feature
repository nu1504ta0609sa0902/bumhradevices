Feature: As a business user, I want to be able to update party details associated with an account
  so that customers unable to use the portal can still provide the latest information on the account, and I can correct minor errors made by the customer

  @regression @mdcm-175 @2265 @_sprint2
  Scenario Outline: Business users should be able to edit and update manufacturer account details
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    When I search for account with following text "<searchTerm>"
    Then I should see at least 1 account matches
    When I view a randomly searched account and update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in the account page
    Examples:
      | user         | link     | searchTerm              | keyValuePairs                                          |
      | businessAuto | Accounts | ManufacturerAccountRT01 | address.line1,address.line2,city.town,country,postcode |
      | businessAuto | Accounts | ManufacturerAccountRT01 | org.telephone,org.fax,job.title                        |
      #| businessAuto | Accounts | ManufacturerAccountRT00 | org.name                                               |


  @regression @mdcm-175 @2265 @_sprint2
  Scenario Outline: Business users should be able to edit and update authorisedRep account details
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    When I search for account with following text "<searchTerm>"
    Then I should see at least 1 account matches
    When I view a randomly searched account and update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in the account page
    Examples:
      | user         | link     | searchTerm               | keyValuePairs                                  |
      | businessAuto | Accounts | AuthorisedRepAccountRT01 | address.line1,address.line2,city.town,postcode |
      | businessAuto | Accounts | AuthorisedRepAccountRT01 | org.telephone,org.fax,job.title                |
      #| businessAuto | Accounts | AuthorisedRepAccountRT00 | org.name                                       |


  @6086 @_sprint22 @2107 @_sprint24 @wip
  Scenario Outline: Manufacturer and authorisedRep user should be able to add new contacts
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I add a new contact person with random data
    And I view the newly created contact person
    Then I should see the contact person information on the page
#    Then I should see the changes "<keyValuePairs>" in my accounts page
    And I should see creation and association dates
    Examples:
      | user              | keyValuePairs                                                                                      |
      | authorisedRepAuto | contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone |
      | manufacturerAuto  | contact.job.title,contact.email,contact.telephone,contact.firstname,contact.lastname               |


  @6091 @6086 @_sprint22
  Scenario Outline: Manufacturer and authorisedRep user should be able to remove contacts person details
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I add a new contact person with random data
    And I remove the newly created contact person
    Then I should not see the contact person information on the page
    Examples:
      | user              |
      | authorisedRepAuto |
      | manufacturerAuto  |


  @mdcm-149 @2283 @mdcm-171 @2269 @mdcm-162 @2275 @mdcm-164 @2274 @_sprint3 @_sprint5 @_sprint6 @6089 @_sprint22 @2107 @_sprint24 @bug
  Scenario Outline: Manufacturer and authorisedRep user should be able to update account contact details
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I update the contact person details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my accounts page
    And I should see creation and association dates
    Examples:
      | user              | keyValuePairs                                                                                      |
      | authorisedRepAuto | contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone |
      | manufacturerAuto  | contact.job.title,contact.email,contact.telephone,contact.firstname,contact.lastname               |


  @mdcm-13 @mdcm-171 @2269 @mdcm-162 @2275 @mdcm-164 @2274 @_sprint4 @_sprint5 @_sprint6 @6089 @_sprint22 @wip @bug
  Scenario Outline: Manufacturer and authorisedRep user should be able to update organisation details
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I update the organisation details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my accounts page
    And I should see creation and association dates
    Examples:
      | user              | keyValuePairs                                                             |
      | authorisedRepAuto | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website |
      | manufacturerAuto  | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website |
      | authorisedRepAuto | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website |
      | manufacturerAuto  | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website |


  @readonly @mdcm-171 @2269 @mdcm-261 @2199 @mdcm-277 @_sprint4 @_sprint5 @bug
  Scenario Outline: Verify correct roles are displayed for approved UK account holder
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    Then I should see the correct "<expectedRoles>" roles
    When I click on edit "Organisation" details
    And Address type is not editable
    Examples:
      | user              | expectedRoles                          |
      | authorisedRepAuto | Authorised Representative              |
      | manufacturerAuto  | Manufacturer,Authorised Representative |


  @regression @readonly @mdcm-21 @2323 @3761 @_sprint9 @_sprint5
  Scenario Outline: Verify list of manufacturer table showing correct details
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    Then I should see the following columns for "<expectedHeadings>" list of manufacturer table
    And I click on random manufacturer with status "<status>"
    Examples:
      | user              | expectedHeadings                                      | status     |
      | manufacturerAuto  | Manufacturer name,Address,Country,Registration status | Registered |
      | authorisedRepAuto | Manufacturer name,Address,Country,Registration status | Registered |


  @mdcm-21 @2323 @mdcm-162 @2275 @mdcm-171 @2269 @mdcm-485 @2030 @_sprint5 @wip @ignore
  Scenario Outline: Manufacturer and authorisedRep user should be able to update manufacturer details
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on random manufacturer with status "<status>"
    And I update the manufacturer details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my manufacturer details page
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout and log back into appian as "<logBackInAs>" user
    Then The task status in AWIP page should be "Completed" for the new account
    And validate task is displaying correct new account details
    Examples:
      | user              | logBackInAs  | keyValuePairs                                                                                      | status     |
      | manufacturerAuto  | businessAuto | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website                          | Registered |
      | manufacturerAuto  | businessAuto | contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone | Registered |
      | authorisedRepAuto | businessAuto | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website                          | Registered |
      | authorisedRepAuto | businessAuto | contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone | Registered |


  @regression @mdcm-263 @2197 @_sprint6 @mdcm-275 @2188 @_sprint7 @4088 @_sprint11 @2185 @3104 @_sprint8 @bug
  Scenario Outline: Verify only 1 task is created when user updates EXISTING manufacturer with multiple devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    #And I click on random manufacturer with status "<status>" to add device
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
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    Then I should see correct device details
    And Check task contains correct devices "<gmdn1>" and other details
    Then Task contains correct devices and products and other details for "<deviceType>"
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user              | logBackInAs  | status         | gmdn1                | gmdn2           | approveReject | deviceType             |
      | authorisedRepAuto | businessAuto | Registered     | Blood weighing scale | Autopsy measure | approve       | General Medical Device |
      | authorisedRepAuto | businessAuto | Not Registered | Blood weighing scale | Autopsy measure | reject        | General Medical Device |
      | manufacturerAuto  | businessAuto | Registered     | Blood weighing scale | Autopsy measure | approve       | General Medical Device |
      | manufacturerAuto  | businessAuto | Not Registered | Blood weighing scale | Autopsy measure | reject        | General Medical Device |

