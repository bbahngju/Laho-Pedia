import {useEffect, useState} from "react";
import {useLocation} from 'react-router-dom'
import axios from "axios";
import { Spin } from 'antd';
import MovieDetailItem from "../components/MovieDetailItem";

export default function DetailPage() {
    const location = useLocation();
    const {movieId} = location.state;
    const [movieDetails, setMovieDetails] = useState();

    const baseUrl = "http://localhost:8080"
    useEffect(() => {
        if(!movieId) {
            return (
                <Spin tip="Loading..."/>
            );
        }

        async function movieDetails() {
            try {
                const response = await axios.get(baseUrl + "/api/details", {
                    params : {
                        movieId : movieId
                    }
                });
                setMovieDetails(response.data);
                console.log(response.data);
            }
            catch (e) {
                console.error(e);
            }
        }

        movieDetails()
    }, [movieId]);

    if(!movieDetails) {
        return (
            <Spin tip="Loading..."/>
        );
    }

    return (
        <div>{movieDetails.map(movieDetails => <MovieDetailItem details={movieDetails} station={location.state}/>)}</div>
    )

}