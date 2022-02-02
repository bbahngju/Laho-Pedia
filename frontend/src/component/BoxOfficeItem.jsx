import React from 'react';
import { Card } from 'antd';

export default function BoxOfficeItem({boxOffice}) {
    const {rank, title, prodYear} = boxOffice || {};

    return (
        <div>
            <Card title={title} bordered={false} style={{ width: 300 }}>
                <p>{rank} / {prodYear}</p>
            </Card>
        </div>
    );
}