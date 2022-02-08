import {useLocation} from 'react-router-dom'
import {Typography, Image} from 'antd'


export default function DetailPage() {
    const location = useLocation();
    const plot = location.state.plot
    const imageUrl = location.state.imageUrl
    const { Title, Paragraph} = Typography;

    return (
        <Typography style={{ padding: '30px 0 0 0' }}>
            <Title>Introduction</Title>

            <Image width={180} src={imageUrl}/>

            <Title>기본 정보</Title>
            <Paragraph>{plot}</Paragraph>
            </Typography>
    )
}