import {Image, Typography} from "antd";

export default function MovieDetailItem({details, station}) {
    const {title, showTm, openDt} = details;
    const {plot, imageUrl} = station;
    const { Title, Paragraph } = Typography;

    return (
        <Typography style={{ padding: '30px 0 0 0' }}>
            <Title>{title}</Title>

            <Image width={180} src={imageUrl}/>

            <Title>기본 정보</Title>
            <Paragraph>{plot}</Paragraph>
        </Typography>
    )
}