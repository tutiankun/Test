package com.example.tk.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtils.class);
    private static final String UNKNOWN = "unKnown";
    private static final String LOCAL_IPV4 = "127.0.0.1";
    private static final String LOCAL_IPOV6 = "0:0:0:0:0:0:0:1";

    private IpUtils() {

    }

    public static String getIp(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("X-Forwarded-For");
            if (StringUtils.isNotEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                int index = ip.indexOf(',');
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
            ip = request.getHeader("X-Real-IP");
            if (StringUtils.isNotEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                return ip;
            }
            ip = request.getRemoteAddr();
            if (LOCAL_IPV4.equals(ip) || LOCAL_IPOV6.equals(ip)) {
                ip = getLocalIp();
            }
            return ip;
        } finally {
            LOGGER.debug("本次获取的IP为: [{}]", ip);
        }
    }

    public static boolean isInternalIp(String ip) {
        byte[] addr;
        try {
            addr = java.net.Inet4Address.getByName(ip).getAddress();
        } catch (UnknownHostException e) {
            return false;
        }
        return isInternalIp(addr);
    }

    public static boolean isInternalIp(byte[] addr) {
        boolean internal = false;
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        //10.x.x.x/8
        final byte section1 = 0x0A;
        //172.16.x.x/12
        final byte section2 = (byte) 0xAC;
        final byte section3 = (byte) 0x10;
        final byte section4 = (byte) 0x1F;
        //192.168.x.x/16
        final byte section5 = (byte) 0xC0;
        final byte section6 = (byte) 0xA8;
        switch (b0) {
            case section1:
                internal = true;
                break;
            case section2:
                if (b1 >= section3 && b1 <= section4) {
                    internal = true;
                }
                break;
            case section5:
                if (b1 == section6) {
                    internal = true;
                }
                break;
            default:
                break;
        }
        return internal;
    }

    private static String getLocalIp() {
        // 根据网卡取本机配置的IP
        InetAddress inet = null;
        try {
            inet = InetAddress.getLocalHost();
        } catch (Exception e) {
            LOGGER.warn("获取本地IP失败");
        }
        if (null != inet) {
            return inet.getHostAddress();
        }
        return null;
    }
}
