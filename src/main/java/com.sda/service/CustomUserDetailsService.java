package com.sda.service;

import com.sda.model.Privilege;
import com.sda.model.Role;
import com.sda.model.User;
import com.sda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailsService() {
        super();
    }

    /**
     * Klasa implementująca UserDetailsService umożliwia w spsób bezpieczny pobranie danch użytkownika i przekazuje je do
     * spring security, aby tam móc sobie na nich operować (trochę taki DataSource w jdbc). Ma jedną użyteczną metodę którą trzeba nadpisać
     * tj. loadUserByUsername
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        try {
            final User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + email);
            }
            // Tutaj jest jeden hint. Uzywamy wbudowanej klasy User ze spring security (klasa ta implementuje UserDetailsService, więc możemy jej obiekt)
            //Nie jest to konieczne ale tak poprostu jest łatwiej. W przeciwnym wypadku musiałbyś stwozyć swoją klasę user implementującą interfejs UserDetails.
            // Tylko ze klasa wbudowana User używa pojęcia uprawnien (uthorities), trzeba więc stworzyć jakąś liste takich uprawnień
            // Robimy to poprzez przekazania kolekcji Ról danego użytkownika (czyli w zasadzie listy Stringów) i przerabiamy je na
            // listę List<GrantedAuthority> , któr jest odczytywalna przez springa. Robomy to w metodach getAuthorities,getPrivileges oraz getGrantedAuthorities
            // Wymusza to na nas stworzenie Encji Role i privileges, których także masz przykłądy w tym folderze
            // Ogólnie warto bo później będzie można np. limitować użytkownikowi dostęp do pewnych stron na podstawie jego uprawnień
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }


    private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private final List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<String>();
        final List<Privilege> collection = new ArrayList<Privilege>();
        for (final Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }

        return privileges;
    }

    private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }


}
