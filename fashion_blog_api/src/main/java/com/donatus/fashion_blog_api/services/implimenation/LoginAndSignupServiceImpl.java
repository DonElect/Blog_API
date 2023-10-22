package com.donatus.fashion_blog_api.services.implimenation;

import com.donatus.fashion_blog_api.config.JWTGenerator;
import com.donatus.fashion_blog_api.dto.AuthResponseDTO;
import com.donatus.fashion_blog_api.dto.LoginDTO;
import com.donatus.fashion_blog_api.dto.SignupDTO;
import com.donatus.fashion_blog_api.dto.user.UserResponseDTO;
import com.donatus.fashion_blog_api.model.entity.Roles;
import com.donatus.fashion_blog_api.model.entity.UserEntity;
import com.donatus.fashion_blog_api.exception.EmailAlreadyExistException;
import com.donatus.fashion_blog_api.exception.InvalidEmailException;
import com.donatus.fashion_blog_api.exception.UserNotFoundException;
import com.donatus.fashion_blog_api.exception.WrongPasswordException;
import com.donatus.fashion_blog_api.repository.RolesRepository;
import com.donatus.fashion_blog_api.repository.UserRepository;
import com.donatus.fashion_blog_api.services.LoginAndSignupServices;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginAndSignupServiceImpl implements LoginAndSignupServices {
    private final ModelMapper mapper = new ModelMapper();

    private final UserRepository userRepo;
    private final RolesRepository rolesRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;


    @Override
    public UserResponseDTO registerUser(SignupDTO user) {
        if (userRepo.existsByEmail(user.getEmail())){
            throw new EmailAlreadyExistException("Email already exist!");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())){
            throw new WrongPasswordException("Password mismatch!");
        }

        // Encrypt password
        user.setPassword(encoder.encode(user.getPassword()));

        UserEntity newUser = mapper.map(user, UserEntity.class);

        if (rolesRepo.existsByName("SUPER_ADMIN")){
            Roles role1 = new Roles();
            role1.setName("USER");
            newUser.addRoles(role1);

            UserEntity savedAdmin = userRepo.save(newUser);
            return mapper.map(savedAdmin, UserResponseDTO.class);
        }

        Roles role1 = new Roles();
        Roles role2 = new Roles();

        role2.setName("SUPER_ADMIN");
        newUser.addRoles(role2);
        role1.setName("USER");
        newUser.addRoles(role1);

        UserEntity savedAdmin = userRepo.save(newUser);

        return mapper.map(savedAdmin, UserResponseDTO.class);
    }

    @Override
    public AuthResponseDTO loginUser(LoginDTO loginDTO) {

        UserEntity userEntity = userRepo.findUserEntityByEmail(loginDTO.getEmail())
                .orElseThrow(()-> new InvalidEmailException("Email not registered!"));

        if (!encoder.matches(loginDTO.getPassword(), userEntity.getPassword())){
            throw new WrongPasswordException("Invalid password!");
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return new AuthResponseDTO(token);
    }

    @Override
    public void removeUser(Long adminId) {
        userRepo.deleteById(adminId);
    }

    @Override
    public UserResponseDTO userDetails(Long userId) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Invalid user ID!"));

        return mapper.map(user, UserResponseDTO.class);
    }
}
