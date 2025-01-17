package org.tabula.instances;

import org.tabula.objects.Credentials;
import org.tabula.utils.fileutils.FileReaderWriter;
import org.tabula.utils.xmlutils.XMLHandler;
import org.w3c.dom.Document;

import java.io.File;
import java.util.List;

public class CredentialsInstance {

    static private CredentialsInstance instance;
    private List<Credentials> credentialsList;

    static public CredentialsInstance getInstance() {
        if(instance == null) {
            instance = new CredentialsInstance();
            return instance;
        }
        return instance;
    }

    private CredentialsInstance() {
        Document document = FileReaderWriter.readXMLFromFile(new File(FileReaderWriter.PATH_TO_CREDENTIALS_LIST));
        credentialsList = XMLHandler.getConnectionsFromXML(document);
    }

    public List<Credentials> getCredentialsList() {
        return credentialsList;
    }

    public Credentials findCredentials(String connectionName) {
        Credentials foundCredentials = credentialsList.stream().filter(credentials -> connectionName.equals(credentials.getConnectionName())).findFirst().orElse(null);
        if(foundCredentials == null) {
            foundCredentials = new Credentials();
            foundCredentials.setConnectionName(connectionName);
        }
        return foundCredentials;
    }
}
