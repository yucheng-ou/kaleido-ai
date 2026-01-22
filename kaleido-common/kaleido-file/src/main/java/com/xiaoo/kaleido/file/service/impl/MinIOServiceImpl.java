package com.xiaoo.kaleido.file.service.impl;

import com.xiaoo.kaleido.file.config.MinIOProperties;
import com.xiaoo.kaleido.file.model.ImageInfo;
import com.xiaoo.kaleido.file.service.IMinIOService;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * MinIO服务实现类
 */
@Slf4j
public class MinIOServiceImpl implements IMinIOService {

    private final MinioClient minioClient;
    private final MinIOProperties properties;

    private static final String SEPARATOR = "/";

    public MinIOServiceImpl(MinioClient minioClient, MinIOProperties properties) {
        this.minioClient = minioClient;
        this.properties = properties;
        // 初始化Bucket
        init();
    }

    /**
     * 初始化Bucket
     */
    private void init() {
        try {
            createBucketIfNotExists(properties.getBucketName());
            log.info("MinIO服务初始化完成，默认Bucket: {}", properties.getBucketName());
        } catch (Exception e) {
            log.error("MinIO服务初始化失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 如果Bucket不存在则创建
     */
    private void createBucketIfNotExists(String bucketName) throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info("创建Bucket: {}", bucketName);
        }
    }

    @Override
    public String getBasisUrl() {
        return properties.getEndpoint() + SEPARATOR + properties.getBucketName() + SEPARATOR;
    }

    /******************************  Operate Bucket Start  ******************************/

    @Override
    public boolean bucketExists() throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.getBucketName()).build());
    }

    @Override
    public String getBucketPolicy() throws Exception {
        return minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(properties.getBucketName()).build());
    }

    @Override
    public List<Bucket> getAllBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    @Override
    public Optional<Bucket> getBucket() throws Exception {
        return getAllBuckets().stream().filter(b -> b.name().equals(properties.getBucketName())).findFirst();
    }

    @Override
    public void removeBucket() throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(properties.getBucketName()).build());
    }

    /******************************  Operate Bucket End  ******************************/

    /******************************  Operate Files Start  ******************************/

    @Override
    public boolean isObjectExist(String objectName) {
        boolean exist = true;
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(properties.getBucketName()).object(objectName).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    @Override
    public boolean isFolderExist(String objectName) {
        boolean exist = false;
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(properties.getBucketName()).prefix(objectName).recursive(false).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && objectName.equals(item.objectName())) {
                    exist = true;
                }
            }
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    @Override
    public List<Item> getAllObjectsByPrefix(String prefix, boolean recursive) throws Exception {
        List<Item> list = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(properties.getBucketName()).prefix(prefix).recursive(recursive).build());
        if (objectsIterator != null) {
            for (Result<Item> o : objectsIterator) {
                Item item = o.get();
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public InputStream getObject(String objectName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(properties.getBucketName()).object(objectName).build());
    }

    @Override
    public InputStream getObject(String objectName, long offset, long length) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(objectName)
                        .offset(offset)
                        .length(length)
                        .build());
    }

    @Override
    public Iterable<Result<Item>> listObjects(String prefix, boolean recursive) {
        return minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(properties.getBucketName())
                        .prefix(prefix)
                        .recursive(recursive)
                        .build());
    }

    @Override
    public ObjectWriteResponse uploadFile(MultipartFile file, String objectName, String contentType) throws Exception {
        InputStream inputStream = file.getInputStream();
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(objectName)
                        .contentType(contentType)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());
    }

    @Override
    public String uploadFile(String objectName, String fileName, boolean needUrl) throws Exception {
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(objectName)
                        .filename(fileName)
                        .build());
        if (needUrl) {
            String imageUrl = properties.getFileHost() + SEPARATOR + properties.getBucketName() + SEPARATOR + objectName;
            return imageUrl;
        }
        return "";
    }

    @Override
    public ObjectWriteResponse uploadFile(String objectName, InputStream inputStream) throws Exception {
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());
    }

    @Override
    public String uploadFile(String objectName, InputStream inputStream, boolean needUrl) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());
        if (needUrl) {
            String imageUrl = properties.getFileHost() + SEPARATOR + properties.getBucketName() + SEPARATOR + objectName;
            return imageUrl;
        }
        return "";
    }

    @Override
    public ObjectWriteResponse createDir(String objectName) throws Exception {
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(objectName)
                        .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                        .build());
    }

    @Override
    public String getFileStatusInfo(String objectName) throws Exception {
        return minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(objectName)
                        .build()).toString();
    }


    @Override
    public void removeFile(String objectName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(objectName)
                        .build());
    }

    @Override
    public void removeFiles(List<String> keys) {
        List<DeleteObject> objects = new LinkedList<>();
        keys.forEach(s -> {
            objects.add(new DeleteObject(s));
            try {
                removeFile(s);
            } catch (Exception e) {
                log.error("批量删除失败！error:{}", e);
            }
        });
    }

    @Override
    public String getPresignedObjectUrl(String objectName, Integer expires) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder().expiry(expires).bucket(properties.getBucketName()).object(objectName).build();
        return minioClient.getPresignedObjectUrl(args);
    }

    @Override
    public String getPresignedObjectUrl(String objectName) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(properties.getBucketName())
                .object(objectName)
                .method(Method.GET).build();
        return minioClient.getPresignedObjectUrl(args);
    }


    @Override
    public ImageInfo getImageInfo(String objectName) throws Exception {
        // 1. 获取文件状态信息
        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(properties.getBucketName())
                        .object(objectName)
                        .build());
        
        // 2. 获取文件大小
        long fileSize = stat.size();
        
        // 3. 获取文件扩展名和MIME类型
        String originalName = objectName;
        String extension = "";
        String mimeType = stat.contentType();
        
        int lastDotIndex = originalName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            extension = originalName.substring(lastDotIndex + 1).toLowerCase();
        }
        
        // 4. 获取图片类型
        String imageType = extension.isEmpty() ? "UNKNOWN" : extension.toUpperCase();
        
        // 5. 下载图片并读取宽高信息
        int width = 0;
        int height = 0;
        
        try (InputStream inputStream = getObject(objectName)) {
            BufferedImage image = ImageIO.read(inputStream);
            if (image != null) {
                width = image.getWidth();
                height = image.getHeight();
            } else {
                throw new IllegalArgumentException("无法读取图片文件或文件不是有效的图片格式: " + objectName);
            }
        }
        
        // 6. 创建并返回ImageInfo对象
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setObjectName(objectName);
        imageInfo.setImageType(imageType);
        imageInfo.setFileSize(fileSize);
        imageInfo.setWidth(width);
        imageInfo.setHeight(height);
        imageInfo.setMimeType(mimeType);
        imageInfo.setImageType(imageType);
        imageInfo.setExtension(extension);
        imageInfo.setCreateTime(stat.lastModified().toString());
        imageInfo.setLastModified(stat.lastModified().toString());
        
        return imageInfo;
    }

    /******************************  Operate Files End  ******************************/
}
