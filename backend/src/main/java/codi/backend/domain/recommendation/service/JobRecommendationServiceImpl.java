package codi.backend.domain.recommendation.service;

import codi.backend.domain.member.entity.Member;
import codi.backend.domain.member.repository.MemberRepository;
import codi.backend.domain.profile.entity.Profile;
import codi.backend.domain.profile.repository.ProfileRepository;
import codi.backend.domain.recommendation.dto.JobRecommendationDto;
import codi.backend.domain.recommendation.repository.JobRecommendationRepository;
import codi.backend.global.exception.BusinessLogicException;
import codi.backend.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Transactional
@Service
public class JobRecommendationServiceImpl implements JobRecommendationService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final JobRecommendationRepository jobRecommendationRepository;

    // TODO change member, profile service DI
    public JobRecommendationServiceImpl(MemberRepository memberRepository, ProfileRepository profileRepository, JobRecommendationRepository jobRecommendationRepository) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
        this.jobRecommendationRepository = jobRecommendationRepository;
    }

    @Override
    public JobRecommendationDto.Response recommendJobs(String memberId) {
        Member member = getMember(memberId);
        Profile profile = getProfile(member.getProfile().getId());

        int age = calculateAge(member.getBirth(), LocalDate.now());

        return jobRecommendationRepository.findTop3JobCategories(profile.getDisability(), profile.getSeverity(), age);
    }

    private int calculateAge(String birthDateString, LocalDate currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
        return Period.between(birthDate, currentDate).getYears();
    }

    private Member getMember(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private Profile getProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PROFILE_NOT_FOUND));
    }
}
