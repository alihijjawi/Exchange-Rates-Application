import "./App.css";
import UserCredentialsDialog from "./UserCredentialsDialog/UserCredentialsDialog";
import { useState, useEffect, useCallback } from "react";
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Snackbar,
  Alert,
  MenuItem,
  InputLabel,
  TextField,
  Select,
} from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { getUserToken, saveUserToken, clearUserToken } from "./localStorage";
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, Legend } from 'recharts';

var SERVER_URL = "http://127.0.0.1:5000";
var graphData = [];

function App() {
  const States = {
    PENDING: "PENDING",
    USER_CREATION: "USER_CREATION",
    USER_LOG_IN: "USER_LOG_IN",
    USER_AUTHENTICATED: "USER_AUTHENTICATED"
  };

  let [buyUsdRate, setBuyUsdRate] = useState(null);
  let [sellUsdRate, setSellUsdRate] = useState(null);
  let [lbpInput, setLbpInput] = useState("");
  let [usdInput, setUsdInput] = useState("");
  let [transactionType, setTransactionType] = useState("usd-to-lbp");
  let [userToken, setUserToken] = useState(getUserToken());
  let [authState, setAuthState] = useState(States.PENDING);
  let [lbpInputCalc, setLbpInputCalc] = useState("");
  let [usdInputCalc, setUsdInputCalc] = useState("");
  let [transactionTypeCalc, setTransactionTypeCalc] = useState("usd-to-lbp");
  let [userTransactions, setUserTransactions] = useState([]);
  let [statsPage, setStatsPage] = useState(false);
  let [buy1hStat, setBuy1hStat] = useState("test");
  let [buy12hStat, setBuy12hStat] = useState("test");
  let [buy24hStat, setBuy24hStat] = useState("");
  let [buy7dStat, setBuy7dStat] = useState("");
  let [buyMinStat, setBuyMinStat] = useState("");
  let [buyMaxStat, setBuyMaxStat] = useState("");
  let [sell1hStat, setSell1hStat] = useState("");
  let [sell12hStat, setSell12hStat] = useState("");
  let [sell24hStat, setSell24hStat] = useState("");
  let [sell7dStat, setSell7dStat] = useState("");
  let [sellMinStat, setSellMinStat] = useState("");
  let [sellMaxStat, setSellMaxStat] = useState("");
  let [graphDates, setGraphDates] = useState([]);
  let [graphBuy, setGraphBuy] = useState([]);
  let [graphSell, setGraphSell] = useState([]);
  let [tradePage, setTradePage] = useState(false);
  let [lbpTradeInput, setLbpTradeInput] = useState("");
  let [usdTradeInput, setUsdTradeInput] = useState("");
  let [tradeTransactionType, setTradeTransactionType] = useState("usd-to-lbp");
  let [tradePosts, setTradePosts] = useState([]);
  let [postIDInput, setPostIDInput] = useState("");

  function resetInput() {
    const inputFields = document.querySelectorAll("post-id-input");
    for (let i = 0; i < inputFields.length; i++) {
        inputFields[i].value = "";
    }
    return;
  }

  function fetchRates() {
    fetch(`${SERVER_URL}/exchangeRate`)
      .then((response) => response.json())
      .then((data) => {
        setBuyUsdRate(data.lbp_to_usd);
        setSellUsdRate(data.usd_to_lbp);
      });
  }
  useEffect(fetchRates, []);

  function addItem() {
    var typeTxn = false;
    if (transactionType === "usd-to-lbp") {
      typeTxn = true;
    }
    const data = {
      usd_amount: usdInput,
      lbp_amount: lbpInput,
      usd_to_lbp: typeTxn,
    };
    console.log(userToken);
    fetch(`${SERVER_URL}/transaction`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `bearer ${userToken}`,
      },

      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Success:", data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
    resetInput()
    fetchRates();
  }

  function login(username, password) {
    console.log("Login:", username, password);
    return fetch(`${SERVER_URL}/authentication`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        user_name: username,
        password: password,
      }),
    })
      .then((response) => response.json())
      .then((body) => {
        setAuthState(States.USER_AUTHENTICATED);
        setUserToken(body.token);
        saveUserToken(body.token);
      });
  }

  function createUser(username, password) {
    return fetch(`${SERVER_URL}/user`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        user_name: username,
        password: password,
      }),
    }).then((response) => login(username, password));
  }

  function logout() {
    resetInput();
    setUserToken(null);
    setTradePage(false);
    clearUserToken();
  }

  const fetchUserTransactions = useCallback(() => {
    fetch(`${SERVER_URL}/transaction`, {
      headers: {
        Authorization: `Bearer ${userToken}`,
      },
    })
      .then((response) => response.json())
      .then((transactions) => setUserTransactions(transactions));
  }, [userToken]);
  useEffect(() => {
    if (userToken) {
      fetchUserTransactions();
    }
  }, [fetchUserTransactions, userToken]);

  function showBuyStats() {
    fetch(`${SERVER_URL}/getstatbuy`)
      .then((response) => response.json())
      .then((data) => {
        setBuy1hStat(data["1h"]);
        setBuy12hStat(data["12h"]);
        setBuy24hStat(data["24h"]);
        setBuy7dStat(data["7d"]);
        setBuyMinStat(data["min"]);
        setBuyMaxStat(data["max"]);
      });
  }

  function showSellStats() {
    fetch(`${SERVER_URL}/getstatsell`)
      .then((response) => response.json())
      .then((data) => {
        setSell1hStat(data["1h"]);
        setSell12hStat(data["12h"]);
        setSell24hStat(data["24h"]);
        setSell7dStat(data["7d"]);
        setSellMinStat(data["min"]);
        setSellMaxStat(data["max"]);
      });
  }

  function getGraph() {
    fetch(`${SERVER_URL}/getgraph`)
      .then((response) => response.json())
      .then((data) => {
        setGraphDates(data["x"]);
        setGraphBuy(data["buy"]);
        setGraphSell(data["sell"]);
      });
  }
  
  function setGraphData() {
    graphData = [];
    for (var i=0; i<graphBuy.length; i++) {
      graphData.push({date: graphDates[i], buy: graphBuy[i], sell: graphSell[i]});
    }
    graphData.sort((a, b) => new Date(a.date) - new Date(b.date));
  }

  function backToHome() {
    resetInput();
    fetchRates();
    setStatsPage(false);
    setTradePage(false)
  }

  function getStats() {
    resetInput();
    showSellStats();
    showBuyStats();
    getGraph();
    setGraphData();
    console.log(graphData);
    setStatsPage(true);
  }

  function getTrade() {
    resetInput();
    getPosts();
    setTradePage(true);
  }

  function postItem() {
    var typeSell = false;
    if (tradeTransactionType === "usd-to-lbp") {
      typeSell = true;
    }
    const data = {
      usd_amount: usdTradeInput,
      lbp_amount: lbpTradeInput,
      typeSell: typeSell,
    };
    console.log(userToken);
    fetch(`${SERVER_URL}/post`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `bearer ${userToken}`,
      },

      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Success:", data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }

  const getPosts = useCallback(() => {
    fetch(`${SERVER_URL}/getPosts`, {
      headers: {
        Authorization: `Bearer ${userToken}`,
      },
    })
      .then((response) => response.json())
      .then((posts) => setTradePosts(posts));
  }, [userToken]);
  useEffect(() => {
    if (userToken) {
      getPosts();
    }
  }, [getPosts, userToken]);

  function acceptItem() {
    const data = {
      postid: postIDInput
    };
    console.log(userToken);
    fetch(`${SERVER_URL}/acceptPost`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `bearer ${userToken}`,
      },

      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Success:", data);
        resetInput();
        getTrade();
        fetchRates();
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }

  return (
    <div>
      <UserCredentialsDialog
        open={authState === States.USER_CREATION}
        onClose={() => setAuthState(States.PENDING)}
        title="Register"
        onSubmit={createUser}
        submitText="Register"
      />
      <UserCredentialsDialog
        open={authState === States.USER_LOG_IN}
        onClose={() => setAuthState(States.PENDING)}
        title="Login"
        onSubmit={login}
        submitText="Login"
      />

      <Snackbar
        elevation={6}
        variant="filled"
        open={authState === States.USER_AUTHENTICATED}
        autoHideDuration={2000}
        onClose={() => setAuthState(States.PENDING)}
      >
        <Alert severity="success">Success</Alert>
      </Snackbar>

      <html>
        <head>
          <title>LBP Exchange Tracker</title>
          <link rel="stylesheet" href="style.css"></link>
        </head>
        <body>
          <AppBar position="static">
            <Toolbar classes={{ root: "nav" }}>
              <Typography variant="h5">
                EXCHANGEOTRON 9000
              </Typography>
              <div>
              {!statsPage && !tradePage ? (
                <>
                  <Button color="inherit" onClick={() => getStats()}>
                    Rate Statistics
                  </Button>
                  {userToken !== null ? (
                  <Button color="inherit" onClick={() => getTrade()}>
                    Trade with Users
                  </Button>
                  ) : (<></>)}
                </>
                ) : (
                  <Button color="inherit" onClick={() => backToHome()}>
                  Back to Home
                </Button>
                )}
                {userToken !== null ? (
                  <Button color="inherit" onClick={logout}>
                    Logout
                  </Button>
                ) : (
                  <>
                    <Button
                      color="inherit"
                      onClick={() => setAuthState(States.USER_CREATION)}
                    >
                      Register
                    </Button>
                    <Button
                      color="inherit"
                      onClick={() => setAuthState(States.USER_LOG_IN)}
                    >
                      Login
                    </Button>
                  </>
                )}
              </div>
            </Toolbar>
          </AppBar>
          {statsPage ? (
                  <div className="wrapper">
                  <h2>Statistics of the past Buy Rates</h2>
                  <h3>1-Hour Buy Rate: <span id="buy-1h-rate">{buy1hStat} %</span></h3> 
                  <h3>12-Hour Buy Rate: <span id="buy-12h-rate">{buy12hStat} %</span></h3>
                  <h3>24-Hour Buy Rate: <span id="buy-24h-rate">{buy24hStat} %</span></h3>
                  <h3>7-Day Buy Rate: <span id="buy-7d-rate">{buy7dStat} %</span></h3>
                  <h3>Minimum Buy Rate: <span id="buy-min-rate">{buyMinStat}</span></h3>
                  <h3>Maximum Buy Rate: <span id="buy-max-rate">{buyMaxStat}</span></h3>
                  
                  <hr />
                  
                  <h2>Statistics of the past Sell Rates</h2>
                  <h3>1-Hour Sell Rate: <span id="sell-1h-rate">{sell1hStat} %</span></h3>
                  <h3>12-Hour Sell Rate: <span id="sell-12h-rate">{sell12hStat} %</span></h3>
                  <h3>24-Hour Sell Rate: <span id="sell-24h-rate">{sell24hStat} %</span></h3>
                  <h3>7-Day Sell Rate: <span id="sell-7d-rate">{sell7dStat} %</span></h3>
                  <h3>Minimum Sell Rate: <span id="sell-min-rate">{sellMinStat}</span></h3>
                  <h3>Maximum Sell Rate: <span id="sell-max-rate">{sellMaxStat}</span></h3>
                  
                  <hr />
                  <LineChart width={900} height={450} data={graphData} margin={{ top: 5, right: 20, bottom: 5, left: 0 }}>
                    <Legend />
                    <Line
                      type="monotone"
                      dataKey="buy"
                      stroke={"blue"}
                      strokeWidth={2}
                      activeDot={{ r: 8 }}
                    />
                    <Line
                      ype="monotone"
                      dataKey="sell"
                      stroke={"red"}
                      strokeWidth={2}
                      activeDot={{ r: 8 }}
                    />
                    <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
                    <XAxis dataKey="date"/>
                    <YAxis />
                    <YAxis />
                    <Tooltip />
                  </LineChart>
                  </div>
                ) : (tradePage ? (
            <div className="wrapper">
            <h2>Trade USD/LBP with Other Users</h2>
            {userToken && (
              <div className="wrapper" style={{ height: 350, width: '90%' }}>
                <DataGrid
                  columns={[
                    { field: "added_date",  headerName: "Date",       width: 250 },
                    { field: "id",          headerName: "Post ID",    width: 40 },
                    { field: "user_id",     headerName: "User",       width: 20 },
                    { field: "username",    headerName: "Username",   flex: 1 },
                    { field: "usd_amount",  headerName: "USD Amount", flex: 1 },
                    { field: "lbp_amount",  headerName: "LBP Amount", flex: 1 },
                    { field: "typeSell",    headerName: "USD to LBP", width: 100 }
                  ]}
                  rows={tradePosts}
                  autoHeight
                />
                <br />
                <br />
                <form name="post-entry">
                  <div className="amount-input">
                    <h3>Choose Post ID</h3>
                    <Select
                      id="post-id-input"
                      value={postIDInput}
                      displayEmpty
                      onChange={(e) => setPostIDInput(e.target.value)}>
                        <MenuItem value=""> Choose Post ID </MenuItem>
                    {tradePosts.map((post, id) => <MenuItem key={id} value={post.id}> {post.id} </MenuItem>)} 
                    </Select>
                  </div>
                  
                  <Button
                    id="accept-button"
                    className="button"
                    type="button"
                    onClick={acceptItem}
                    variant="contained"
                    sx={{
                      width: 135,
                    }}
                  >
                    Trade
                  </Button>
                </form>
              </div>
            )}
            <br></br>
            <br></br>
            <br></br>
            <hr />
            

            <h2>Post Your Own Trades</h2>
            <form name="post-entry">
              <div className="amount-input">
                <InputLabel htmlFor="lbp-trade-amount">LBP Amount</InputLabel>
                <TextField
                  id="lbp-trade-amount"
                  type="number"
                  value={lbpTradeInput}
                  onChange={(e) => setLbpTradeInput(e.target.value)}
                />
              </div>
              <div className="amount-input">
                <InputLabel htmlFor="usd-trade-amount">USD Amount</InputLabel>
                <TextField
                  id="usd-trade-amount"
                  type="number"
                  value={usdTradeInput}
                  onChange={(e) => setUsdTradeInput(e.target.value)}
                />
              </div>
              <Select
                id="transaction-type"
                value={tradeTransactionType}
                onChange={(e) => setTradeTransactionType(e.target.value)}
              >
                <MenuItem value="usd-to-lbp">USD to LBP</MenuItem>
                <MenuItem value="lbp-to-usd">LBP to USD</MenuItem>
              </Select>
              <br />
              <br />
              <Button
                id="post-button"
                className="button"
                type="button"
                onClick={postItem}
                variant="contained"
                sx={{
                  width: 135,
                }}
              >
                Post
              </Button>
            </form>
            </div>
                    ) : (
          <div className="wrapper">
            <h2>Today's Exchange Rate</h2>
            <p>LBP to USD Exchange Rate</p>
            <h3>
              Buy USD: <span id="buy-usd-rate">{buyUsdRate}</span>
            </h3>
            <h3>
              Sell USD: <span id="sell-usd-rate">{sellUsdRate}</span>
            </h3>

            <hr />
            <h2>Calculate a Transaction</h2>
            <form name="transaction-entry">
              <div className="amount-input">
                <InputLabel htmlFor="lbp-amount">LBP Amount</InputLabel>
                <TextField
                  id="lbp-amount-c"
                  type="number"
                  value={lbpInputCalc}
                  onChange={(e) => {
                    if (transactionType === "usd-to-lbp") {
                      setLbpInputCalc(e.target.value);
                      setUsdInputCalc(e.target.value / buyUsdRate);
                    } else {
                      setLbpInputCalc(e.target.value);
                      setUsdInputCalc(e.target.value / sellUsdRate);
                    }
                  }}
                />
              </div>

              <div className="amount-input">
                <InputLabel htmlFor="usd-amount">USD Amount</InputLabel>
                <TextField
                  id="usd-amount-c"
                  type="number"
                  value={usdInputCalc}
                  onChange={(e) => {
                    if (transactionType === "usd-to-lbp") {
                      setUsdInputCalc(e.target.value);
                      setLbpInputCalc(e.target.value * buyUsdRate);
                    } else {
                      setUsdInputCalc(e.target.value);
                      setLbpInputCalc(e.target.value * sellUsdRate);
                    }
                  }}
                />
              </div>
              <Select
                id="transaction-type-c"
                onChange={(e) => setTransactionTypeCalc(e.target.value)}
                value={transactionTypeCalc}
              >
                <MenuItem value="usd-to-lbp">USD to LBP</MenuItem>
                <MenuItem value="lbp-to-usd">LBP to USD</MenuItem>
              </Select>
            </form>

            <hr />
            <h2>Record a recent transaction</h2>
            <form name="transaction-entry">
              <div className="amount-input">
                <InputLabel htmlFor="lbp-amount">LBP Amount</InputLabel>
                <TextField
                  id="lbp-amount"
                  type="number"
                  value={lbpInput}
                  onChange={(e) => setLbpInput(e.target.value)}
                />
              </div>
              <div className="amount-input">
                <InputLabel htmlFor="usd-amount">USD Amount</InputLabel>
                <TextField
                  id="usd-amount"
                  type="number"
                  value={usdInput}
                  onChange={(e) => setUsdInput(e.target.value)}
                />
              </div>
              <Select
                id="transaction-type"
                value={transactionType}
                onChange={(e) => setTransactionType(e.target.value)}
              >
                <MenuItem value="usd-to-lbp">USD to LBP</MenuItem>
                <MenuItem value="lbp-to-usd">LBP to USD</MenuItem>
              </Select>
              <br />
              <br />
              <Button
                id="add-button"
                className="button"
                type="button"
                onClick={addItem}
                variant="contained"
                sx={{
                  width: 135,
                }}
              >
                Add
              </Button>
            </form>
            <hr />
            <h2>Your past Transactions</h2>
            {userToken && (
              <div className="wrapper">
                <DataGrid
                  columns={[
                    {
                      field: "added_date",
                      headerName: "Date",

                      width: 180,
                    },
                    { field: "usd_amount", headerName: "LBP Amount", flex: 1 },
                    { field: "lbp_amount", headerName: "USD Amount", flex: 1 },
                    { field: "usd_to_lbp", headerName: "USD to LBP", flex: 1 },
                  ]}
                  rows={userTransactions}
                  autoHeight
                />
              </div>
            )}
          </div>
          ))}
          <script src="script.js"></script>
        </body>
      </html>
    </div>
  );
}

export default App;
