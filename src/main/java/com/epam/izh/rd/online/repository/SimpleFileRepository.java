package com.epam.izh.rd.online.repository;

import java.io.File;

public class SimpleFileRepository implements FileRepository {
    private int counter = 0;
    private boolean isFirstTime = true;

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {

        File file = new File(path);
        if (isFirstTime) {
            file = new File("./src/main/resources/" + path); //прошу прощения за этот костыль, не разобрался с путями
            isFirstTime = false;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null){
            for (File listFile : listFiles) {
                if (!listFile.isDirectory())
                    counter++;
                if (listFile.isDirectory())
                    countFilesInDirectory(listFile.getPath());
            }
        }
        return counter;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        return 0;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        return;
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        return null;
    }
}
