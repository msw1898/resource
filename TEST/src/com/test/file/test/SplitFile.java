package com.test.file.test;

import com.test.file.utils.ConsUtil;
import com.test.file.vo.FileInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SplitFile {
    public static void splitFile(FileInfo fileInfo, int fileCount,String flag) throws IOException {
        FileInputStream fis = new FileInputStream(fileInfo.getFileFolderPath()+"/"+fileInfo.getFileName()+fileInfo.getFileType());
        FileChannel inputChannel = fis.getChannel();
        long fileSize = inputChannel.size();
        //平均值
        long average = fileSize / fileCount;
        //缓存块大小，自行调整
        long bufferSize = 200;
        // 申请一个缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.valueOf(bufferSize + ""));
        //子文件开始位置
        long startPosition = 0;
        //子文件结束位置
        long endPosition = average < bufferSize ? 0 : average - bufferSize;
        for (int i = 0; i < fileCount; i++) {
            if (i + 1 != fileCount) {
                // 读取数据
                int read = inputChannel.read(byteBuffer, endPosition);
                readW:
                while (read != -1) {
                    byteBuffer.flip();//切换读模式
                    byte[] array = byteBuffer.array();
                    for (int j = 0; j < array.length; j++) {
                        byte b = array[j];
                        //判断\n\r
                        if (b == 10 || b == 13) {
                            endPosition += j;
                            break readW;
                        }
                    }
                    endPosition += bufferSize;
                    byteBuffer.clear(); //重置缓存块指针
                    read = inputChannel.read(byteBuffer, endPosition);
                }
            } else {
                endPosition = fileSize; //最后一个文件直接指向文件末尾
            }
            FileOutputStream fos = new FileOutputStream(fileInfo.getFileFolderPath()+"/"+flag+"/"+fileInfo.getFileName() + (i + 1) + fileInfo.getFileType());
            FileChannel outputChannel = fos.getChannel();
            inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);//通道传输文件数据
            outputChannel.close();
            fos.close();
            startPosition = endPosition + 1;
            endPosition += average;
        }
        inputChannel.close();
        fis.close();

    }

    public static void main(String[] args) throws Exception {
        FileInfo fileInfo=new FileInfo();
        fileInfo.setFileFolderPath(ConsUtil.FILEFOLDERPATH);
        fileInfo.setFileName(ConsUtil.FILENAME);
        fileInfo.setFileType(ConsUtil.FILETYPE);

        int fileCount = 50;
        System.out.println("begin=================");
        long startTime = System.currentTimeMillis();
        splitFile(fileInfo, fileCount,ConsUtil.FLAG);
        long endTime = System.currentTimeMillis();
        System.out.println("end================================" );
        System.out.println("耗费时间： " + (endTime - startTime) + " ms");
    }

}
