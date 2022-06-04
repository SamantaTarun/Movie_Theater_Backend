package movie

import java.sql.PreparedStatement
import kotlin.Int
import kotlin.String
import kotlin.Unit
import norm.Command
import norm.ParamSetter

public data class UpdateSeatStatusParams(
  public val show_id: Int?,
  public val seat_no: Int?
)

public class UpdateSeatStatusParamSetter : ParamSetter<UpdateSeatStatusParams> {
  public override fun map(ps: PreparedStatement, params: UpdateSeatStatusParams): Unit {
    ps.setObject(1, params.show_id)
    ps.setObject(2, params.seat_no)
  }
}

public class UpdateSeatStatusCommand : Command<UpdateSeatStatusParams> {
  public override val sql: String = """
      |UPDATE seats
      |SET booked = TRUE
      |WHERE show_id = ? AND seat_no = ?;
      |""".trimMargin()

  public override val paramSetter: ParamSetter<UpdateSeatStatusParams> =
      UpdateSeatStatusParamSetter()
}
