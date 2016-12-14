Feature: As a business and account holder, I want to be able to update party details associated with an account
  so that customers unable to use the portal can still provide the latest information on the account, and I can correct minor errors made by the customer

  @regression @mdcm-175
  Scenario Outline: Business users should be able to edit and update manufacturer account details
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    When I search for account with following text "<searchTerm>"
    Then I should see at least 1 account matches
    When I view a randomly searched account and update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in the account page
    Examples:
      | user         | link     | searchTerm       | keyValuePairs                                          |
      | businessAuto | Accounts | ManufacturerRT01 | address.line1,address.line2,city.town,country,postcode |
      | businessAuto | Accounts | ManufacturerRT01 | org.telephone,org.fax,job.title                        |
      | businessAuto | Accounts | ManufacturerRT00 | org.name                                               |


  @regression @mdcm-175
  Scenario Outline: Business users should be able to edit and update authorisedRep account details
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    When I search for account with following text "<searchTerm>"
    Then I should see at least 1 account matches
    When I view a randomly searched account and update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in the account page
    Examples:
      | user         | link     | searchTerm        | keyValuePairs                                  |
      | businessAuto | Accounts | AuthorisedRepRT01 | address.line1,address.line2,city.town,postcode |
      | businessAuto | Accounts | AuthorisedRepRT01 | org.telephone,org.fax,job.title                |
      | businessAuto | Accounts | AuthorisedRepRT00 | org.name                                       |


  @regression @mdcm-149 @mdcm-171 @mdcm-162
  Scenario Outline: Manufacturer and authorisedRep user should be able to update account contact details
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I update the contact person details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my accounts page
    Examples:
      | user              | keyValuePairs                                                                                      |
      | authorisedRepAuto | contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone |
      | manufacturerAuto  | contact.job.title,contact.email,contact.telephone,contact.firstname,contact.lastname               |


  @regression @mdcm-13 @mdcm-171 @mdcm-162 @wip
  Scenario Outline: Manufacturer and authorisedRep user should be able to update organisation details
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I update the organisation details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my accounts page
    Examples:
      | user              | keyValuePairs                                                                      |
      | authorisedRepAuto | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website          |
      | manufacturerAuto  | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website          |
      | authorisedRepNoor | org.name,org.address1,org.address2,org.city,org.postcode,org.telephone,org.website |
      | manufacturerNoor  | org.name,org.address1,org.address2,org.city,org.postcode,org.telephone,org.website |


  @regression @mdcm-261 @mdcm-277
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


  @regression @mdcm-21 @mdcm-485 @wip
  Scenario Outline: Manufacturer and authorisedRep user should be able to update manufacturer details
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on random manufacturer with status "<status>"
    And I update the manufacturer details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my manufacturer details page
    Examples:
      | user              | keyValuePairs                                                             | status     |
      | manufacturerAuto  | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website          | REGISTERED |
      | manufacturerAuto  | contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone | REGISTERED |
#      | authorisedRepAuto | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website | REGISTERED |
#      | authorisedRepAuto | contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone          | REGISTERED |

