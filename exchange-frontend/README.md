# `React/Web Platform`

## `How to Run`
- clone the repo with the exchange-frontend files
- if you have a "node_modules" folder already, skip 3 steps
- if you download the source code only: make sure to run "npx create-react-app exchange-frontend" command in the CMD on the main folder of the frontend code
- run the commands "npm i @mui/material @emotion/react @emotion/styled recharts" to install the needed libraries and any other libraries the CMD promts you to do
- you should see a "node_modules" folder that will run your react app
- Run the backend properly according to the instructions on that
- Run the command "npm start" in the main frontend folder and wait for your local browser to open your localhost:3000 route
- You should be able to use the webapp normally

## `Technologies Used`
- React, Javascript JSX with a good set of libraries are used to build the User Interface
- [<Recharts/>](https://recharts.org/en-US/) is used for the exchange rate multi-line graph

## `Functionalities`

### `Homepage`
The homepage includes the main Rates of buying and selling USD/LPB according caluclated according to previous transactions users of the webapp have done. It also contains a calculator that helps the user check how much they need to have in some currency, in regards of the other according the the rate, or how much their own money money will transfer to in the other currency. Also, a users can record their own transactions and the type (buy/sell) and that will affect the main rates. Previous transactions will show if the user has made said transactions while being logged in.

### `Registering/Logging In`
A user can register using a unique identifier and a password to open extra features of the website, like seeing past transactions and performing trades with other users.

### `Rate Statistics`
Clicking on that button will transfer the user to a new page that shows specific rate-statistics that can inform them of how the history of the rates were evolving, and also show a graph of the buy and sell rates are fluctuating.

### `Trade Exchange`
Registered users can trade their own money with users when they post a trade offer in the Trade section. First, they have a list of all the trade offers by other users of the webapp and it shows the details of the trade, and they can choose a specific post-id from the list to choose to trade for. This affects the calculation of the actual main buy/sell rate.
The users can also post trade offers by entering the specific amounts they have and want to trade, and it will show on other user's list of trade offers. Obviously, only one person can accept a trade offer, and they it will be gone.

# `App Architecture`
This is a visual representation of the architecture the app is based on 

![arch-figure drawio](https://user-images.githubusercontent.com/63451559/168114539-0f19c79a-85c6-43f3-9447-703c4ab69a2e.png)
