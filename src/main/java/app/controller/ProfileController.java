package app.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.dto.DoctorProfileDto;
import app.model.dto.PatientProfileDto;
import app.model.dto.UpdatePasswordDto;
import app.model.dto.UpdateProfileDto;
import app.model.dto.UserUpdatedResponseDto;
import app.model.entity.Doctor;
import app.model.entity.Patient;
import app.model.security.AuthorityName;
import app.model.security.User;
import app.repository.DoctorRepository;
import app.repository.PatientRepository;
import app.security.JwtTokenUtil;
import app.security.JwtUser;
import app.security.repository.UserRepository;

@RestController
@RequestMapping("/me")
public class ProfileController {

    private SimpleGrantedAuthority patientAuthority = new SimpleGrantedAuthority(AuthorityName.ROLE_USER.toString());
    private SimpleGrantedAuthority doctorAuthority = new SimpleGrantedAuthority(AuthorityName.ROLE_ADMIN.toString());

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getProfileInfo() {

        JwtUser principal = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findById(principal.getId()).get();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        if (authorities.contains(patientAuthority)) {
            Patient patient = patientRepository.findByUser(user);
            PatientProfileDto profileDto = PatientProfileDto.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .firstName(user.getFirstname())
                    .lastName(user.getLastname())
                    .patientId(patient.getId())
                    .birthDate(patient.getBirthDate())
                    .pesel(patient.getPesel())
                    .phoneNumber(patient.getPhoneNumber())
                    .build();
            return ResponseEntity.ok(profileDto);
        } else if (authorities.contains(doctorAuthority)) {
            Doctor doctor = doctorRepository.findByUser(user);
            DoctorProfileDto profileDto = DoctorProfileDto.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .firstName(user.getFirstname())
                    .lastName(user.getLastname())
                    .doctorId(doctor.getId())
                    .healthcareUnitId(doctor.getHealthcareUnit().getId())
                    .birthDate(doctor.getBirthDate())
                    .pesel(doctor.getPesel())
                    .phoneNumber(doctor.getPhoneNumber())
                    .specialisation(doctor.getSpecialisation())
                    .build();
            return ResponseEntity.ok(profileDto);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("No proper authority found");
    }

    // TODO: update na profilu się pierdoli, do rozkminki
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity updateProfileInfo(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody UpdateProfileDto updateProfileRequest) {

        JwtUser principalID = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findById(principalID.getId()).get();

        String oldToken = authorizationHeader.split("Bearer ")[1];

        // First update account-related data
        // TODO: verify if username/email exist in DB before
        // TODO: same while registering!
        user.setUsername(updateProfileRequest.getUsername());
        user.setEmail(updateProfileRequest.getEmail());
        user.setFirstname(updateProfileRequest.getFirstName());
        user.setLastname(updateProfileRequest.getLastName());
        user = userRepository.save(user);

        // Then update patient data
        Patient patient = patientRepository.findByUser(user);
        patient.setPhoneNumber(updateProfileRequest.getPhoneNumber());
        patient.setUser(user);
        patientRepository.save(patient);

        // Change authentication data

        // Finally, return new token
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        String newToken = jwtTokenUtil.generateToken(userDetails);
        UserUpdatedResponseDto userUpdatedResponse = new UserUpdatedResponseDto(newToken);

        return ResponseEntity.ok(userUpdatedResponse);
    }

    // TODO: jakakolwiek weryfikacja hasla (dlugosc, znaki specjalne etc)?
    @RequestMapping(value = "/update/pass", method = RequestMethod.POST)
    public ResponseEntity updatePassword(@RequestBody UpdatePasswordDto updatePasswordRequest) {

        JwtUser principalID = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findById(principalID.getId()).get();

        String encodedPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Password update successful");
    }
}
