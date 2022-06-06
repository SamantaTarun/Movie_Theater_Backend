package movie

import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.Int
import kotlin.String
import kotlin.Unit
import norm.ParamSetter
import norm.Query
import norm.RowMapper

public data class SaveScreenParams(
  public val title: String?,
  public val capacity: Int?
)

public class SaveScreenParamSetter : ParamSetter<SaveScreenParams> {
  public override fun map(ps: PreparedStatement, params: SaveScreenParams): Unit {
    ps.setObject(1, params.title)
    ps.setObject(2, params.capacity)
  }
}

public class SaveScreenRowMapper : RowMapper<SaveScreenResult> {
  public override fun map(rs: ResultSet): SaveScreenResult = SaveScreenResult(
  id = rs.getObject("id") as kotlin.Int,
    title = rs.getObject("title") as kotlin.String,
    capacity = rs.getObject("capacity") as kotlin.Int)
}

public class SaveScreenQuery : Query<SaveScreenParams, SaveScreenResult> {
  public override val sql: String = """
      |INSERT INTO screens(title, capacity)
      |VALUES (?, ?)
      |returning *;
      |""".trimMargin()

  public override val mapper: RowMapper<SaveScreenResult> = SaveScreenRowMapper()

  public override val paramSetter: ParamSetter<SaveScreenParams> = SaveScreenParamSetter()
}

public data class SaveScreenResult(
  public val id: Int,
  public val title: String,
  public val capacity: Int
)
