package xdtic.projpool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xdtic.projpool.model.SignInfo;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Repository
public class SignInfoDao {

    @Autowired
    private JdbcTemplate jdbcTmpl;

    private static final String SQL_ADD_SIGN_INFO
            = "INSERT INTO sign_info "
            + "(pro_id, user_id, apply, skill, experience, sign_date) "
            + "VALUES (?, ?, ?, ?, ?, NOW())";

    public boolean addSignInfo(SignInfo signInfo) {

        int result = jdbcTmpl.update(SQL_ADD_SIGN_INFO,
                signInfo.getProId(), signInfo.getUserId(),
                signInfo.getApply(), signInfo.getSkill(), signInfo.getExperience());

        return result == 1;
    }

    private static final String SQL_GET_SIGN_INFO
            = "SELECT u.username, s.* FROM sign_info s, user u "
            + "WHERE s.id = ? AND u.id = s.user_id";

    public Optional<SignInfo> getSignInfo(Integer id) {
        return jdbcTmpl.query(SQL_GET_SIGN_INFO, this::extractSignInfo, id);
    }

    private static final String SQL_GET_SIGN_INFOS
            = "SELECT u.username, s.* FROM sign_info s, user u "
            + "WHERE pro_id = ? AND u.id = s.user_id";

    public List<SignInfo> getSignInfos(Integer proId) {
        return jdbcTmpl.query(SQL_GET_SIGN_INFOS, this::mapSignInfo, proId);
    }

    private Optional<SignInfo> extractSignInfo(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapSignInfo(rs, 1)) : Optional.empty();
    }

    private SignInfo mapSignInfo(ResultSet rs, int rowNum) throws SQLException {
        return SignInfo.builder()
                .id(rs.getInt("id"))
                .proId(rs.getInt("pro_id"))
                .userId(rs.getInt("user_id"))
                .apply(rs.getString("apply"))
                .experience(rs.getString("experience"))
                .skill(rs.getString("skill"))
                .signDate(rs.getTimestamp("sign_date"))
                .username(rs.getString("username")).build();
    }

}