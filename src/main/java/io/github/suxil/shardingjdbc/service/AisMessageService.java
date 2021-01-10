package io.github.suxil.shardingjdbc.service;

import io.github.suxil.shardingjdbc.domain.AisMessage;
import io.github.suxil.shardingjdbc.repository.AisMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AisMessageService {

    @Autowired
    private AisMessageRepository aisMessageRepository;

    public void save(AisMessage aisMessage) {
        aisMessageRepository.save(aisMessage);
    }

}
