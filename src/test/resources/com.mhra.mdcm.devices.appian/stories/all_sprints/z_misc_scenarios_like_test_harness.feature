Feature: Aa a user I would like to verify features which are not related to devices and manufacturers
  So that I can be reassured my tests will not fail due to unexpected circumstances

  @2399 @readonly @_sprint7
  Scenario Outline: Verify the new countries list in business test harness uses autosuggestion
    Given I am logged into appian as "<user>" user
    When I go to test harness page
    And I get a list of countries matching searchterm "<searchTerm>" in business test harness
    Then I should see following "<matches>" returned by autosuggests
    Examples:
      | user         | searchTerm | matches          |
      | businessNoor | ZZZ         | No results found |
      | businessNoor | Ban         | Bangladesh       |
      | businessNoor | Aus         | Australia        |

  @2399 @3365 @readonly @_sprint7
  Scenario Outline: Verify the new countries list in MANUFACTURER test harness uses autosuggestion
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    And I get a list of countries matching searchterm "<searchTerm>" from manufacturer test harness
    Then I should see following "<matches>" returned by manufacturer test harness autosuggests
    Examples:
      | user              | searchTerm | matches          |
      | manufacturerAuto  | Aus         | Australia        |
      | manufacturerAuto  | ZZZ         | No results found |
      | authorisedRepAuto | Aus         | Australia        |
      | authorisedRepAuto | ZZZ         | No results found |


  @mdcm-465 @mdcm-164 @readonly @_sprint5 @_sprint6
  Scenario Outline: Verify no EU countries are displayed to authorisedReps
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    And I get a list of countries matching searchterm "<searchTerm>" from manufacturer test harness
    Then I should see following "<matches>" returned by manufacturer test harness autosuggests
    Examples:
      | user              | searchTerm      | matches          |
      | authorisedRepAuto  | randomEUCountry | No results found |
      | authorisedRepAuto | randomEUCountry | No results found |


  @regression @1838 @_sprint13 @wip
  Scenario Outline: Users should be able to search using  GMDN code or term
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I search for device type "<deviceType>" with gmdn "<gmdn>"
    Then I should see at least <count> devices matches
    Examples:
      | user              | deviceType             | gmdn      | count |
      | authorisedRepAuto | General Medical Device | Blood     | 1     |
      | authorisedRepAuto | General Medical Device | 18148     | 1     |
      | authorisedRepAuto | General Medical Device | HllNBlood | 0     |
      | authorisedRepAuto | General Medical Device | 181481    | 0     |
      | manufacturerAuto  | General Medical Device | Blood     | 1     |
      | manufacturerAuto  | General Medical Device | 18148     | 1     |

  @regression @1838 @4211 @_sprint12 @_sprint13 @wip
  Scenario Outline: Users should be able to view all gmdn terms or definitions
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
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
#    And I should see PARD option "<pardOption>" to be selected for "<updateNameOrAddressPard>"
#    Then I should see business manufacturer details page for the manufacturer
#    When I click on link "product details" and go to "devices" page
#    Then I should see device table with devices
#    When I click on a device with link "heart" for device type "<deviceType>"
#    Then I should see correct information for device type "<deviceType>"
    Examples:
      | user         | page              | searchTerm      | pardOptions                | updateNameOrAddressPard | PARDUpdateMessage              |
      | businessAuto | All Organisations | AuthorisedRepRT | name=Optin,address=Optin   | name                    | Publish name & address         |
      | businessAuto | All Organisations | ManufacturerRT  | name=Optin,address=Optout  | address                 | Publish name only              |
      | businessAuto | All Organisations | AuthorisedRepRT | name=Optout,address=Optin  | name                 | Publish address only           |
      | businessAuto | All Organisations | ManufacturerRT  | name=Optout,address=Optout | address                 | Do not publish name or address |

#  @regression @2049 @_sprint8
#  Scenario Outline: Check correct options shown to users when adding devices
#    Given I am logged into appian as "<user>" user
#    And I go to list of manufacturers page
#    And I click on a random manufacturer
#    When I try to add an incomplete device to SELECTED manufacturer with following data
#      | deviceType         | <deviceType>         |
#      | gmdnDefinition     | <gmdnDefinition>     |
#      | customMade         | false                |
#      | riskClassification | <riskClassification> |
#      | notifiedBody       | <notifiedBody>       |
#      | isBearingCEMarking | <isBearingCEMarking> |
#      | devicesCompatible  | <devicesCompatible>  |
#    Then I should see validation error message in devices page with text "<errorMsg>"
#    And I should be prevented from adding the devices
#    Examples:
#      | user              | deviceType               | gmdnDefinition      | riskClassification | notifiedBody | isBearingCEMarking | devicesCompatible | errorMsg                                                                                         |
#      | authorisedRepAuto | General Medical Device   | Blood               | class2a            | NB 0086 BSI  |                    |                   | You cannot register class IIa devices with the MHRA                                              |
#      | manufacturerAuto  | General Medical Device   | Blood               | class2b            | NB 0086 BSI  |                    |                   | You cannot register class IIb devices with the MHRA                                              |
#      | manufacturerAuto  | General Medical Device   | Blood               | class3             | NB 0086 BSI  |                    |                   | You cannot register class III devices with the MHRA                                              |
#      | authorisedRepAuto | System or Procedure Pack | Desiccating chamber |                    | NB 0086 BSI  | true               |                   | You cannot register this as a System/procedure pack because all the components must be CE marked |
#      | authorisedRepAuto | System or Procedure Pack | Desiccating chamber |                    | NB 0086 BSI  |                    | false             | You cannot register this as a System/procedure pack because all the components must be CE marked |
#      | manufacturerAuto  | System or Procedure Pack | Desiccating chamber |                    | NB 0086 BSI  | false              | false             | This System/procedure pack cannot be registered with us                                          |

