package movie

import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.Int
import kotlin.String
import kotlin.Unit
import norm.ParamSetter
import norm.Query
import norm.RowMapper

public data class MovieByIdParams(
  public val id: Int?
)

public class MovieByIdParamSetter : ParamSetter<MovieByIdParams> {
  public override fun map(ps: PreparedStatement, params: MovieByIdParams): Unit {
    ps.setObject(1, params.id)
  }
}

public class MovieByIdRowMapper : RowMapper<MovieByIdResult> {
  public override fun map(rs: ResultSet): MovieByIdResult = MovieByIdResult(
  id = rs.getObject("id") as kotlin.Int,
    title = rs.getObject("title") as kotlin.String,
    duration = rs.getObject("duration") as kotlin.Int?)
}

public class MovieByIdQuery : Query<MovieByIdParams, MovieByIdResult> {
  public override val sql: String = """
      | select * from movies where id = ?;
      |""".trimMargin()

  public override val mapper: RowMapper<MovieByIdResult> = MovieByIdRowMapper()

  public override val paramSetter: ParamSetter<MovieByIdParams> = MovieByIdParamSetter()
}

public data class MovieByIdResult(
  public val id: Int,
  public val title: String,
  public val duration: Int?
)
