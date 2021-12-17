package itmo.practice.repository;

import itmo.practice.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    int countByLogin(String login);

    @Transactional
    @Modifying
    @Query(value = "UPDATE client SET password_sha=md5(CONCAT('1be3db47a7684152', ?2, ?3)) WHERE id=?1", nativeQuery = true)
    void updatePasswordSha(long id, String login, String password);

    @Query(value = "SELECT * FROM client WHERE login=?1 AND password_sha=md5(CONCAT('1be3db47a7684152', ?1, ?2))", nativeQuery = true)
    Client findByLoginAndPassword(String login, String password);

    @Query(value = "SELECT * FROM client WHERE login=?1", nativeQuery = true)
    Client findByLogin(String login);

    @Query(value = "SELECT count(*) FROM client1_client2 WHERE client1_id=?1 AND client2_id=?2", nativeQuery = true)
    long findFriends(long id1, long id2);

    List<Client> findAllByOrderByIdDesc();
}