package com.wzp.www.base.net;

import com.wzp.www.base.helper.L;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by wengzhipeng on 17/3/3.
 */

public class HttpsHelper {
    private static final String TAG = HttpsHelper.class.getSimpleName();
    /**
     * 跳过Host验证
     */
    public static final HostnameVerifier NO_HOST_VERIFIER = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public class SSLFactory {
        private SSLSocketFactory mSSLSocketFactory;
        private X509TrustManager mTrustManager;

        public SSLSocketFactory getSSLSocketFactory() {
            return mSSLSocketFactory;
        }

        public void setSSLSocketFactory(SSLSocketFactory SSLSocketFactory) {
            mSSLSocketFactory = SSLSocketFactory;
        }

        public X509TrustManager getTrustManager() {
            return mTrustManager;
        }

        public void setTrustManager(X509TrustManager trustManager) {
            mTrustManager = trustManager;
        }
    }

    /**
     * 获取OkHttpClient设置sslSocketFactory所需参数,传null则绕过验证,用法如下
     * <p>HttpsHelper.SSLFactory sslFactory = getSSLSocketFactory(null, null, null);</p>
     * <p>new OkHttpClient.Builder().sslSocketFactory(sslFactory.getSSLSocketFactory(),
     * sslFactory.getTrustManager())</p>
     */
    public SSLFactory getSSLSocketFactory(InputStream[] certificates, InputStream
            bksStream, String password) {
        SSLFactory sslFactory = new SSLFactory();
        try {
            TrustManager[] trustManagers = prepareTrustManager(certificates);
            KeyManager[] keyManagers = prepareKeyManager(bksStream, password);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            X509TrustManager trustManager;
            if (trustManagers != null) {
                trustManager = new MyTrustManager(chooseTrustManager(trustManagers));
            } else {
                trustManager = new UnSafeTrustManager();
            }
            sslContext.init(keyManagers, new TrustManager[]{trustManager}, null);
            sslFactory.setSSLSocketFactory(sslContext.getSocketFactory());
            sslFactory.setTrustManager(trustManager);
            return sslFactory;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (KeyManagementException e) {
            throw new AssertionError(e);
        } catch (KeyStoreException e) {
            throw new AssertionError(e);
        }
    }

    private TrustManager[] prepareTrustManager(InputStream[] certificates) {
        if (certificates != null && certificates.length > 0) {
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance
                        ("X.509");
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null);
                for (int i = 0; i < certificates.length; i++) {
                    InputStream certificate = certificates[i];
                    keyStore.setCertificateEntry(Integer.toString(i),
                            certificateFactory.generateCertificate(certificate));
                    try {
                        if (certificate != null) {
                            certificate.close();
                        }
                    } catch (IOException e) {
                        L.e(TAG, e.getMessage());
                    }
                }
                TrustManagerFactory trustManagerFactory = TrustManagerFactory
                        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                return trustManagers;
            } catch (NoSuchAlgorithmException e) {
                L.e(TAG, e.getMessage());
            } catch (CertificateException e) {
                L.e(TAG, e.getMessage());
            } catch (KeyStoreException e) {
                L.e(TAG, e.getMessage());
            } catch (Exception e) {
                L.e(TAG, e.getMessage());
            }
        }
        return null;
    }

    private KeyManager[] prepareKeyManager(InputStream bksStream, String password) {
        if (bksStream != null && password != null) {
            try {
                KeyStore keyStore = KeyStore.getInstance("BKS");
                keyStore.load(bksStream, password.toCharArray());
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance
                        (KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, password.toCharArray());
                return keyManagerFactory.getKeyManagers();
            } catch (KeyStoreException e) {
                L.e(TAG, e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                L.e(TAG, e.getMessage());
            } catch (UnrecoverableKeyException e) {
                L.e(TAG, e.getMessage());
            } catch (CertificateException e) {
                L.e(TAG, e.getMessage());
            } catch (IOException e) {
                L.e(TAG, e.getMessage());
            } catch (Exception e) {
                L.e(TAG, e.getMessage());
            }
        }
        return null;
    }

    private X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    private class MyTrustManager implements X509TrustManager {
        private X509TrustManager mDefaultTrustManager;
        private X509TrustManager mLocalTrustManager;

        public MyTrustManager(X509TrustManager localTrustManager) throws
                NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory factory = TrustManagerFactory.getInstance
                    (TrustManagerFactory.getDefaultAlgorithm());
            factory.init((KeyStore) null);
            mDefaultTrustManager = chooseTrustManager(factory.getTrustManagers());
            mLocalTrustManager = localTrustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws
                CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws
                CertificateException {
            try {
                mDefaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException e) {
                mLocalTrustManager.checkServerTrusted(chain, authType);
            }
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private class UnSafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws
                CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws
                CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }

}
