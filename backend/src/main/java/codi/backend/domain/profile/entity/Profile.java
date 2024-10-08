package codi.backend.domain.profile.entity;

import codi.backend.domain.favorite.entity.Favorite;
import codi.backend.domain.member.entity.Member;
import codi.backend.domain.mentoring.entity.Mentoring;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "new_profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String imgUrl;

    @Column
    private String desiredJob;

    @Column
    private String disability;

    @Column
    private String severity;

    @Enumerated(value = EnumType.STRING)
    @Column
    private EmploymentStatus employmentStatus;

    @OneToOne
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mentoring> mentoringList = new ArrayList<>();

    public void addFavorite(Favorite favorite) {
        this.favorites.add(favorite);
        favorite.setProfile(this);
    }

    public void removeFavorite(Favorite favorite) {
        this.favorites.remove(favorite);
        favorite.setProfile(null);
    }

    public void addMentoring(Mentoring mentoring) {
        this.mentoringList.add(mentoring);
        mentoring.setProfile(this);
    }

    public void removeMentoring(Mentoring mentoring) {
        this.mentoringList.remove(mentoring);
        mentoring.setProfile(null);
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum EmploymentStatus {
        JOBSEEKER("취업 준비생"),
        STUDENT("학생"),
        PREPARINGCHANGEJOB("이직 준비중"),
        MENTEE("표시하지 않음(멘티로 표시)"),
        EMPLOYED("재직중");

        @Getter
        private final String employmentStatus;

        EmploymentStatus(String employmentStatus) {
            this.employmentStatus = employmentStatus;
        }

        public static EmploymentStatus employmentStatusOf(String name) {
            for (EmploymentStatus employmentStatus : EmploymentStatus.values()) {
                if (employmentStatus.getEmploymentStatus().equals(name)) {
                    return employmentStatus;
                }
            }
            return null;
        }
    }

    @Builder
    public Profile(Long id, String nickname, String imgUrl, String desiredJob, String disability, String severity, EmploymentStatus employmentStatus) {
        this.id = id;
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.desiredJob = desiredJob;
        this.disability = disability;
        this.severity = severity;
        this.employmentStatus = employmentStatus;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
