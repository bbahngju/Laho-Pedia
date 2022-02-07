import React from "react";
import { Card } from 'antd';

const { Meta } = Card;

export default function MovieItem({movie}) {
    const imageUrl  =  movie.posters?.split('|')[0];
    const {title, nation, openDt} = movie || {};
    const {movieId, plot} = movie || {};

    return (
        <Card
            hoverable
            style={{ width: 200 }}
            cover={<img alt="example" src={imageUrl} />}
        >
            <Meta title={title} description={`${nation} · ${openDt}`} />
        </Card>
    )
}