package com.epam.izh.rd.online.repository;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class SimpleFileRepository implements FileRepository {
    private int fileCounter = 0;
    private int dirCounter = 0;
    private boolean isFirstTimeFile = true;
    private boolean isFirstTimeDir = true;

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File file = new File(path);
        if (isFirstTimeFile) {
            file = new File("src/main/resources/" + path);
            isFirstTimeFile = false;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null){
            for (File listFile : listFiles) {
                if (!listFile.isDirectory()) {
                    fileCounter++;
                }
                if (listFile.isDirectory()) {
                    countFilesInDirectory(listFile.getPath());
                }
            }
        }
        return fileCounter;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File file = new File(path);
        if (isFirstTimeDir) {
            file = new File("src/main/resources/" + path);
            if(file.isDirectory()) {
                dirCounter++;
            }
            isFirstTimeDir = false;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null){
            for (File listFile : listFiles) {
                if (listFile.isDirectory()) {
                    dirCounter++;
                    countDirsInDirectory(listFile.getPath());
                }
            }
        }
        return dirCounter;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        fileTo.mkdirs();
        File[] listFiles = fileFrom.listFiles();
        for (File listFile : listFiles) {
            if (listFile.getName().endsWith(".txt")) {
                try {
                    Files.copy(listFile.toPath(), new File(to + "/" + listFile.getName()).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(path);

        File filePath = new File(resource.getFile());
        filePath.mkdir();
        File file = new File(filePath, name);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.exists();

    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/" + fileName))) {
            while (reader.ready()) {
                result.append(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
