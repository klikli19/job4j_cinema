package ru.job4j_cinema.service;

import ru.job4j_cinema.dto.FileDto;
import ru.job4j_cinema.model.File;

import java.util.Optional;

public interface FileService {

    File save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    void deleteById(int id);
}
