import {Image, Typography} from "antd";
import './ComponentStyle.css'

export default function MovieDetailItem({details, station}) {
    const {title, showTm, openDt, nations, genres, directors, actors, watchGrade} = details;
    const {plot, imageUrl} = station;
    const { Title, Text, Paragraph } = Typography;

    return (
        <Typography style={{ padding: '30px 0 0 0' }}>
            <div className="movie-details-form">
                <div className="movie-details-first">
                    <div className="movie-details-image">
                        <Image width={150} src={imageUrl}/>
                    </div>
                    <div className="movie-details-short-info">
                        <Title>{title}</Title>
                        <Text type="secondary">{openDt} ・ {showTm}분 ・
                            {nations.map(nation => nation.nationNm)} ・ {genres.map(genre => genre.genreNm + " ")}</Text>
                    </div>
                </div>

                <div className="movie-details-second">
                    <Title level={3}>기본 정보</Title>
                    <Paragraph>{plot}</Paragraph>
                </div>

                <div className="movie-details-third">
                    <Title level={3}>출연/제작</Title>
                    <div className="movie-directors">
                        <Text strong="true">감독 </Text>{directors.map(director => director.peopleNm)}
                    </div>
                    <div className="movie-actors">
                        <Text strong="true">배우|역할 </Text>{actors.map(actor => "(" + actor.peopleNm + "|" + actor.cast + ") ")}
                    </div>
                </div>
            </div>
        </Typography>
    )
}