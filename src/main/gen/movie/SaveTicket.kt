package movie

import norm.ParamSetter
import norm.Query
import norm.RowMapper
import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.Int
import kotlin.String

public data class SaveTicketParams(
    public val user_email: String?,
    public val seat_number: Int?,
    public val show_id: Int?
)

public class SaveTicketParamSetter : ParamSetter<SaveTicketParams> {
    public override fun map(ps: PreparedStatement, params: SaveTicketParams) {
        ps.setObject(1, params.user_email)
        ps.setObject(2, params.seat_number)
        ps.setObject(3, params.show_id)
    }
}

public class SaveTicketRowMapper : RowMapper<SaveTicketResult> {
    public override fun map(rs: ResultSet): SaveTicketResult = SaveTicketResult(
        id = rs.getObject("id") as kotlin.Int,
        userEmail = rs.getObject("user_email") as kotlin.String,
        seatNumber = rs.getObject("seat_number") as kotlin.Int,
        showId = rs.getObject("show_id") as kotlin.Int
    )
}

public class SaveTicketQuery : Query<SaveTicketParams, SaveTicketResult> {
    public override val sql: String = """
      |INSERT INTO ticket(user_email, seat_number, show_id)
      |values(?, ?, ?)
      |returning *;
      |""".trimMargin()

    public override val mapper: RowMapper<SaveTicketResult> = SaveTicketRowMapper()

    public override val paramSetter: ParamSetter<SaveTicketParams> = SaveTicketParamSetter()
}

public data class SaveTicketResult(
    public val id: Int,
    public val userEmail: String,
    public val seatNumber: Int,
    public val showId: Int
)
