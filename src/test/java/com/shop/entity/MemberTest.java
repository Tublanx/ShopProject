package com.shop.entity;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.shop.repository.MemberRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberTest {

	@Autowired
	MemberRepository memberRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Test
	@DisplayName("Auditing 테스트")
	@WithMockUser(username = "lgh", roles = "USER")
	public void auditingTest() {
		Member newMember = new Member();
		memberRepository.save(newMember);

		entityManager.flush();
		entityManager.clear();

		Member member = memberRepository.findById(newMember.getId()).orElseThrow(EntityNotFoundException::new);

		System.out.println("reg time: " + member.getRegTime());
		System.out.println("update time: " + member.getUpdateTime());
		System.out.println("create member: " + member.getCreatedBy());
		System.out.println("modify member: " + member.getModifiedBy());
	}

}
