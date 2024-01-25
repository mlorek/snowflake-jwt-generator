import org.example.SnowflakeJwtGenerator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestGenerator {

    @Test
    void test() throws Exception {
        String account = "dummy";
        String user = "test";
        Path path = Path.of("src/test/resources/rsa_key.p8");

        Date issuedDate = new Date(1000);
        Date expiresDate = new Date(2000);

        String jwt = SnowflakeJwtGenerator.generateSnowflakeJWT(account, user, path, issuedDate, expiresDate);

        String expected = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJEVU1NWS5URVNULlNIQTI1NjpiYXYxenZOTHY0TzRnTjhpSmdYU2k1T1dZQmxMdFpIU1VDMmNzSWZJWDNNPSIsInN1YiI6IkRVTU1ZLlRFU1QiLCJpYXQiOjEsImV4cCI6Mn0.VTdjDFVwIOwI1T0RmMVyGy0cteWG8IpbXMV5cCNc2amfjJfbDm4qWiqVXTwBOrvTzOYSMud6Ib9DtXrYpKlM72nEFEjoj8lXtdy66MXjxU98IUvLLMH7nVpiML94a2VY8b0mgZ3wm394emvKkG3CQikRRP0YYNXl9sJc3-6xSR8H9B8rmyx1vuIkWNiVAWhNGUyFk0qzIggRZSEgeexvsrjxdcooKnuZwDCVOa1veFYbXNVfMKDsmHD2jfWUnDMBD6mhczPvyxjKn1zAas4HrjkF0Y3fg9nFitBiaA2IpHbLJpvxl5mQ_Rb_jxJNc_mArmkS9Cd41S7hYezIajaQzw";
        assertEquals(expected, jwt);
    }
}
