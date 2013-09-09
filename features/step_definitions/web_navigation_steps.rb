require 'watir-webdriver'

Given /^a user is using the browser Firefox$/ do
   @browser = Watir::Browser.new :ff
end

Given /^a user is using the browser Chrome$/ do
   @browser = Watir::Browser.new :chrome
end

Given /^a user is using the browser Safari$/ do
   @browser = Watir::Browser.new :safari
end

When /^the user goes to rnr\.thoughtworks\.com$/ do
   @browser.goto "http://localhost:8080/"
end

Then /^a banner should be visible$/ do
   banner = @browser.div(:id, "banner")
   raise NotImplementedError.new unless banner.exists?
end

