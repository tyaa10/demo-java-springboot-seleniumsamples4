package org.tyaa.demo.java.springboot.selenium.samples4.ui.utils;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.*;

public class Mailer {
    public static Message[] fetchUnreadMails() throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        Store store = session.getStore("imaps");
        store.connect("imap.googlemail.com", 993, "tyaamariupol", "YOUR_PASSWORD");
        System.out.println("CONNECTED");
        Folder inbox = store.getFolder( "INBOX" );
        inbox.open(Folder.READ_ONLY);
        // Fetch unseen messages from inbox folder
        Message[] messages = inbox.search(
            new FlagTerm(new Flags(Flags.Flag.SEEN), false));
        /* Message[] messages = inbox.search(
            new FlagTerm(new Flags(Flags.Flag.RECENT), true)); */
        System.out.println("RECEIVED: " + messages.length);
        // Sort messages from recent to oldest
        /* Arrays.sort( messages, ( m1, m2 ) -> {
            try {
                return m2.getSentDate().compareTo( m1.getSentDate() );
            } catch ( MessagingException e ) {
                throw new RuntimeException( e );
            }
        } );
        System.out.println("SORTED"); */
        /* for ( Message message : messages ) {
            System.out.println(
                "sendDate: " + message.getSentDate()
                    + " subject:" + message.getSubject() );
        } */
        return messages;
    }
}
