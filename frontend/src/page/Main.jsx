import {useEffect, useState} from "react";
import MovieItem from "../component/MovieItem"
import axios from "axios";
import { Space } from 'antd';
import BoxOfficeItem from "../component/BoxOfficeItem";
import { Spin } from 'antd';

export default function Main({movies}) {
    const baseUrl = "http://localhost:8080"
    const [boxOffices, setBoxOffies] = useState();

    useEffect(() => {
        async function boxOfficeData() {
            try {
                const response = await axios.get(baseUrl + "/");
                setBoxOffies(response.data);
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
                : <Space wrap>{boxOffices.map(boxOffice => <BoxOfficeItem boxOffice={boxOffice}/>)}</Space>
    )
}


