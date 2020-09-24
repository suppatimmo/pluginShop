package com.sda.security;

import com.sda.model.User;
import com.sda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    /**
     * W poniżeszej metodzie definiujesz co spring ma zrobić, gdy ktoś będzie chciał się zalogować do Twojej aplikacji,
     * zarówno przez rest api jak i np. przez stronę w thymeleafie.
     * W obiekcie typu Authentication przekazywane są dane autoryzacyjne które użytkownik przesyła w parametrze zapytania o nazwie Authentication
     *
     * @param auth
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        // Tu akurat przykłąd gdzie email użytkownika jest także jego loginem, ale równie dobrze można by szukać po loginie czy peselu czy co tam chcesz
        final User user = userRepository.findByEmail(auth.getName());
        if ((user == null)) {
            throw new BadCredentialsException("Invalid username");
        }

        final Authentication result = super.authenticate(auth);
        // Za pomocą UsernamePasswordAuthenticationToken tworzymy zbiorczy obiekt zawierający wszytskie niezbędne elementy
        // do wykonania utoryzacji typu basic i przekazujemy go springowi, żeby przeprowadził autoryzację typu Basic - czyli poprostu sprawdził lgin i hasło
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    /**
     * Poniższa metoda oznacza że Twój Authentication provider akceptuje metodę autoryzacji loginem i hasłem czyli Basic
     *
     * @param authentication
     * @return
     */

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}