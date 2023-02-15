package ru.ialexdm.springboot.WebLibrary2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ialexdm.springboot.WebLibrary2.models.Reader;

@Repository
public interface ReadersRepository extends JpaRepository<Reader, Integer> {
    Reader findByFullName(String name);
}
