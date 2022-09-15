package com.Zapi;

import com.datamanager.ConfigManager;
import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import com.thed.zephyr.cloud.rest.client.JwtGenerator;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JwtGeneratorClass {
    static ConfigManager sys = new ConfigManager();
    static Logger log = LogManager.getLogger("JwtGeneratorClass");
    public static String generateJWTToken(String cycleURI,String requestType)
    {
        String zephyrBaseUrl = sys.getProperty("zephyr.BASE_URL");
        String accessKey = sys.getProperty("zephyr.accessKey");
        String secretKey = sys.getProperty("zephyr.secretKey");
        String accountId = sys.getProperty("zephyr.accountId");
        ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl,accessKey,secretKey,accountId).build();
        JwtGenerator jwtGenerator = client.getJwtGenerator();
        String jwt = null;
        try {
            URI uri = new URI(cycleURI);
            int expirationInSec = 360;
            jwt = jwtGenerator.generateJWT(requestType,uri,expirationInSec);
            log.info("JWT Token is : " + jwt);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jwt;
    }
}
