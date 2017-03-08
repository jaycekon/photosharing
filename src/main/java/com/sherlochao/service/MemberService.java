package com.sherlochao.service;

import com.sherlochao.model.Member;
import com.sherlochao.model.QueryResponseVo;

import java.util.List;

public interface MemberService {
    /**
     * @param @param  pager
     * @param @return 设定文件
     * @return List<Account>    返回类型
     * @throws
     * @Title: findMemberList
     * @Description: TODO (查询所有的会员信息，用分页显示)
     */
    //List<Member> findMemberList(Pager pager);

    /**
     * 保存member信息
     *
     * @param member
     */
    void save(Member member);

    /**
     * 修改member信息
     *
     * @param member
     */
    void update(Member member);

    /**
     * @param @param id    设定文件
     * @return void    返回类型
     * @throws
     * @Title: delete
     * @Description: TODO (删除)
     */
    void delete(Long id);


    /**
     * 根据会员id获取会员信息
     *
     * @param memberId
     * @return
     */
    Member findById(Integer memberId);

    /**
     * 根据会员名查询会员信息
     *
     * @param memberName
     * @return
     */
    Member findMemberByName(String memberName);

    /**
     * 根据会员名  或者 memberMobile查询会员信息 主要是判断数量
     *
     * @param member
     * @return
     */
    QueryResponseVo findMemberNumByNameOrMobile(Member member);

    /**
     * 根据会员名  或者 memberMobile查询会员信息
     *
     * @param member
     * @return
     */
    Member findMemberByNameOrMobile(Member member);

    /**
     * 根据Member修改信息
     *
     * @param member
     */
    void updateMember(Member member);

    /**
     * 根据id获得会员
     *
     * @param id
     * @return
     */
    Member findMemberById(Integer id);

    /**
     * 根据memberId修改密码
     *
     * @param newPasswd
     * @param memberId
     */
    int updatePass(String newPasswd, Integer memberId);

    /**
     * 修改会员
     *
     * @param data
     * @return
     */
    int updateMember(String data);

    /**
     * 根据memberId修改密码
     *
     * @param newPasswd
     * @param memberId
     */
    int updatePass(String memberPasswd, String newPasswd, Integer memberId);

    /**
     * 修改头像
     *
     * @param path
     * @param memberId
     * @return
     */
    int updateFace(String path, Integer memberId);

    /**
     * 根据email查询会员
     *
     * @param email
     * @return
     */
    Member findMemberByEmail(String email);

    /**
     * 根据memberMobile查询member表
     *
     * @param memberMobile
     * @return
     */
    Member findMemberByMobile(String memberMobile);

    /**
     * 仅仅修改当前登陆人的登录时间
     *
     * @param memberId
     * @return
     */
    void updateweiMember(Integer memberId);


    /**
     * 获取总记录数
     *
     * @param member
     * @return int
     */
    int findMemberCount(Member member);

    /**
     * 根据会员信息查找
     *
     * @param
     * @return
     */
    Member findMember(Member member);

    /**
     * 根据第三方登录id查找
     *
     * @param
     * @return
     */
    Member findMemberByOpenId(Member member);

    Member findByMemberId(Integer memberId);

    void updateAuthorityMember(Member member);

    List<Member> listAllMembers();

}
