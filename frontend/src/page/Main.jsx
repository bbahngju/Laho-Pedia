import {useEffect, useState} from "react";
import MovieItem from "../component/MovieItem"
import axios from "axios";
import { Space } from 'antd';

export default function Main({movies}) {
    const baseUrl = "http://localhost:8080"
    const [boxOffices, setBoxOffies] = useState("");

    useEffect(() => {
        async function fetchData() {
            const response = await axios.get(baseUrl + "/");
            setBoxOffies(response.data);
        }
        fetchData();
    }, []);

    return (
        <div>
            {movies ? <Space wrap>{movies.map(movie => <MovieItem movie={movie}/>)}</Space>
                : boxOffices}
        </div>
    )
}


