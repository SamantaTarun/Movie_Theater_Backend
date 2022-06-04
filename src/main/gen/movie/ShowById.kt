package movie

import java.math.BigDecimal
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import kotlin.Int
import kotlin.String
import kotlin.Unit
import norm.ParamSetter
import norm.Query
import norm.RowMapper

public data class ShowByIdParams(
  public val id: Int?
)

public class ShowByIdParamSetter : ParamSetter<ShowByIdParams> {
  public override fun map(ps: PreparedStatement, params: ShowByIdParams): Unit {
    ps.setObject(1, params.id)
  }
}

public class ShowByIdRowMapper : RowMapper<ShowByIdResult> {
  public override fun map(rs: ResultSet): ShowByIdResult = ShowByIdResult(
  id = rs.getObject("id") as kotlin.Int,
    startTime = rs.getObject("start_time") as java.sql.Timestamp,
    movieId = rs.getObject("movie_id") as kotlin.Int,
    price = rs.getObject("price") as java.math.BigDecimal,
    movielanguage = rs.getObject("movielanguage") as kotlin.String,
    movietype = rs.getObject("movietype") as kotlin.String,
    seats = rs.getObject("seats") as kotlin.Int?)
}

public class ShowByIdQuery : Query<ShowByIdParams, ShowByIdResult> {
  public override val sql: String = """
      |select * from shows where id = ?;
      |""".trimMargin()

  public override val mapper: RowMapper<ShowByIdResult> = ShowByIdRowMapper()

  public override val paramSetter: ParamSetter<ShowByIdParams> = ShowByIdParamSetter()
}

public data class ShowByIdResult(
  public val id: Int,
  public val startTime: Timestamp,
  public val movieId: Int,
  public val price: BigDecimal,
  public val movielanguage: String,
  public val movietype: String,
  public val seats: Int?
)
