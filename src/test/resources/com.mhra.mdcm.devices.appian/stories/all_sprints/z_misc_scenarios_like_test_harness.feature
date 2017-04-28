Feature: Aa a user I would like to verify features which are not related to devices and manufacturers
  So that I can be reassured my tests will not fail due to unexpected circumstances

  @regression @2399 @readonly @_sprint7
  Scenario Outline: Verify the new countries list in business test harness uses autosuggestion
    Given I am logged into appian as "<user>" user
    When I go to test harness page
    And I get a list of countries matching searchterm "<searchTerm>" in business test harness
    Then I should see following "<matches>" returned by autosuggests
    Examples:
      | user         | searchTerm | matches          |
      | businessNoor | ZZZ        | No results found |
      | businessNoor | Ban        | Bangladesh       |
      | businessNoor | Aus        | Australia        |

  @regression @2399 @3365 @readonly @_sprint7
  Scenario Outline: Verify the new countries list in MANUFACTURER test harness uses autosuggestion
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    And I get a list of countries matching searchterm "<searchTerm>" from manufacturer test harness
    Then I should see following "<matches>" returned by manufacturer test harness autosuggests
    Examples:
      | user              | searchTerm | matches          |
      | manufacturerAuto  | Aus        | Australia        |
      | manufacturerAuto  | ZZZ        | No results found |
      | authorisedRepAuto | Aus        | Australia        |
      | authorisedRepAuto | ZZZ        | No results found |


  @regression @mdcm-465 @mdcm-164 @readonly @_sprint5 @_sprint6
  Scenario Outline: Verify no EU countries are displayed to authorisedReps
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    And I get a list of countries matching searchterm "<searchTerm>" from manufacturer test harness
    Then I should see following "<matches>" returned by manufacturer test harness autosuggests
    Examples:
      | user              | searchTerm      | matches          |
      | authorisedRepAuto | randomEUCountry | No results found |
      | authorisedRepAuto | randomEUCountry | No results found |


  @1838 @_sprint13 @readonly
  Scenario Outline: Users should be able to search using  GMDN code or term
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    When I search for device type "<deviceType>" with gmdn "<gmdn>"
    Then I should see at least <count> devices matches
    Examples:
      | user              | deviceType             | gmdn      | count |
      | authorisedRepAuto | General Medical Device | Blood     | 1     |
      | authorisedRepAuto | General Medical Device | 10014     | 1     |
      | authorisedRepAuto | General Medical Device | HllNBlood | 0     |
      | authorisedRepAuto | General Medical Device | 181481    | 0     |
      | manufacturerAuto  | General Medical Device | Blood     | 1     |
      | manufacturerAuto  | General Medical Device | 10014     | 1     |

  @regression @1838 @4211 @_sprint12 @_sprint13 @readonly @wip
  Scenario Outline: Users should be able to view all gmdn terms or definitions
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    When I click on view all gmdn term or definitions for device type "<deviceType>"
#    Then I should see all gmdn term and definition table
    When I search for gmdn "<gmdn>"
    Then I should see at least <count> devices matches
    Examples:
      | user              | deviceType                        | gmdn  | count |
      | authorisedRepAuto | General Medical Device            | Air   | 1     |
      | authorisedRepAuto | In Vitro Diagnostic Device        | Blood | 1     |
      | authorisedRepAuto | Active Implantable Medical Device | Blood | 1     |
      | authorisedRepAuto | System or Procedure Pack          | Blood | 1     |
      | manufacturerAuto  | General Medical Device            | Air   | 1     |
      | manufacturerAuto  | In Vitro Diagnostic Device        | Blood | 1     |
      | manufacturerAuto  | Active Implantable Medical Device | Blood | 1     |
      | manufacturerAuto  | System or Procedure Pack          | Blood | 1     |


  @regression @2097 @_sprint8
  Scenario Outline: Override PARD preferences when reviewing a manufacturer registration
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
    And I click on a random organisation link "<searchTerm>" in "<page>" page
    When I click on edit account information
    And I update PARD options to "<pardOptions>" for both name and address
    Then I should see following PARD "<PARDUpdateMessage>" message
#    Then I should see PARD option "<pardOption>" to be selected for "<updateNameOrAddressPard>"
    Examples:
      | user         | page          | searchTerm      | pardOptions                | updateNameOrAddressPard | PARDUpdateMessage              |
      | businessAuto | Organisations | AuthorisedRepRT | name=Optin,address=Optin   | name                    | Publish name & address         |
      | businessAuto | Organisations | ManufacturerRT  | name=Optin,address=Optout  | address                 | Publish name only              |
      | businessAuto | Organisations | AuthorisedRepRT | name=Optout,address=Optin  | name                    | Publish address only           |
      | businessAuto | Organisations | ManufacturerRT  | name=Optout,address=Optout | address                 | Do not publish name or address |
