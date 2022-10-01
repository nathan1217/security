import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import sun.misc.*;

public class SecurityDemo {

    private File keystoreFile;

    private String keyStoreType;

    private char[] password;

    private String alias;

    private File exportedPrivateKeyFile;

    private File exportedPublicKeyFile;

    public static KeyPair getKeyPair(KeyStore keystore, String alias, char[] password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {

        Key key = keystore.getKey(alias, password);

        if (key instanceof PrivateKey) {

            Certificate cert = keystore.getCertificate(alias);

            PublicKey publicKey = cert.getPublicKey();

            return new KeyPair(publicKey, (PrivateKey) key);

        }

        return null;

    }

    public void exportPrivate() throws Exception {

        KeyStore keystore = KeyStore.getInstance(keyStoreType);

        keystore.load(new FileInputStream(keystoreFile), password);

        KeyPair keyPair = getKeyPair(keystore, alias, password);

        BASE64Encoder encoder = new BASE64Encoder();

        PrivateKey privateKey = keyPair.getPrivate();

        String encoded = encoder.encode(privateKey.getEncoded());

        FileWriter fw = new FileWriter(exportedPrivateKeyFile);

        fw.write("-----BEGIN PRIVATE KEY-----\n");

        fw.write(encoded);

        fw.write("\n");

        fw.write("-----END PRIVATE KEY-----");

        fw.close();

    }

    public void exportCertificate() throws Exception {

        KeyStore keystore = KeyStore.getInstance(keyStoreType);

        BASE64Encoder encoder = new BASE64Encoder();

        keystore.load(new FileInputStream(keystoreFile), password);

        Certificate cert = (Certificate) keystore.getCertificate(alias);

        String encoded = encoder.encode(cert.getEncoded());

        FileWriter fw = new FileWriter(exportedPublicKeyFile);

        fw.write("-----BEGIN CERTIFICATE-----\n");

        fw.write(encoded);

        fw.write("\n");

        fw.write("-----END CERTIFICATE-----");

        fw.close();

    }

    public static void main(String args[]) throws Exception {

        SecurityDemo export = new SecurityDemo();

        export.keystoreFile = new File("C:\\sap\\code\\demo\\.keystore");

        export.keyStoreType = "JKS";

        export.password = "1234qwer".toCharArray();

        export.alias = "mykey";

        export.exportedPrivateKeyFile = new File("C:\\sap\\code\\demo\\exported-pkcs8.key");

        export.exportedPublicKeyFile = new File("C:\\sap\\code\\demo\\exported-public.key");

        export.exportPrivate();

        export.exportCertificate();

    }

}
