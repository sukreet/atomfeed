package org.ict4h.atomfeed.client.util;

import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.feed.atom.Link;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Util {

    public static URI getSelfLink(Feed feed) {
        return getArchiveLink(feed,"self");
    }

    public static URI getPreviousLink(Feed feed) {
        return getArchiveLink(feed,"prev-archive");
    }

    public static URI getNextLink(Feed feed) {
        return getArchiveLink(feed,"next-archive");
    }

   private static URI getArchiveLink(Feed feed, String archiveType) {
        try {
            for (Object obj : feed.getAlternateLinks()) {
                Link l = (Link) obj;
                if (l.getRel().equals(archiveType)) {
                    return new URI(l.getHref());
                }
            }
            return null;
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Bad %s link", archiveType));
        }
    }


    public static String getExceptionString(Exception e) {
        StringBuffer sb = null;
        try {
            sb = new StringBuffer();
            if (e.getMessage() != null) sb.append(e.getMessage());

            ByteArrayOutputStream stream = null;
            PrintStream ps = null;
            try {
                stream = new ByteArrayOutputStream();
                ps = new PrintStream(stream);
                e.printStackTrace(ps);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try { ps.close(); } catch(Exception e1) {}
            }

            sb.append(stream.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sb.toString();
    }
}
