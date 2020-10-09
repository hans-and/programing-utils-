package hasses.magical.tools.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hasses.magical.tools.model.SlApiData;

@Repository
public interface SlApiDataRepository extends JpaRepository<SlApiData,Long>{

}
