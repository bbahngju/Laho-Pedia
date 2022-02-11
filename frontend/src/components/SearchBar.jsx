import {Link} from "react-router-dom";
import React from "react";
import Search from "antd/es/input/Search";

export default function SearchBar({handleSearchClick}) {

    return (
        <header>
            <div className="App-logo">
                <a href="/">LAHO-PEDIA</a>
            </div>
            <nav className="Nav">
                <ul>
                    <li><Link to="/myPage">마이페이지</Link></li>
                    <li><Link to="/login">로그인</Link></li>
                    <li>
                        <Search placeholder="검색어를 입력하세요." onSearch={handleSearchClick} style={{ width: 200 }} />
                    </li>
                </ul>
            </nav>
        </header>
    );
}