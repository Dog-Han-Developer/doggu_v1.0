package com.doghandeveloper.doggu.account.repository;

import com.doghandeveloper.doggu.account.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
