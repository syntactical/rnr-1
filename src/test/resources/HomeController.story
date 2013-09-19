Scenario: View banner in Firefox

Given I am a user
When I go to rnr.thoughtworks.com
Then a banner should be visible

Scenario: Fill in Date in Submit Box

Given I am a user going to rnr.thoughtworks.com
When I select a date
Then the form box should contain a date
