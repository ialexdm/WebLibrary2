package ru.ialexdm.springboot.WebLibrary2.services;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ialexdm.springboot.WebLibrary2.models.Book;
import ru.ialexdm.springboot.WebLibrary2.models.Reader;
import ru.ialexdm.springboot.WebLibrary2.repositories.ReadersRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional(readOnly = true)
public class ReadersService {
    private final ReadersRepository readersRepository;

    public ReadersService(ReadersRepository readersRepository) {
        this.readersRepository = readersRepository;
    }
    public List<Reader> findAll(){
        return readersRepository.findAll();
    }
    public Reader findOne(Integer id){
        Optional<Reader> foundReader = readersRepository.findById(id);
        return foundReader.orElse(null);
    }
    public Reader findOne(String fullName){
        Optional<Reader> foundReader = Optional.ofNullable(readersRepository.findByFullName(fullName));

        return foundReader.orElse(null);
    }
    public List<Book> findBooksByReaderId(Integer id) {
        Optional<Reader> reader = readersRepository.findById(id);
        if (reader.isPresent()) {
            Hibernate.initialize(reader.get().getReadableBooks());
            return reader.get().getReadableBooks();
        }
        return Collections.EMPTY_LIST;
    }

    public boolean[] isBooksDelayed(List<Book> books){
        boolean[] isDelayed = null;
        if (!books.isEmpty()){
            isDelayed = new boolean[books.size()];
            for (int i = 0; i< books.size(); i++){
                LocalDate wasGot = books.get(i).getWasGot();
                isDelayed[i] = wasGot != null &&
                        DAYS.between(wasGot, LocalDate.now()) > 10;
            }
        }
        return isDelayed;
    }

    @Transactional
    public void save(Reader reader){
        readersRepository.save(reader);
    }
    @Transactional
    public void update(Integer id, Reader updatedReader){
        updatedReader.setId(id);
        readersRepository.save(updatedReader);
    }
    @Transactional
    public void delete(Integer id){
        readersRepository.deleteById(id);
    }
}
