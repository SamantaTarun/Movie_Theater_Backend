package movie

import norm.Command
import norm.ParamSetter
import java.sql.PreparedStatement
import kotlin.Int
import kotlin.String

public data class InsertSeatParams(
    public val show_id: Int?,
    public val seat_no: Int?
)

public class InsertSeatParamSetter : ParamSetter<InsertSeatParams> {
    public override fun map(ps: PreparedStatement, params: InsertSeatParams) {
        ps.setObject(1, params.show_id)
        ps.setObject(2, params.seat_no)
    }
}

public class InsertSeatCommand : Command<InsertSeatParams> {
    public override val sql: String = """
      |insert into seats(show_id, seat_no) values(?, ?);
      |""".trimMargin()

    public override val paramSetter: ParamSetter<InsertSeatParams> = InsertSeatParamSetter()
}
