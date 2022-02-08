import React, { useState } from 'react';
import './App.css';
import Login from "./pages/Login";
import MyPage from "./pages/MyPage";
import { Route, Switch } from "react-router-dom";
import Header from "./layout/Header";
import Main from "./pages/Main";
import 'antd/dist/antd.css';
import DetailPage from "./pages/DetailPage";

function App() {
    const [movies, setMovies] = useState();

    return (
        <div className="App">
            <Header setMovies={setMovies}/>

            <div className="page">
                <Switch>
                    <Route exact path="/"><Main movies={movies}/></Route>
                    <Route path="/myPage"><MyPage/></Route>
                    <Route path="/login"><Login/></Route>
                    <Route path="/details"><DetailPage/></Route>
                </Switch>
            </div>
        </div>
    );
}

export default App;
