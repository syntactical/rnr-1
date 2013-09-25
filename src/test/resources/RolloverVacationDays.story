Scenario: User can specify number of rollover vacation days as of Jan 1

Given I am a Thoughtworker
When I go to rnr.thoughtworks.com
And I enter my start date prior to the current calendar year
Then system promts me to enter how many days I rolled over

Given I am a Thoughtworker
When I go to rnr.thoughtworks.com
And I enter my start date in the scope of the current calendar year
Then system does not ask me to enter how many days I rolled over

Given I am a Thoughtworker
When I go to rnr.thoughtworks.com
And I enter my start date prior to the current calendar year
And I enter how many days I rolled over as a negative number
Then system does not allow me to enter minus symbol

