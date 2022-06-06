package movie

import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.Int
import kotlin.String
import kotlin.Unit
import norm.ParamSetter
import norm.Query
import norm.RowMapper

public class GetAllScreensParams()

public class GetAllScreensParamSetter : ParamSetter<GetAllScreensParams> {
  public override fun map(ps: PreparedStatement, params: GetAllScreensParams): Unit {
  }
}

public class GetAllScreensRowMapper : RowMapper<GetAllScreensResult> {
  public override fun map(rs: ResultSet): GetAllScreensResult = GetAllScreensResult(
  id = rs.getObject("id") as kotlin.Int,
    title = rs.getObject("title") as kotlin.String,
    capacity = rs.getObject("capacity") as kotlin.Int)
}

public class GetAllScreensQuery : Query<GetAllScreensParams, GetAllScreensResult> {
  public override val sql: String = """
      |SELECT * FROM screens
      |""".trimMargin()

  public override val mapper: RowMapper<GetAllScreensResult> = GetAllScreensRowMapper()

  public override val paramSetter: ParamSetter<GetAllScreensParams> = GetAllScreensParamSetter()
}

public data class GetAllScreensResult(
  public val id: Int,
  public val title: String,
  public val capacity: Int
)
