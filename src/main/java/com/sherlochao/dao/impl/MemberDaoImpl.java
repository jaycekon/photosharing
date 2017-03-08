package com.sherlochao.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sherlochao.dao.MemberDao;
import com.sherlochao.model.Member;

@Repository("memberDao")
public class MemberDaoImpl implements MemberDao{
	
	@Resource
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(Member member) {
		this.getSession().save(member);
	}

	@Override
	public void update(Member member) {
		this.getSession().saveOrUpdate(member);
	}

	@Override
	public void delete(Long id) {
		
	}

	@Override
	public List<Member> findById(Integer memberId) {
		String hql = "from Member where memberId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, memberId);
		return query.list();
	}

	@Override
	public Member findByMemberId(Integer memberId) {
		String hql = "from Member where memberId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, memberId);
		return (Member)query.uniqueResult();
	}

	@Override
	public List<Member> findMemberByName(String memberName) {
		return null;
	}

	@Override
	public void updateMember(Member member) {
		this.getSession().saveOrUpdate(member);
	}

	@Override
	public List<Member> findMemberById(Integer id) {
		String hql = "from Member where memberId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, id);
		return query.list();
	}

	@Override
	public List<Member> findMemberByEmail(String memberEmail) {
		return null;
	}

	@Override
	public List<Member> findMemberByMobile(String memberMobile) {
		String hql = "from Member where memberMobile = ?";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, memberMobile);
		return query.list();
	}

	@Override
	public int findMemberCount(Member member) {
		return 0;
	}

	@Override
	public List<Member> findMember(Member member) {
		return null;
	}

	@Override
	public List<Member> findMemberByNameOrMobile(Member member) {
		String hql = "from Member where memberName = ? or memberMobile = ?";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, member.getMemberName());
		query.setString(1, member.getMemberMobile());
		return query.list();
	}

	@Override
	public List<Member> findMemberByOpenId(Member member) {
		return null;
	}

	@Override
	public List<Member> listAllMembers(Integer role) {
		String hql = "from Member where memberRole = ?";
		Query query = this.getSession().createQuery(hql);
		query.setInteger(0, role);
		return query.list();
	}

}
