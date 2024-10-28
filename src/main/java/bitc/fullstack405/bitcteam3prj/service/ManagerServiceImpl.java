package bitc.fullstack405.bitcteam3prj.service;

import bitc.fullstack405.bitcteam3prj.database.entity.ManagerEntity;
import bitc.fullstack405.bitcteam3prj.database.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public ManagerEntity selectManagerById(long id) throws Exception {
        ManagerEntity manager = null;
        var opt = managerRepository.findById(id);
        if(opt.isPresent()) {
            manager = (ManagerEntity)opt.get();
        }

        return manager;
    }
}