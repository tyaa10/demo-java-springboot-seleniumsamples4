package org.tyaa.demo.java.springboot.selenium.samples4.ui.utils;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.*;
// обязательно включить опцию для учетной записи в Gmail по адресу:
// https://myaccount.google.com/lesssecureapps
public class Mailer {
    public static Message[] fetchUnreadMails() throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        Store store = session.getStore("imaps");
        store.connect("imap.googlemail.com", 993, "YOUR_USERNAME", "YOUR_PASSWORD");
        System.out.println("CONNECTED");
        Folder inbox = store.getFolder( "INBOX" );
        inbox.open(Folder.READ_ONLY);
        // Fetch unseen messages from inbox folder
        Message[] messages = inbox.search(
            new FlagTerm(new Flags(Flags.Flag.SEEN), false));
        System.out.println("RECEIVED: " + messages.length);
        return messages;
    }
}
