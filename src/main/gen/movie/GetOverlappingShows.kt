package movie

import norm.ParamSetter
import norm.Query
import norm.RowMapper
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import kotlin.Long
import kotlin.String

public data class GetOverlappingShowsParams(
    public val show_end_time: Timestamp?,
    public val show_start_time: Timestamp?
)

public class GetOverlappingShowsParamSetter : ParamSetter<GetOverlappingShowsParams> {
    public override fun map(ps: PreparedStatement, params: GetOverlappingShowsParams) {
        ps.setObject(1, params.show_end_time)
        ps.setObject(2, params.show_start_time)
    }
}

public class GetOverlappingShowsRowMapper : RowMapper<GetOverlappingShowsResult> {
    public override fun map(rs: ResultSet): GetOverlappingShowsResult = GetOverlappingShowsResult(
        count = rs.getObject("count") as kotlin.Long?
    )
}

public class GetOverlappingShowsQuery : Query<GetOverlappingShowsParams, GetOverlappingShowsResult> {
    public override val sql: String = """
      |select count(*) from shows
      |where  not  (
      |start_time > ?
      |or
      |start_time + ((select duration from movies where id = movie_id)||'minutes')::interval < ?
      |);
      |""".trimMargin()

    public override val mapper: RowMapper<GetOverlappingShowsResult> = GetOverlappingShowsRowMapper()

    public override val paramSetter: ParamSetter<GetOverlappingShowsParams> =
        GetOverlappingShowsParamSetter()
}

public data class GetOverlappingShowsResult(
    public val count: Long?
)
