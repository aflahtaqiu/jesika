package id.koridor50.jesika.ui.chat_room;

import java.io.InputStream;

public class DialogflowCredentialss {

    private static DialogflowCredentialss mCredentials;
    private InputStream mInputStream;

    private DialogflowCredentialss() {
        //Prevent form the reflection api.
        if (mCredentials != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }


    public static DialogflowCredentialss getInstance() {
        if (mCredentials == null) { //if there is no instance available... create new one
            synchronized (DialogflowCredentialss.class) {
                if (mCredentials == null) mCredentials = new DialogflowCredentialss();
            }
        }
        return mCredentials;
    }

    //Make singleton from serialize and deserialize operation.
    protected DialogflowCredentialss readResolve() {
        return getInstance();
    }


    public void setInputStream(InputStream is) {
        mInputStream = is;
    }

    protected InputStream getInputStream() {
        return mInputStream;
    }
}