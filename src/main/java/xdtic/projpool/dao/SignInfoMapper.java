package xdtic.projpool.dao;

import java.util.List;
import xdtic.projpool.model.SignInfo;

/**
 * Sign Info Mapper
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public interface SignInfoMapper {

    int addSignInfo(SignInfo record);

    SignInfo getSignInfo(Integer id);

    List<SignInfo> getSignInfosByProId(Integer proId);

    long containsSignInfo(Integer userId, Integer proId);
}