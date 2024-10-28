package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.ManagerEntity;

public interface ManagerService {
    ManagerEntity selectManagerById(long id) throws Exception;
}
