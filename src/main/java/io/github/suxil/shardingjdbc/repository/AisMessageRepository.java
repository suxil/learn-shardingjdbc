package io.github.suxil.shardingjdbc.repository;

import io.github.suxil.shardingjdbc.domain.AisMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AisMessageRepository extends JpaRepository<AisMessage, String> {
}
