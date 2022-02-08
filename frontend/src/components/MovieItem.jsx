import React from "react";
import {Badge, Card} from 'antd';
import {useHistory} from 'react-router-dom';

const { Meta } = Card;

export default function MovieItem({movie}) {
    const history = useHistory();

    const imageUrl  =  movie.posters?.split('|')[0];
    const {rank, title, nation, openDt} = movie || {};
    const {movieId, plot} = movie || {};

    let movieDescription = rank ? <Meta title={title} description={`${openDt}`} /> : <Meta title={title} description={`${nation} · ${openDt}`} />
    let moviePost = <Card hoverable style={{ width: 200 }} cover={<img alt="example" src={imageUrl} />}> {movieDescription} </Card>

    return (
        <div onClick={() => history.push({
            pathname: "/details",
            state: {movieId: movieId, imageUrl: imageUrl, plot: plot}
        })}>
            {rank ? <Badge count={rank} status="success">{moviePost}</Badge> : moviePost}
        </div>
    )
}