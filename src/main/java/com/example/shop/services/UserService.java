package com.example.shop.services;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import com.example.shop.R;
import com.example.shop.config.EmailConfig;
import com.example.shop.config.JwtConfig;
import com.example.shop.entity.User;
import com.example.shop.exception.BusinessException;
import com.example.shop.repository.UserRepository;
import com.example.shop.validator.UserLoginDto;
import com.example.shop.validator.UserRegisterDto;
import com.example.shop.validator.VerificationCodeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.Jwts;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.Date;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final EmailConfig emailConfig;
    private final StringRedisTemplate stringRedisTemplate;


    public static final String REDIS_VERIFY_CODE_KEY_PREFIX = "register:verificationCode:email:";
    public static final Duration EXPIRE_TIME_VERIFY_CODE = Duration.ofMinutes(3);


    public UserService(PasswordEncoder passwordEncoder, JwtConfig jwtConfig, UserRepository userRepository, JavaMailSender javaMailSender, EmailConfig emailConfig, StringRedisTemplate stringRedisTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.emailConfig = emailConfig;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public R login(UserLoginDto userLoginDto) throws JsonProcessingException {
        Optional<User> userOptional = userRepository.findUserByUsername(userLoginDto.getName());
        User user = userOptional.orElseThrow(() -> new BusinessException("用户找不到"));
        String passwordInDb = user.getPassword();
        if (passwordEncoder.matches(userLoginDto.getPassword(), passwordInDb)) {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(user);
            String token = Jwts.builder()
                    .subject(json)
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + jwtConfig.getJwtExpired()))
                    .signWith(jwtConfig.getJwtKey())
                    .compact();
            return R.ok("登陆成功", token);
        } else {
            return R.error("登录失败");
        }
    }

    public R sendMessageWithAttachment(String toEMail ,String randomString ) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(emailConfig.getUsername());
        helper.setTo(toEMail);
        helper.setSubject("这是一条注册验证码");
        helper.setText("请在三分钟内输入验证码"+randomString);
        javaMailSender.send(message);

        return R.ok();
    }

    public R verificationCode(VerificationCodeDto verificationCode) {
        String email = verificationCode.getEmail();
        if (email == null || email.isEmpty()) {
            return R.error("邮箱不能为空");
        }
        String randomString = RandomUtil.randomString(4);
        stringRedisTemplate.opsForValue().set(REDIS_VERIFY_CODE_KEY_PREFIX + email, randomString, EXPIRE_TIME_VERIFY_CODE);

        try {
            sendMessageWithAttachment(email, randomString);
        } catch (MessagingException e) {
            return R.error("发送邮件失败: " + e.getMessage());
        }

        return R.ok();
    }
    public User findByUsernameOrThrow(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new BusinessException("用户不存在: " + username));
    }
    public Optional<User> findUserById(Long userId) {
        return userRepository.findUserById(userId);
    }



    @SneakyThrows
    public R register(UserRegisterDto userRegisterDto) {
        String code = stringRedisTemplate.opsForValue().get(REDIS_VERIFY_CODE_KEY_PREFIX + userRegisterDto.getEmail());
        Assert.notNull(code,()-> new BusinessException("请重新获取验证码"));
        Assert.equals(userRegisterDto.getVerificationCode(),code,()->new BusinessException("无效的验证码"));

        Optional<User> userOptional = userRepository.findUserByUsername(userRegisterDto.getName());


        Assert.isTrue(userOptional.isEmpty(),()->new BusinessException("用户已存在"));

        User user = new User();
        user.setUsername(userRegisterDto.getName());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userRepository.save(user);


        stringRedisTemplate.opsForValue().getAndDelete(REDIS_VERIFY_CODE_KEY_PREFIX + userRegisterDto.getEmail());


        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);
        String token = Jwts.builder()
                .subject(json)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtConfig.getJwtExpired()))
                .signWith(jwtConfig.getJwtKey())
                .compact();
        return R.ok("注册成功",token);

    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }



}

