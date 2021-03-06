package edu.nju.onlinestock.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 2016年11月11日17:09:30
 * presented by kylin
 * <p>
 * 文件输入输出工具类
 * <p>
 * All Rights Reserved
 */
public class FileIOHelper {

    /**
     * 读取目录根目录下所有文件名称
     *
     * @param path 根目录
     * @return 所有文件名称
     */
    public static List<String> getFileNames(String path) {

        File file = new File(path);
        File[] array = file.listFiles();

        List<String> stringList = new ArrayList<>();

        assert array != null : "make sure path != null";

        for (File anArray : array) {
            if (anArray.isFile())
                stringList.add(anArray.getName());
        }

        return stringList;
    }

    /**
     * 读取本地文本文件
     *
     * @param filePath 文件路径
     * @return 文件内容
     */
    public static String readTxtFile(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append('\n');
        }

        reader.close();
        inputStreamReader.close();
        fileInputStream.close();
        return builder.toString();
    }

    /**
     * 计算TXT文件的行数
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static int numberOfLines(String filePath) throws IOException {
        List<String> result = FileIOHelper.readTxtFileLines(filePath);
        return result.size();
    }

    /**
     * 读取本地文本文件
     *
     * @param filePath 文件路径
     * @return 文件内容
     */
    public static List<String> readTxtFileLines(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        ArrayList<String> strings = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null)
            strings.add(line);

        reader.close();
        inputStreamReader.close();
        fileInputStream.close();
        return strings;
    }


    /**
     * 创建文本文件
     *
     * @param content 文本内容
     * @param path    文件路径
     * @return 创建结果
     */
    public static boolean createTxtFile(String content, String path) throws IOException {
        File file = new File(path);
//        System.out.println("create file:"+path);
        boolean result = true;
        if (!file.exists()) {
            result = file.createNewFile();
        }
        if (result) {
            return FileIOHelper.writeTxtFile(content, path);
        } else
            return false;
    }

    /**
     * 在指定路径下创建指定文本文件
     *
     * @param content  文件内容
     * @param dir      文件目录
     * @param filepath 文件名称
     * @return 创建结果
     */
    public static boolean creaTxtFileInDirs(String content, String dir, String filepath) throws IOException {

        File file = new File(dir);
        boolean result = true;
        if (!file.exists())
            result = file.mkdirs();

        File file2 = new File(filepath);
        if (!file2.exists())
            result = file2.createNewFile();

        return FileIOHelper.writeTxtFile(content, filepath);

    }

    /**
     * 输出文本文件内容
     *
     * @param content 文件内容
     * @param path    路径
     * @return 结果
     */
    public static boolean writeTxtFile(String content, String path) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        bufferedWriter.write(content);

        bufferedWriter.close();
        writer.close();
        fileOutputStream.close();

        return true;
    }

}
