import React from 'react';
import { Card } from 'antd';
import { Badge } from 'antd';

const { Meta } = Card;

export default function BoxOfficeItem({boxOffice}) {
    const imageUrl  =  boxOffice.posters?.split('|')[0];
    const {rank, title, openDt} = boxOffice || {};

    return (
        <Badge count={rank} status="success"><Card
            hoverable
            style={{ width: 200 }}
            cover={<img alt="example" src={imageUrl} />}
        >
            <Meta title={title} description={`${openDt}`} />
        </Card></Badge>
    );
}