import React from "react";
import axios from "axios";
import SearchBar from "../components/SearchBar";

export default function Header({setMovies}) {
    const baseUrl = "http://localhost:8080";

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
        <SearchBar handleSearchClick = {handleSearchClick}/>
    )
}