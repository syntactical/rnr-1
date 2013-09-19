
Scenario: Fill in Date in Submit Box

Given a user is using the browser Firefox
When the user clicks selects a date
Then the form box should contain a date

Scenario: View banner in Firefox

Given a user is using the browser Firefox
When the user goes to rnr.thoughtworks.com
Then a banner should be visible
