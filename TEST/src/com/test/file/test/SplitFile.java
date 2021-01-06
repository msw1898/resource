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
        //ƽ��ֵ
        long average = fileSize / fileCount;
        //������С�����е���
        long bufferSize = 200;
        // ����һ��������
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.valueOf(bufferSize + ""));
        //���ļ���ʼλ��
        long startPosition = 0;
        //���ļ�����λ��
        long endPosition = average < bufferSize ? 0 : average - bufferSize;
        for (int i = 0; i < fileCount; i++) {
            if (i + 1 != fileCount) {
                // ��ȡ����
                int read = inputChannel.read(byteBuffer, endPosition);
                readW:
                while (read != -1) {
                    byteBuffer.flip();//�л���ģʽ
                    byte[] array = byteBuffer.array();
                    for (int j = 0; j < array.length; j++) {
                        byte b = array[j];
                        //�ж�\n\r
                        if (b == 10 || b == 13) {
                            endPosition += j;
                            break readW;
                        }
                    }
                    endPosition += bufferSize;
                    byteBuffer.clear(); //���û����ָ��
                    read = inputChannel.read(byteBuffer, endPosition);
                }
            } else {
                endPosition = fileSize; //���һ���ļ�ֱ��ָ���ļ�ĩβ
            }
            FileOutputStream fos = new FileOutputStream(fileInfo.getFileFolderPath()+"/"+flag+"/"+fileInfo.getFileName() + (i + 1) + fileInfo.getFileType());
            FileChannel outputChannel = fos.getChannel();
            inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);//ͨ�������ļ�����
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
        System.out.println("�ķ�ʱ�䣺 " + (endTime - startTime) + " ms");
    }

}
