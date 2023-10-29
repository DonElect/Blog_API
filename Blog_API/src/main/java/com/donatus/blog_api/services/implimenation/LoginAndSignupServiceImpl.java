package com.donatus.blog_api.services.implimenation;

import com.donatus.blog_api.security.JWTGenerator;
import com.donatus.blog_api.dto.AuthResponseDTO;
import com.donatus.blog_api.dto.LoginDTO;
import com.donatus.blog_api.dto.SignupDTO;
import com.donatus.blog_api.dto.user.UserResponseDTO;
import com.donatus.blog_api.model.entity.Roles;
import com.donatus.blog_api.model.entity.UserEntity;
import com.donatus.blog_api.exception.EmailAlreadyExistException;
import com.donatus.blog_api.exception.InvalidEmailException;
import com.donatus.blog_api.exception.UserNotFoundException;
import com.donatus.blog_api.exception.WrongPasswordException;
import com.donatus.blog_api.repository.RolesRepository;
import com.donatus.blog_api.repository.UserRepository;
import com.donatus.blog_api.services.LoginAndSignupServices;
import com.donatus.blog_api.services.email.EmailServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginAndSignupServiceImpl implements LoginAndSignupServices {
    private final ModelMapper mapper = new ModelMapper();

    private final UserRepository userRepo;
    private final RolesRepository rolesRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    private final EmailServices emailServices;

    Logger LOG = LoggerFactory.getLogger(LoginAndSignupServiceImpl.class);

    @Override
    public String registerUser(SignupDTO user) {
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
            newUser.setVerified(false);

            userRepo.save(newUser);

            return verificationLink(user.getEmail());
        }

        Roles role1 = new Roles();
        Roles role2 = new Roles();

        role2.setName("SUPER_ADMIN");
        newUser.addRoles(role2);
        role1.setName("USER");
        newUser.addRoles(role1);
        newUser.setVerified(false);

        userRepo.save(newUser);

        return verificationLink(user.getEmail());
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

        String token = jwtGenerator.generateToken(authentication, 24L);

        return new AuthResponseDTO(token);
    }

    @Override
    public void removeUser(String adminId) {
        userRepo.deleteById(adminId);
    }

    @Override
    public UserResponseDTO userDetails(String userId) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Invalid user ID!"));

        return mapper.map(user, UserResponseDTO.class);
    }

    @Override
    public String verifyEmail(String token) {
        if (!jwtGenerator.validateToken(token)){
            return "Link has expired. Request for a new link";
        }

        String email = jwtGenerator.getEmailFromJWT(token);

        UserEntity user = userRepo.findUserEntityByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Admin with email: "+ email+" not found."));

        user.setVerified(true);

        userRepo.save(user);

        return "Email verified. Go back and Login";
    }

    @Override
    public String verificationRequest() {
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();

        return verificationLink(email);
    }

    private String verificationLink(String email){
        if (email == null){
            return "Invalid email!";
        }
        String token = jwtGenerator.generateSignupToken(email, 1L);

        String message = String.format("""
                Complete your registration by clicking on the link below.
                
                http://192.168.0.134:1002/api/v1/users/verify_email/%s\s
                Link will expire after one minute""", token);

        new Thread(() -> {
            emailServices.sendSimpleMessage(
                    email,
                    "Email Verification",
                    message
            );

            LOG.info("Verification email sent to {}", email);
        }
        ).start();

        return "Check your mail and complete your registration";
    }
}
