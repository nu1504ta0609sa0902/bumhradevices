Feature: As a user, I want to view devices and product details associated with an account
  so that I can quickly verify correct device and related product information is correct

  @regression @mdcm-125 @_sprint6 @wip
  Scenario Outline: Business users should be able to view all the associated organisations related with an account
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    #Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I view new task with link "New Manufacturer Registration Request" for the new account
#    Then I should see associated organisations related to this account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    When I go to records page and click on "<page>"
    And I search for stored organisation in "<page>" page
    Then All organisation search result should return <count> matches
    Examples:
      | user              | logBackInAs  | accountType   | approveReject | count | countryName | link              | page              |
      | manufacturerAuto  | businessAuto | manufacturer  | approve       | 1     | Brazil      | Organisations | Organisations |
      | authorisedRepAuto | businessAuto | authorisedRep | approve       | 1     | Belarus     | Accounts          | Organisations |

  @mdcm-126 @readonly @_sprint1 @mdcm-125 @_sprint6 @wip
  Scenario Outline: As a business user I should be able to view specific account in all accounts page
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    And I search for account with following text "<accountInitials>"
    And I view a random account from search result
    Then I should see account displaying correct fields
    Examples:
      | user         | accountInitials | link     | pageHeading | columns                                                                                                          |
      | businessAuto | _AT             | Accounts | Accounts    | Organisation name,Account number,Organisation role,Contact name,Organisation address,Organisation country,Status |

  @regression @1924 @3271 @_sprint8 @wip
  Scenario Outline: As a business user I should be able to create new manufacturers and verify device and product details related to a manufacturer
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | <deviceType>         |
      | gmdnDefinition | Blood weighing scale |
      | customMade     | true                 |
    And Proceed to payment and confirm submit device details
    #Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I view new task with link "New Manufacturer Registration Request" for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    When I go to records page and click on "<page>"
    And I search for stored organisation in "<page>" page
#    And I perform a search for "<searchTerm>" in "<page>" page
    And I click on a random organisation link "<searchTerm>" in "<page>" page
    Then I should see business manufacturer details page for the manufacturer
    When I click on link "product details" and go to "devices" page
    Then I should see device table with devices
    When I click on a device for device type "<deviceType>"
    Then I should see correct information for device type "<deviceType>"
    Examples:
      | user              | logBackInAs  | accountType   | approveReject | countryName | page              | searchTerm        | deviceType             |
      | manufacturerAuto  | businessAuto | manufacturer  | approve       | Brazil      | Organisations | ManufacturerRT01  | General Medical Device |
      | authorisedRepAuto | businessAuto | authorisedRep | approve       | Belarus     | Organisations | AuthorisedRepRT01 | General Medical Device |

  @regression @1924 @3271 @_sprint8 @wip
  Scenario Outline: As a business user I should be able to verify device and product details related to a manufacturer
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
    And I click on a random organisation link "<searchTerm>" in "<page>" page
    Then I should see business manufacturer details page for the manufacturer
    When I click on link "product details" and go to "devices" page
    Then I should see device table with devices
    When I click on a device with link "heart" for device type "<deviceType>"
    Then I should see correct information for device type "<deviceType>"
    Examples:
      | user         | page              | searchTerm                     | deviceType             |
      | businessAuto | Accounts          | _AT        | General Medical Device |
      | businessAuto | Organisations | AuthorisedRepRT | General Medical Device |
