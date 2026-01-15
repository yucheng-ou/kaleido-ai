package com.xiaoo.kaleido.file.service;

import io.minio.ObjectWriteResponse;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

/**
 * MinIO服务接口
 */
public interface MinIOService {

    /**
     * 获取上传文件前缀路径
     *
     * @return 文件访问的基础URL，格式为：endpoint/bucketName/
     */
    String getBasisUrl();

    /******************************  Operate Bucket Start  ******************************/

    /**
     * 判断Bucket是否存在
     *
     * @param bucketName 存储桶名称
     * @return true：存在，false：不存在
     * @throws Exception MinIO操作异常
     */
    boolean bucketExists(String bucketName) throws Exception;

    /**
     * 获得Bucket的策略
     *
     * @param bucketName 存储桶名称
     * @return Bucket策略
     * @throws Exception MinIO操作异常
     */
    String getBucketPolicy(String bucketName) throws Exception;

    /**
     * 获得所有Bucket列表
     *
     * @return Bucket列表
     * @throws Exception MinIO操作异常
     */
    List<Bucket> getAllBuckets() throws Exception;

    /**
     * 根据bucketName获取其相关信息
     *
     * @param bucketName 存储桶名称
     * @return Bucket信息
     * @throws Exception MinIO操作异常
     */
    Optional<Bucket> getBucket(String bucketName) throws Exception;

    /**
     * 根据bucketName删除Bucket
     *
     * @param bucketName 存储桶名称
     * @throws Exception MinIO操作异常
     */
    void removeBucket(String bucketName) throws Exception;

    /******************************  Operate Bucket End  ******************************/

    /******************************  Operate Files Start  ******************************/

    /**
     * 判断文件是否存在
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件对象名称
     * @return true：文件存在，false：文件不存在
     */
    boolean isObjectExist(String bucketName, String objectName);

    /**
     * 判断文件夹是否存在
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件夹名称
     * @return true：文件夹存在，false：文件夹不存在
     */
    boolean isFolderExist(String bucketName, String objectName);

    /**
     * 根据文件前置查询文件
     *
     * @param bucketName 存储桶名称
     * @param prefix     前缀
     * @param recursive  是否使用递归查询
     * @return MinioItem 列表
     * @throws Exception MinIO操作异常
     */
    List<Item> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) throws Exception;

    /**
     * 获取文件流
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件对象名称
     * @return 文件输入流
     * @throws Exception MinIO操作异常
     */
    InputStream getObject(String bucketName, String objectName) throws Exception;

    /**
     * 断点下载
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件对象名称
     * @param offset     起始字节的位置
     * @param length     要读取的长度
     * @return 文件输入流
     * @throws Exception MinIO操作异常
     */
    InputStream getObject(String bucketName, String objectName, long offset, long length) throws Exception;

    /**
     * 获取路径下文件列表
     *
     * @param bucketName 存储桶名称
     * @param prefix     文件前缀
     * @param recursive  是否递归查找，false：模拟文件夹结构查找
     * @return 文件结果迭代器
     */
    Iterable<io.minio.Result<Item>> listObjects(String bucketName, String prefix, boolean recursive);

    /**
     * 使用MultipartFile进行文件上传
     *
     * @param bucketName  存储桶名称
     * @param file        上传的文件对象
     * @param objectName  文件在MinIO中的对象名称
     * @param contentType 文件内容类型
     * @return MinIO上传响应对象
     * @throws Exception 文件上传异常
     */
    ObjectWriteResponse uploadFile(String bucketName, MultipartFile file, String objectName, String contentType) throws Exception;

    /**
     * 上传本地文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件对象名称
     * @param fileName   本地文件路径
     * @param needUrl    是否需要返回URL
     * @return 文件URL或空字符串
     * @throws Exception MinIO操作异常
     */
    String uploadFile(String bucketName, String objectName, String fileName, boolean needUrl) throws Exception;

    /**
     * 通过流上传文件
     *
     * @param bucketName  存储桶名称
     * @param objectName  文件对象名称
     * @param inputStream 文件输入流
     * @return MinIO上传响应对象
     * @throws Exception MinIO操作异常
     */
    ObjectWriteResponse uploadFile(String bucketName, String objectName, InputStream inputStream) throws Exception;

    /**
     * 通过流上传文件
     *
     * @param bucketName  存储桶名称
     * @param objectName  文件对象名称
     * @param inputStream 文件输入流
     * @param needUrl     是否需要返回URL
     * @return 文件URL或空字符串
     * @throws Exception MinIO操作异常
     */
    String uploadFile(String bucketName, String objectName, InputStream inputStream, boolean needUrl) throws Exception;

    /**
     * 创建文件夹或目录
     *
     * @param bucketName 存储桶名称
     * @param objectName 目录路径
     * @return MinIO上传响应对象
     * @throws Exception MinIO操作异常
     */
    ObjectWriteResponse createDir(String bucketName, String objectName) throws Exception;

    /**
     * 获取文件信息
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件对象名称
     * @return 文件状态信息字符串
     * @throws Exception MinIO操作异常
     */
    String getFileStatusInfo(String bucketName, String objectName) throws Exception;

    /**
     * 拷贝文件
     *
     * @param bucketName    源存储桶名称
     * @param objectName    源文件对象名称
     * @param srcBucketName 目标存储桶名称
     * @param srcObjectName 目标文件对象名称
     * @return MinIO拷贝响应对象
     * @throws Exception MinIO操作异常
     */
    ObjectWriteResponse copyFile(String bucketName, String objectName, String srcBucketName, String srcObjectName) throws Exception;

    /**
     * 删除文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件对象名称
     * @throws Exception 文件删除异常
     */
    void removeFile(String bucketName, String objectName) throws Exception;

    /**
     * 批量删除文件
     *
     * @param bucketName 存储桶名称
     * @param keys       需要删除的文件列表
     */
    void removeFiles(String bucketName, List<String> keys);

    /**
     * 获取文件外链（带过期时间）
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件对象名称
     * @param expires    过期时间（单位：秒），小于等于7天
     * @return 预签名URL
     * @throws Exception MinIO操作异常
     */
    String getPresignedObjectUrl(String bucketName, String objectName, Integer expires) throws Exception;

    /**
     * 获取文件外链（默认GET方法，7天有效期）
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件对象名称
     * @return 预签名URL
     * @throws Exception MinIO操作异常
     */
    String getPresignedObjectUrl(String bucketName, String objectName) throws Exception;

    /**
     * 将URLDecoder编码转成UTF8
     *
     * @param str 编码字符串
     * @return UTF-8解码字符串
     * @throws UnsupportedEncodingException 编码异常
     */
    String getUtf8ByURLDecoder(String str) throws UnsupportedEncodingException;
}
