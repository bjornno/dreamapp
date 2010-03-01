#/bin/bash




SAVE_PATH="./"

##Create stores
SERVER_TRUSTSTORE='server.truststore.jks'
CLIENT_TRUSTSTORE='client.truststore.jks'
SERVER_KEYSTORE='server.keystore.jks'
CLIENT_KEYSTORE='client.keystore.jks'

#Set all passwords
SERVER_TRUSTSTORE_PASSWORD='123456'
CLIENT_TRUSTSTORE_PASSWORD='123456'
SERVER_KEYSTORE_PASSWORD='123456'
CLIENT_KEYSTORE_PASSWORD='123456'

#Set data
SERVER_KEY_DATA='CN=localhost,OU=TEST,O=Company,L=OSLO,C=NO'
CLIENT_KEY_DATA='CN=localhost,OU=Test,O=Company,L=OSLO,C=NO'


set -x

#Create Client/Server keys
keytool -genkey -alias myClient -keyalg rsa -dname $CLIENT_KEY_DATA -keystore $SAVE_PATH$CLIENT_KEYSTORE -storepass $CLIENT_KEYSTORE_PASSWORD -keypass $CLIENT_KEYSTORE_PASSWORD
keytool -genkey -alias myServer -keyalg rsa -dname $SERVER_KEY_DATA -keystore $SAVE_PATH$SERVER_KEYSTORE -storepass $SERVER_KEYSTORE_PASSWORD -keypass $SERVER_KEYSTORE_PASSWORD

#Export client certificate and import into server truststore
keytool -export  -alias myClient -file $SAVE_PATH$''client.cer -keystore $SAVE_PATH$CLIENT_KEYSTORE -storepass $CLIENT_KEYSTORE_PASSWORD
keytool -import -noprompt -trustcacerts -alias myClient -file $SAVE_PATH''client.cer -keystore $SAVE_PATH$SERVER_TRUSTSTORE -storepass $SERVER_TRUSTSTORE_PASSWORD



#Export server certificate and import into client truststore
keytool -export  -alias myServer -file $SAVE_PATH$''server.cer -keystore $SAVE_PATH$SERVER_KEYSTORE -storepass $SERVER_KEYSTORE_PASSWORD
keytool -import -noprompt -trustcacerts -alias myServer -file $SAVE_PATH''server.cer -keystore $SAVE_PATH$CLIENT_TRUSTSTORE -storepass $CLIENT_TRUSTSTORE_PASSWORD

#Create a client keystore PKCS12 (Windows)
keytool -importkeystore -srckeystore $CLIENT_KEYSTORE -destkeystore client.keystore.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass $CLIENT_KEYSTORE_PASSWORD -deststorepass $CLIENT_KEYSTORE_PASSWORD -srcalias myClient -destalias myClient -srckeypass $CLIENT_KEYSTORE_PASSWORD -destkeypass $CLIENT_KEYSTORE_PASSWORD
