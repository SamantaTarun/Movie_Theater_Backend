package movie

import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.Int
import kotlin.String
import kotlin.Unit
import norm.ParamSetter
import norm.Query
import norm.RowMapper

public data class GetNotBookedSeatsParams(
  public val show_id: Int?
)

public class GetNotBookedSeatsParamSetter : ParamSetter<GetNotBookedSeatsParams> {
  public override fun map(ps: PreparedStatement, params: GetNotBookedSeatsParams): Unit {
    ps.setObject(1, params.show_id)
  }
}

public class GetNotBookedSeatsRowMapper : RowMapper<GetNotBookedSeatsResult> {
  public override fun map(rs: ResultSet): GetNotBookedSeatsResult = GetNotBookedSeatsResult(
  seatNo = rs.getObject("seat_no") as kotlin.Int)
}

public class GetNotBookedSeatsQuery : Query<GetNotBookedSeatsParams, GetNotBookedSeatsResult> {
  public override val sql: String = """
      |SELECT seat_no FROM seats WHERE booked = FALSE and show_id = ?;
      |""".trimMargin()

  public override val mapper: RowMapper<GetNotBookedSeatsResult> = GetNotBookedSeatsRowMapper()

  public override val paramSetter: ParamSetter<GetNotBookedSeatsParams> =
      GetNotBookedSeatsParamSetter()
}

public data class GetNotBookedSeatsResult(
  public val seatNo: Int
)
