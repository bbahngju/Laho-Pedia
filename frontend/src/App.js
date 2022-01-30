import React, { useState, useEffect } from 'react';
import './App.css';
import Login from "./page/Login";
import MyPage from "./page/MyPage";
import axios from "axios";
import {Route, Switch, Link} from "react-router-dom";
import Header from "./layout/Header";
import Main from "./page/Main";
import 'antd/dist/antd.css';

function App() {
    const baseUrl = "http://localhost:8080"
    const [movies, setMovies] = useState();


    async function handleSearchClick(searchKeyword) {
        try {
            const searchResponse = await axios.get( baseUrl + "/api/search", {
                params : {
                    keyWord : searchKeyword
                }
            });
            setMovies(searchResponse.data);

        } catch (e) {
            console.error(e);
        }
    }

    return (
        <div className="App">
            <Header handleSearchClick={handleSearchClick}/>

            <div className="page">
                <Switch>
                    <Route exact path="/"><Main movies={movies}/></Route>
                    <Route path="/myPage"><MyPage/></Route>
                    <Route path="/login"><Login/></Route>
                </Switch>
            </div>
        </div>
    );
}

export default App;
