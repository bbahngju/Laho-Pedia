import {useEffect, useState} from "react";
import MovieItem from "../components/MovieItem"
import axios from "axios";
import { Space } from 'antd';
import { Spin } from 'antd';

export default function Main({movies}) {
    const [boxOffices, setBoxOffices] = useState();

    const baseUrl = "http://localhost:8080"
    useEffect(() => {
        async function boxOfficeData() {
            try {
                const response = await axios.get(baseUrl + "/");
                setBoxOffices(response.data);
            }
            catch (e) {
                console.error(e);
            }
        }

        boxOfficeData()
    }, []);

    if(!movies && !boxOffices) {
        return (
            <Spin tip="Loading..."/>
        );
    }

    //MoviesID 가져오기
    return (
            movies ? <Space wrap>{movies.map(movie => <MovieItem movie={movie}/>)}</Space>
                : <Space wrap>{boxOffices.map(boxOffice => <MovieItem movie={boxOffice}/>)}</Space>
    )
}


