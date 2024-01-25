package org.example;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

public class SnowflakeJwtGenerator {

    private SnowflakeJwtGenerator() {
    }

    public static String generateSnowflakeJWT(String account, String user, Path privateKeyFile, Date issued, Date expires) throws Exception {

        RSAPrivateCrtKey privateKey = readPrivateKey(privateKeyFile);
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privateKey.getModulus(), privateKey.getPublicExponent());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

        String qualifiedUserName = account.toUpperCase(Locale.ROOT) + "." + user.toUpperCase(Locale.ROOT);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String publicKeyFp = "SHA256:" + Base64.getEncoder().encodeToString(digest.digest(publicKey.getEncoded()));

        return JWT.create()
                .withIssuer(qualifiedUserName + "." + publicKeyFp)
                .withSubject(qualifiedUserName)
                .withIssuedAt(issued)
                .withExpiresAt(expires)
                .sign(algorithm);
    }

    private static RSAPrivateCrtKey readPrivateKey(Path file) throws Exception {
        String key = Files.readString(file, Charset.defaultCharset());

        String privateKeyPEM =
                key.replace("-----BEGIN PRIVATE KEY-----", "")
                        .replaceAll(System.lineSeparator(), "")
                        .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateCrtKey) keyFactory.generatePrivate(keySpec);
    }

}
