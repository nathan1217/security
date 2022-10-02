import java.io.*;
import java.security.*;

public class SigVer {
    public static void main(String[] args) {
        /* Verify a DSA signature */
        if (args.length != 3) {
            System.out.println("Usage: SigVer publickeyfile signaturefile datafile");
        } else {
            try {
                /* import encoded public cert */
                FileInputStream certfis = new FileInputStream(args[0]);
                java.security.cert.CertificateFactory cf = java.security.cert.CertificateFactory.getInstance("X.509");
                java.security.cert.Certificate cert = cf.generateCertificate(certfis);
                PublicKey pubKey = cert.getPublicKey();
                /* create a Signature object and initialize it with the public key */
                Signature rsaSig = Signature.getInstance("SHA256withRSA");
                rsaSig.initVerify(pubKey);

                /* Update and verify the data */
                FileInputStream datafis = new FileInputStream(args[2]);
                BufferedInputStream bufin = new BufferedInputStream(datafis);
                byte[] buffer = new byte[1024];
                int len;
                while (bufin.available() != 0) {
                    len = bufin.read(buffer);
                    rsaSig.update(buffer, 0, len);
                }
                bufin.close();

                /* input the signature bytes */
                FileInputStream sigfis = new FileInputStream(args[1]);
                byte[] sigToVerify = new byte[sigfis.available()];
                sigfis.read(sigToVerify);
                sigfis.close();

                boolean verifies = rsaSig.verify(sigToVerify);
                System.out.println("signature verifies: " + verifies);
            } catch (Exception e) {
                System.err.println("Caught exception " + e.toString());
            }
            ;
        }
    }
}
