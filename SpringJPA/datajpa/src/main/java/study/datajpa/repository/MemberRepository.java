package study.datajpa.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);


    /**
     * 반환 타입 설정
     */
    List<Member> findListByUsername(String name); //컬렉션

    Member findByUsername(String name); //단건

    Optional<Member> findOptionalByUsername(String name); //단건 Optional

    /**
     * 페이지네이션
     */

//    Page<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용
//    Slice<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
//    List<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용 안함
    List<Member> findByUsername(String name, Sort sort);

    Page<Member> findByAge(int age, Pageable pageable);

    /**
     * 벌크 업데이트 쿼리
     */

    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    /**
     * 패치 조인
     */
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    /**
     * EntityGraph
     */

    //공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    //메서드 이름으로 쿼리에서 특히 편리하다.
//    @EntityGraph(attributePaths = {"team"})
//    List<Member> findByUsername(String username);

    /**
     * 쿼리 힌트
     */
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    /**
     * Lock
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String name);
}
