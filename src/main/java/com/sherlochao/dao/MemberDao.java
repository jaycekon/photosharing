package com.sherlochao.dao;

import java.util.List;

import com.sherlochao.model.Member;

public interface MemberDao {

    void save(Member member);

    void update(Member member);

    void delete(Long id);

    List<Member> findById(Integer memberId);

    Member findByMemberId(Integer memberId);

    /**
     * 根据会员名查询会员信息
     *
     * @param memberName
     * @return
     */
    public List<Member> findMemberByName(String memberName);

    /**
     * 根据Member修改信息
     *
     * @param member
     */
    void updateMember(Member member);

    /**
     * 根据会员id查询会员信息
     *
     * @param id
     * @return
     */
    List<Member> findMemberById(Integer id);

    List<Member> findMemberByEmail(String memberEmail);

    List<Member> findMemberByMobile(String memberMobile);

    /**
     * 获取总记录数
     *
     * @return
     */
    int findMemberCount(Member member);

    /**
     * 根据会员信息查找
     *
     * @param
     * @return
     */
    List<Member> findMember(Member member);

    /**
     * 查找用户名或者手机是否已经被注册
     *
     * @param
     * @return
     */
    List<Member> findMemberByNameOrMobile(Member member);

    /**
     * 根据第三方登录id查找
     *
     * @param
     * @return
     */
    List<Member> findMemberByOpenId(Member member);

    List<Member> listAllMembers(Integer role);

}
