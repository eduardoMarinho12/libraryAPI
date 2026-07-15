package com.library.libraryapi.service;

import com.library.libraryapi.domain.LibraryUser;
import com.library.libraryapi.repository.LibraryUserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibraryUserService {

    private final LibraryUserRepository userRepository;

    public LibraryUserService(LibraryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<LibraryUser> findAll() {
        return userRepository.findAll();
    }

    public LibraryUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));
    }

    public LibraryUser create(LibraryUser user) {
        return userRepository.save(user);
    }

    @Transactional
    public LibraryUser update(Long id, LibraryUser request) {
        LibraryUser user = findById(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        return user;
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Usuario nao encontrado");
        }
        userRepository.deleteById(id);
    }
}
