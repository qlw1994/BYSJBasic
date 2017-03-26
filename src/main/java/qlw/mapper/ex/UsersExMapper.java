package qlw.mapper.ex;

import qlw.mapper.UsersMapper;
import qlw.model.Users;
import qlw.util.annotation.BatisRepository;

/**
 * Created by wiki on 2017/1/24.
 */
@BatisRepository
public interface UsersExMapper extends UsersMapper {
    long insertBackId(Users users);
}
