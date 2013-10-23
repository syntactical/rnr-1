Scenario: User can specify number of rollover vacation days as of Jan 1

Given I am a Thoughtworker
When I go to rnr.thoughtworks.com
And I enter my start date
And I enter my rollover days
And I click submit
Then my vacation days are displayed
