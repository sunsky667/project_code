package sunsky.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sunsky.dao.slave.SdDao;
import sunsky.entity.Sd;
import sunsky.service.SdService;

import java.util.List;

@Service
public class SdServiceImpl implements SdService {

    @Autowired
    private SdDao sdDao;

    public List<Sd> findSds() {
        return sdDao.querySds();
    }
}
