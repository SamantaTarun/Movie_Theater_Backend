package movie

import norm.ParamSetter
import norm.Query
import norm.RowMapper
import java.math.BigDecimal
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import kotlin.Int
import kotlin.String

public data class SaveShowParams(
    public val start_time: Timestamp?,
    public val movie_id: Int?,
    public val price: BigDecimal?,
    public val movieLanguage: String?,
    public val movieType: String?
)

public class SaveShowParamSetter : ParamSetter<SaveShowParams> {
    public override fun map(ps: PreparedStatement, params: SaveShowParams) {
        ps.setObject(1, params.start_time)
        ps.setObject(2, params.movie_id)
        ps.setObject(3, params.price)
        ps.setObject(4, params.movieLanguage)
        ps.setObject(5, params.movieType)
    }
}

public class SaveShowRowMapper : RowMapper<SaveShowResult> {
    public override fun map(rs: ResultSet): SaveShowResult = SaveShowResult(
        id = rs.getObject("id") as kotlin.Int,
        startTime = rs.getObject("start_time") as java.sql.Timestamp,
        movieId = rs.getObject("movie_id") as kotlin.Int,
        price = rs.getObject("price") as java.math.BigDecimal,
        movielanguage = rs.getObject("movielanguage") as kotlin.String,
        movietype = rs.getObject("movietype") as kotlin.String,
        seats = rs.getObject("seats") as kotlin.Int?
    )
}

public class SaveShowQuery : Query<SaveShowParams, SaveShowResult> {
    public override val sql: String = """
      |INSERT INTO shows(start_time, movie_id, price,movieLanguage,movieType)
      |VALUES (?, ?, ?, ?, ?)
      |returning *;
      |""".trimMargin()

    public override val mapper: RowMapper<SaveShowResult> = SaveShowRowMapper()

    public override val paramSetter: ParamSetter<SaveShowParams> = SaveShowParamSetter()
}

public data class SaveShowResult(
    public val id: Int,
    public val startTime: Timestamp,
    public val movieId: Int,
    public val price: BigDecimal,
    public val movielanguage: String,
    public val movietype: String,
    public val seats: Int?
)
