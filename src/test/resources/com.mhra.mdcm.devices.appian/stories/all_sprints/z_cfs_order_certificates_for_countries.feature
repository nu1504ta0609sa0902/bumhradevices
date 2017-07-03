Feature: As a UK based organisation I need to obtain a CERTIFICATE OF FREE SALE to export medical devices to non-EU countries

  @1845 @1945 @_sprint18
  Scenario: Check application reference is correct format
    Given I am logged into appian as "businessAuto" user
    And I go to application WIP page
    Then Check the application reference number format is valid


  @1974 @_sprint15
  Scenario: Users should be able to go to cfs page
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS


  @5571 @3856 @_sprint18
  Scenario: Users should be able to go to BACK from the application
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    And I fill out the form called tell us about your organisation
    And I click on the back button
    Then I should see an alert box asking for confirmation
    When I click "yes" on the alert box
    Then I should see a list of manufacturers available for CFS


  @5571 @4203 @4698 @_sprint15 @_sprint18
  Scenario Outline: Users should be able to go to BACK from the application 2
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I goto add a new cfs manufacturer page
    And I click on the back button
    Then I should see an alert box asking for confirmation
    When I click "<alertOption>" on the alert box
    Then I should see a list of manufacturers available for CFS
    Examples:
      | user             | alertOption |
      | manufacturerAuto | Yes         |
      | manufacturerAuto | No          |


  @4330 @4203 @_sprint15 @_sprint16
  Scenario: Users should be able to tell what stage of device registration they are in
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I goto add a new cfs manufacturer page
    Then I should see current stage of indication


  @5207 @5578 @_sprint16 @_sprint18
  Scenario Outline: Error messages should be displayed to user for certain combinations
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "<searchTerm>" which needs cfs
    And I try to add a device to SELECTED CFS manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | riskClassification | <riskClassification> |
    Then I should see the following "<errorMsg>" error message
    Examples:
      | user             | searchTerm | errorMsg                                 | deviceType                 | gmdnDefinition       | customMade | riskClassification |
      | manufacturerAuto | AccountST  | This device must be registered with MHRA | General Medical Device     | Blood weighing scale | true       |                    |
      | manufacturerAuto | AccountST  | This device must be registered with MHRA | General Medical Device     | Blood weighing scale | false      | class1             |
      | manufacturerAuto | AccountST  | This device must be registered with MHRA | Active Implantable Device  | Blood weighing scale | true       |                    |
      | manufacturerAuto | AccountST  | This device must be registered with MHRA | In Vitro Diagnostic Device |                      |            |                    |
      | manufacturerAuto | AccountST  | This device must be registered with MHRA | System or Procedure Pack   |                      |            |                    |


  @1974 @1978 @4704 @_sprint15
  Scenario Outline: Users should be able to go to cfs page and add to a random manufacturer from the list
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | <country> |
      | noOfCFS     | <noCFS>   |
    Then I should see the correct details in cfs order review page
    When I submit payment for the CFS
    Examples:
      | country    | noCFS |
      | Brazil     | 15    |
      | Bangladesh | 10    |


  @1974 @1978 @4704 @_sprint15 @5499 @_sprint17 @5980 @1958 @1960 @_sprint22 @wip
  Scenario: Users should be able to go to edit list of devices added for initial CFS process
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | Brazil |
      | noOfCFS     | 10     |
    Then I should see the correct details in cfs order review page
    When I edit the list of devices added for CFS
    Then I should see the correct details in cfs order review page
    When I save cfs order application for later
    Then I should see application tab showing my application with correct details


  @1974 @1978 @_sprint15 @5499 @_sprint17 @5980 @1958 @1960 @_sprint22
  Scenario: Users should be able to go to edit country and number of certificates
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | Brazil |
      | noOfCFS     | 10     |
    Then I should see the correct details in cfs order review page
    When I update the country to "Bangladesh" and number of certificates to 9
    Then I should see the correct details in cfs order review page
    When I save cfs order application for later
    Then I should see application tab showing my application with correct details


  @1974 @1978 @5578 @_sprint15 @_sprint18
  Scenario Outline: Users should be able to order CFS for multiple countries
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for multiple countries with following data
      | listOfCFSCountryPair | <countryAndCertificateNumber> |
    Then I should see correct details for all the countries and certificate in cfs order review page
    When I submit payment for the CFS
    Examples:
      | countryAndCertificateNumber                                |
      | Switzerland=5,Norway=10,British Virgin=15,British Indian=1 |
      | Bangladesh=5,Brazil=2,United States=3                      |
      | Turkey=5,Iceland=10,United States=20,Liechtenstein=20      |

  @1992 @5960 @_sprint21 @6012 @_sprint22
  Scenario Outline: Users can search for products and order CFS for already registered manufacturers
    Given I am logged into appian as "<logInAs>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I search for registered manufacturer "<searchTerm>"
    And I click on a random organisation which needs cfs
    And I search for product by "medical device name" for the value "NoItemShouldMatchThis"
    Then I should see 0 products matching search results
    And I search by "medical device name" for the value "random" and order cfs for a country with following data
      | countryName | <country> |
      | noOfCFS     | <noCFS>   |
    Then I should see the correct details in cfs order review page
    When I submit payment for the CFS
    Examples:
      | country    | noCFS | logInAs           | searchTerm            |
      | Brazil     | 15    | manufacturerAuto  | ManufacturerRT01Test  |
      | Bangladesh | 10    | authorisedRepAuto | AuthorisedRepRT01Test |

  @1992 @5349 @_sprint21 @6012 @_sprint22
  Scenario Outline: Users should be able to search and filter for products and devices
    Given I am logged into appian as "<logInAs>" user
# Submit a new CFS manufacturer application
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Desiccating chamber       |
      | customMade     | false                     |
      | notifiedBody   | NB 0086 BSI               |
      | productName    | Product1NU1               |
      | productModel   | Model1NU1                 |
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Free Sale"
# Now verify search and filter functionalities
    Given I am logged into appian as "<logInAs>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I search for registered manufacturer ""
    And I click on a random organisation which needs cfs
    And I search for product by "<searchBy>" for the value "<searchTerm>"
    Then I should see <count> products matching search results
    Examples:
      | country    | noCFS | logInAs           | searchBy            | searchTerm           | count |
      | Bangladesh | 10    | authorisedRepAuto | gmdn term           | Blood weighing scale | 1     |
      | Brazil     | 15    | manufacturerAuto  | medical device name | Product              | 2     |

