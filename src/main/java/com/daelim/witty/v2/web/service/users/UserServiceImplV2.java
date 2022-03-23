package com.daelim.witty.v2.web.service.users;


import com.daelim.witty.v1.web.repository.comments.CommentRepository;
import com.daelim.witty.v2.domain.*;
import com.daelim.witty.v2.web.controller.dto.users.GetFollowerResponse;
import com.daelim.witty.v2.web.controller.dto.users.UserLogInDTO;
import com.daelim.witty.v2.web.controller.dto.users.VerificationCodeDTO;
import com.daelim.witty.v2.web.exception.BadRequestException;
import com.daelim.witty.v2.web.repository.comments.CommentRepositoryV2;
import com.daelim.witty.v2.web.repository.users.*;
import com.daelim.witty.v2.web.repository.wittys.WittyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImplV2 implements UserServiceV2 {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final EmailVerificationRepository emailVerificationRepository;
    private final FollowRepository followRepository;
    private final WittyLikeRepository wittyLikeRepository;
    private final WittyRepository wittyRepository;
    private final CommentRepositoryV2 commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final EntityManager em;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User signUp(User user) throws Exception {
        userRepository.save(user);
        return user;
    }


    @Override
    public User login(UserLogInDTO userLogInDTO) throws Exception {
        return userRepository.findById(userLogInDTO.getUser_id())
                .filter(user -> user.getPassword().equals(userLogInDTO.getPassword())).orElse(null);
    }

    //아이디가 존재하지않으면 true 존재하면 false
    @Override
    public boolean isDuplicatedId(String id) throws Exception {
        return userRepository.findById(id).isPresent();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void emailConfirm(EmailVerification emailVerification) throws Exception {
        mailService.sendMail(emailVerification);
    }


    @Override
    public boolean verification(VerificationCodeDTO verificationCodeDTO) throws Exception {
        Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findByEmail(verificationCodeDTO.getEmail());
        if(emailVerificationOptional.isEmpty()) throw new BadRequestException("인증번호 요청을 먼저 해 주어야 합니다.");

        EmailVerification emailVerification = emailVerificationOptional.get();

        return emailVerification.getEmail().equals(verificationCodeDTO.getEmail()) &&
                emailVerification.getVerificationKey().equals(verificationCodeDTO.getKey());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addFollow(String toUserName, String fromUsername) throws Exception {
        User fromUser = userRepository.findById(fromUsername).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        User toUser = userRepository.findById(toUserName).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));

        Follow follow = new Follow(toUser, fromUser);
        followRepository.save(follow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelFollow(String toUserName, String fromUserName) throws Exception {
        User fromUser = userRepository.findById(fromUserName).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        User toUser = userRepository.findById(toUserName).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        Follow follow = followRepository.findByFromUserAndToUser(fromUser, toUser)
                .orElseThrow(() -> new BadRequestException("입력값 확인 필요"));

        followRepository.delete(follow);
    }

    public List<GetFollowerResponse> getFollower(String profileId, String loginId) throws Exception {
        String sb = "SELECT u.user_id, u.email, u.department ," +
                "if ((SELECT 1 FROM follow WHERE from_user_id = ? AND to_user_id = u.user_id), TRUE, FALSE) AS followState " +
                "FROM user u, follow f " +
                "WHERE u.user_id = f.from_user_id AND f.to_user_id = ?";

        Query query =  em.createNativeQuery(sb)
                         .setParameter(1, loginId)
                         .setParameter(2, profileId);

        JpaResultMapper result = new JpaResultMapper();
        return result.list(query, GetFollowerResponse.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likeWitty(Long wittyId, User user) throws Exception {
        Witty witty = wittyRepository.findById(wittyId).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        WittyLike likeInfo = new WittyLike(user, witty);
        wittyLikeRepository.save(likeInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlikeWitty(Long wittyId, User user) throws Exception {
        Witty witty = wittyRepository.findById(wittyId).orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        WittyLike wittyLike = wittyLikeRepository.findByWittyAndUser(witty, user)
                .orElseThrow(() -> new BadRequestException("입력값 확인 필요"));

        wittyLikeRepository.delete(wittyLike);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likeComment(Long commentId, User user) throws Exception {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException("입력값 확인 필요"));

        CommentLike likeInfo = new CommentLike(user, comment);
        commentLikeRepository.save(likeInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlikeComment(Long commentId, User user) throws Exception {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException("입력값 확인 필요"));

        CommentLike likeInfo = commentLikeRepository.findByCommentAndUser(comment, user)
                .orElseThrow(() -> new BadRequestException("입력값 확인 필요"));
        commentLikeRepository.delete(likeInfo);
    }
}
