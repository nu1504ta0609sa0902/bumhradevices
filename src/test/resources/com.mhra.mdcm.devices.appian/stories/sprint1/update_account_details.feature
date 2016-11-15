Feature: As a business user, I want to be able to update party details associated with an account
  so that customers unable to use the portal can still provide the latest information on the account, and I can correct minor errors made by the customer

  @mdcm-175 @regression
  Scenario Outline: Customers should be able to update account details
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see the following columns for "<link>" page
      | columns | <columns> |
    When I search for account with following text "<searchTerm>"
    Then I should see at least 1 account matches
    When I select a random account and update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in the account page
    Examples:
      | user         | link     | keyValuePairs                          | searchTerm       | columns                                                                                                             |
      | businessAuto | Accounts | job.title=AutoChangedManufacturerTitle | ManufacturerTest | Organisation name,Account number,Organisation role, Contact name, Organisation address,Organisation country, Status |
#      | businessAuto | Accounts | job.title=AutoChangedAuthorisedRepTitle | AuthorisedRep | Organisation name,Account number,Organisation role, Contact name, Organisation address,Organisation country, status |
