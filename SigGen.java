import java.io.*;
import java.security.*;

public class SigGen {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java SigGen <nameOfFileToSign>");
        } else {
            try {
                /*
                 * create key paire use keytool:
                 * $ keytool -genkey -alias signLegal -keystore demo-cert.pkcs12 -validity 1800
                 */
                // read keystore file
                KeyStore ks = KeyStore.getInstance("PKCS12");
                FileInputStream ksfis = new FileInputStream("demo-cert.pkcs12");
                BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
                // open keystore and get private key
                // alias is 'signLeal', kpasswd/spasswd is 'vagrant'
                ks.load(ksbufin, "1234qwer".toCharArray());
                PrivateKey priv = (PrivateKey) ks.getKey("user", "1234qwer".toCharArray());

                /* Create a Signature object and initialize it with the private key */
                Signature rsaSig = Signature.getInstance("SHA256withRSA");
                rsaSig.initSign(priv);
                /* Update and sign the data */
                FileInputStream fis = new FileInputStream(args[0]);
                BufferedInputStream bufin = new BufferedInputStream(fis);
                byte[] buffer = new byte[1024];
                int len;
                while (bufin.available() != 0) {
                    len = bufin.read(buffer);
                    rsaSig.update(buffer, 0, len);
                }

                bufin.close();
                /*
                 * Now that all the data to be signed has been read in,
                 * generate a signature for it
                 */
                byte[] realSig = rsaSig.sign();

                /* Save the signature in a file */
                FileOutputStream sigfos = new FileOutputStream("sig");
                sigfos.write(realSig);
                sigfos.close();
                /*
                 * public key file can export from keystore use keytool:
                 * $ keytool -export -keystore examplestanstore -alias signLegal -file
                 * StanSmith.cer
                 */

            } catch (Exception e) {
                System.err.println("Caught exception " + e.toString());
            }
        }
    }
}