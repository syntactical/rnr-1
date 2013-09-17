
Scenario: Fill in Date in Submit Box

Given a user is using the chrome browser
When the user clicks selects a date
Then the form box should contain a date
Then close browser

Scenario: View banner in Chrome

Given a user is using the chrome Browser
When the user goes to rnr.thoughtworks.com
Then a banner should be visible