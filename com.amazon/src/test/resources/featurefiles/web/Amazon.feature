Feature: Amazon 

@Amazon @Bag
Scenario Outline: verify the product details after logout and login to application 
Given User is logged in to the amazon application successfully with <UserName> and <Password>
And I should see username after login to the application <UserName>
And I remove the product if already exist
And I navigate to pdppage after search the <product> 
And I add product to bag
Then I verify the added product details in shoppingBag
And I verify the product details after logout and login to application with <UserName> and <Password>
Examples:
| UserName 			  | Password | product|
| antony602@gmail.com | Ammu@602 | iphone x 64 gb |

@Amazon @validError
Scenario Outline: verify the error message details in login page
Given I am navigate to url
When I Enter <UserName> and <Password>
Then I should see the valid error message

Examples:
| UserName 			  | Password | 
| antony			  | pas |
| antony602@gmail.com | a |