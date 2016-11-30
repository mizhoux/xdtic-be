package wenjing.xdtic.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wenjing.xdtic.model.Systemassage;

/**
 *
 * @author admin
 */
@Repository
public class SystemassageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int countMessages(Integer uid) {
        String SQL = "SELECT COUNT(*) FROM systemassage";
        Map<String, Object> map = jdbcTemplate.queryForMap(SQL);
        Long count = (Long)map.get("COUNT(*)");
        
        return count.intValue();
    }
    
    public List<Systemassage> getSystemassageid(Integer uid, Integer pageNum ,Integer size) {
        String SQL = "SELECT * FROM  systemassage WHERE uid = ? LIMIT ?, ?";
        //limit 后加限制条件

        List<Systemassage> messages = new ArrayList<>();

        try {
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(SQL, uid, pageNum, size);

            for (Map<String, Object> map : maps) {
                //Map数据集返回对象名为string类型的值
                Systemassage systemassage = new Systemassage();
                systemassage.setUid((Integer) map.get("uid"));//将得到的数据赋值，并返回
                systemassage.setMid((Integer) map.get("mid"));
                systemassage.setMassage((String) map.get("massage"));
                systemassage.setType((String) map.get("type"));
                
                Timestamp timestamp = (java.sql.Timestamp) map.get("date");    //获取时间
                LocalDateTime dateTime = timestamp.toLocalDateTime();
                String dateStr = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));//设施时间格式
                systemassage.setDate(dateStr); //加入map
                messages.add(systemassage);
            }

        } catch (EmptyResultDataAccessException ex) {
            //    return null;// 捕获异常      spring查询不到输入数据时返回null    
        }

        return messages;
    }
    
}
