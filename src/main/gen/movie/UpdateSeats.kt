package movie

import java.sql.PreparedStatement
import kotlin.Int
import kotlin.String
import kotlin.Unit
import norm.Command
import norm.ParamSetter

public data class UpdateSeatsParams(
  public val seats: Int?,
  public val id: Int?
)

public class UpdateSeatsParamSetter : ParamSetter<UpdateSeatsParams> {
  public override fun map(ps: PreparedStatement, params: UpdateSeatsParams): Unit {
    ps.setObject(1, params.seats)
    ps.setObject(2, params.id)
  }
}

public class UpdateSeatsCommand : Command<UpdateSeatsParams> {
  public override val sql: String = """
      |UPDATE shows
      |SET seats = seats - ?
      |WHERE
      |id = ?;
      |""".trimMargin()

  public override val paramSetter: ParamSetter<UpdateSeatsParams> = UpdateSeatsParamSetter()
}
