import React, { useState, useEffect } from 'react';
import './App.css';
import Login from "./login";
import Mypage from "./myPage";
import axios from "axios";
import {BrowserRouter as Routers, Route, Switch, Link} from "react-router-dom";

function App() {
    const baseUrl = "http://localhost:8080"
    const [input, setInput] = useState("Hello MainPage!");


    useEffect(() => {
        async function fetchData() {
            const response = await axios.get(baseUrl + "/");
            setInput(response.data);
        }
        fetchData();
    }, []);

    return (
        <div className="App">
            <header className="App-header">
                <div className="App-logo">
                    <h1>LAHO-PEDIA</h1>
                </div>
            </header>

            <Routers>
                <nav className="Nav">
                    <ul>
                        <li><Link to="/myPage">마이페이지</Link></li>
                        <li><Link to="/login">로그인</Link></li>
                    </ul>
                </nav>
                    <Switch>
                        <Route exact path="/">{input}</Route>
                        <Route path="/myPage"><Mypage/></Route>
                        <Route path="/login"><Login/></Route>
                    </Switch>
            </Routers>


        </div>
    );
}

export default App;
