package com.ngocnb20.travel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**master data à thông tin không cần thay đổi không thực hiện CRUD vói master data chỉ read **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoleMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "role_id" , nullable = false,referencedColumnName = "id")
    private Role role;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "member_id" , nullable = false ,referencedColumnName = "id")
    private Member member;

    public RoleMember(Role role, Member member) {
        this.role = role;
        this.member = member;
    }
}
