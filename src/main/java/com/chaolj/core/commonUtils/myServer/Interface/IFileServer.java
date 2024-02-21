package com.chaolj.core.commonUtils.myServer.Interface;

import java.util.List;
import com.chaolj.core.commonUtils.myDto.DataResultDto;
import com.chaolj.core.commonUtils.myServer.Models.FileObjectDto;

/*
 * 文件服务接口
 * */
public interface IFileServer {
    DataResultDto<String> CreatePath(String path);

    DataResultDto<String> MovePath(String sourcePath, String targetPath);

    DataResultDto<String> DeletePath(String path);

    DataResultDto<String> DeleteFile(String path, String fileName);

    DataResultDto<FileObjectDto> GetFile(String path, String fileName);

    DataResultDto<List<FileObjectDto>> GetFiles(String path, boolean recursive, String searchPattern);

    DataResultDto<List<FileObjectDto>> GetFiles(String path);

    DataResultDto<FileObjectDto> Upload(String path, String fileName, byte[] fileBytes);

    DataResultDto<byte[]> Download(String path, String fileName);

    DataResultDto<byte[]> GetThumbnail(String path, String fileName, Integer width, Integer height);

    DataResultDto<byte[]> GetThumbnail(String path, String fileName);
}
