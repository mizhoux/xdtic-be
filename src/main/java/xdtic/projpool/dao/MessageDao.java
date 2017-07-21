package xdtic.projpool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xdtic.projpool.model.Message;

/**
 *
 * @author wenjing
 */
@Repository
public class MessageDao {

    @Autowired
    private JdbcTemplate jdbcTmpl;

    private static final String SQL_ADD_MESSAGE
            = "INSERT INTO message (user_id, pro_id, content, creation_date) VALUES (?, ?, ?, NOW())";

    public boolean addMessage(Message msg) {
        return jdbcTmpl.update(SQL_ADD_MESSAGE,
                msg.getUserId(), msg.getProId(), msg.getContent()) == 1;
    }

    public Optional<Message> getMessage(Integer id) {
        String sql = "SELECT * FROM message WHERE id = ?";
        return jdbcTmpl.query(sql, this::extractMessage, id);
    }

    public List<Message> getMessages(Integer userId, int offset, int size) {
        String sql = "SELECT * FROM message WHERE user_id = ? ORDER BY creation_date DESC LIMIT ?, ?";
        return jdbcTmpl.query(sql, this::mapMessage, userId, offset, size);
    }

    /**
     * 获得用户的消息总数
     *
     * @param userId 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public long countMessages(Integer userId) {
        String sql = "SELECT COUNT(*) FROM message WHERE user_id = ?";
        return jdbcTmpl.queryForObject(sql, Long.class, userId);
    }

    /**
     * 将消息标记为已读
     *
     * @param id 消息 ID
     * @return
     */
    public boolean setMessageRead(Integer id) {
        String sql = "UPDATE message m SET m.read = TRUE WHERE m.id = ?";
        return jdbcTmpl.update(sql, id) == 1;
    }

    public boolean setMessagesRead(List<Integer> ids) {
        String sql = "UPDATE message m SET m.read = TRUE WHERE m.id IN "
                + ids.stream().map(String::valueOf).collect(Collectors.joining(",", "(", ")"));
        return jdbcTmpl.update(sql) == ids.size();
    }

    /**
     * 获得用户未读的消息总数
     *
     * @param userId 用户的 id
     * @return 数据库中对应用户消息的数量
     */
    public long countUnreadMessages(Integer userId) {
        String sql = "SELECT COUNT(*) FROM message m WHERE m.user_id = ? AND m.read = FALSE";
        return jdbcTmpl.queryForObject(sql, Long.class, userId);
    }

    /**
     * 从 ResultSet 提取数据，将其映射为 Message，目标函数为 ResultSetExtractor.extractData
     *
     * @param rs
     * @return
     * @throws SQLException
     * @see org.springframework.jdbc.core.ResultSetExtractor
     */
    private Optional<Message> extractMessage(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapMessage(rs, 1)) : Optional.empty();
    }

    /**
     * 将 ResultSet 中的数据其映射为 Message，目标函数为 RowMapper.mapRow
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     * @see org.springframework.jdbc.core.RowMapper
     */
    private Message mapMessage(ResultSet rs, int rowNum) throws SQLException {
        Message message = new Message();

        message.setId(rs.getInt("id"));
        message.setUserId(rs.getInt("user_id"));
        message.setContent(rs.getString("content"));
        message.setProId(rs.getInt("pro_id"));
        message.setRead(rs.getBoolean("read"));
        message.setCreationDate(rs.getTimestamp("creation_date"));

        return message;
    }

}