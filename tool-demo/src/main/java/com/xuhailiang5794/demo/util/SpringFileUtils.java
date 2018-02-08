package com.xuhailiang5794.demo.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

/**
 * <pre>
 * Spring文件下载工具类
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/8 13:36
 */
public class SpringFileUtils {
    /**
     * <pre>
     * 说明:通过文件类路径获取资源并封装到ResponseEntity
     * </pre>
     *
     * @param classpathFilename 资源类路径（例如：/xxx.txt）
     * @param filename          下载显示的文件名
     * @author hailiang.xu
     * @since 2017年2月8日 下午1:54:04
     */
    public static ResponseEntity getResponseEntityByClasspath(
            String classpathFilename, String filename) {
        return getResponseEntity(new ClassPathResource(classpathFilename),
                System.currentTimeMillis() + ".xlsx");
    }

    /**
     * <pre>
     * 说明:通过文件路径获取资源并封装到ResponseEntity
     * </pre>
     *
     * @param filepath 资源名（例如：E:\xxx\xxx.txt）
     * @param filename 下载显示的文件名
     * @author hailiang.xu
     * @since 2017年2月8日 下午1:54:04
     */
    public static ResponseEntity getResponseEntityFilename(
            String filepath, String filename) throws URISyntaxException {
        return getResponseEntity(new FileSystemResource(filepath),
                filename);
    }

    private static ResponseEntity getResponseEntity(
            Resource resource, String filename) {
        return ResponseEntity
                .ok()
                .headers(initHeaders(filename))
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private static HttpHeaders initHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }
}
