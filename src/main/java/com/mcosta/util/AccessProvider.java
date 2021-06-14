package com.mcosta.util;

import com.mcosta.model.User;
import com.mcosta.model.UserTypeEnum;
import javafx.fxml.Initializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AccessProvider {
    
    private static User user = new User(1L, "marcio", "123", "marcio", UserTypeEnum.ADMIN);;

    public static List<Page> getPagesByUserType(UserTypeEnum userType) {
        switch(userType) {
            case ATTENDANT:
                return pagesForAttendant();
            case LIBRARIAN:
                return pagesForLibrarian();
            case ADMIN:
                return pagesForAdmin();
        }
        return null;
    }

    private static List<Page> pagesForAttendant(){
        List<Page> pages = new ArrayList<Page>();
        return pages;
    }

    private static List<Page> pagesForLibrarian(){
        List<Page> pages = new ArrayList<Page>();
        pages.add(new Page("user", "Usuário"));
        pages.add(new Page("author", "Autor"));
        pages.add(new Page("reader", "Leitor"));
        pages.add(new Page("publisher", "Editora"));
        pages.add(new Page("book", "Livro"));
        return pages;
    }

    private static List<Page> pagesForAdmin(){
        Set<Page> pages = new HashSet<Page>();
        pages.addAll(pagesForAttendant());
        pages.addAll(pagesForLibrarian());
        return new ArrayList<>(pages);
    }

    public static class Page{
        private String uri;
        private String label;

        Page(String uri, String label) {
            this.uri = uri;
            this.label = label;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((uri == null) ? 0 : uri.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Page other = (Page) obj;
            if (uri == null) {
                if (other.uri != null)
                    return false;
            } else if (!uri.equals(other.uri))
                return false;
            return true;
        }
        
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AccessProvider.user = user;
    }
}
