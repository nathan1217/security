echo off
@rem generate keypair of root ca into keystore
keytool -genkeypair -alias rootca -validity 3650 -keysize 2048 -dname CN=rootca,OU=nathan,O=development,L=cd,ST=chengdu,C=cn -keystore demo-cert.pkcs12 -storepass 1234qwer -keypass 1234qwer -storetype pkcs12 -keyalg RSA 

@rem export certificate of root ca
keytool -exportcert -keystore demo-cert.pkcs12 -storepass 1234qwer -alias rootca -file rootca.cer

@rem generate keypair of second ca into keystore
keytool -genkeypair -alias secondca -validity 3650 -keysize 2048 -dname CN=secondca,OU=nathan,O=development,L=cd,ST=chengdu,C=cn -keystore demo-cert.pkcs12 -storepass 1234qwer -keypass 1234qwer -storetype pkcs12 -keyalg RSA 

@rem list the second ca
keytool -list -v -alias secondca -keystore demo-cert.pkcs12 -storepass 1234qwer

@rem generate certificate request for second ca
keytool -certreq -alias secondca -keystore demo-cert.pkcs12 -storepass 1234qwer -file secondca.csr

@rem generate certificate for second ca
keytool -gencert -alias rootca -keystore demo-cert.pkcs12 -storepass 1234qwer -infile secondca.csr -outfile secondca.cer

@rem import scondca.cer into keystore
keytool -importcert -file secondca.cer -alias secondca -keystore demo-cert.pkcs12 -storepass 1234qwer

@rem list the second ca
keytool -list -v -alias secondca -keystore demo-cert.pkcs12 -storepass 1234qwer

@rem generate keypair of user into keystore
keytool -genkeypair -alias user -validity 3650 -keysize 2048 -dname CN=user,OU=www.demo.com,O=nathan,L=cd,ST=chengdu,C=cn -keystore demo-cert.pkcs12 -storepass 1234qwer -keypass 1234qwer -storetype pkcs12 -keyalg RSA 

@rem list the user
keytool -list -v -alias user -keystore demo-cert.pkcs12 -storepass 1234qwer

@rem generate certificate request for user
keytool -certreq -alias user -keystore demo-cert.pkcs12 -storepass 1234qwer -file user.csr

@rem generate certificate for user
keytool -gencert -alias secondca -keystore demo-cert.pkcs12 -storepass 1234qwer -infile user.csr -outfile user.cer

@rem import user.cer into keystore
keytool -importcert -alias user -file user.cer -keystore demo-cert.pkcs12 -storepass 1234qwer

@rem list the second ca
keytool -list -v -alias user -keystore demo-cert.pkcs12 -storepass 1234qwer



