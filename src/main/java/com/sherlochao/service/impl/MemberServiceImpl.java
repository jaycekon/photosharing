package com.sherlochao.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.sherlochao.constant.MemberState;
import com.sherlochao.constant.SharedState;
import com.sherlochao.dao.SharedDao;
import com.sherlochao.model.Shared;
import com.sherlochao.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sherlochao.dao.MemberDao;
import com.sherlochao.model.Member;
import com.sherlochao.model.QueryResponseVo;
import com.sherlochao.service.MemberService;
import com.sherlochao.util.Digests;
import com.sherlochao.util.StringUtils;

@Transactional
@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	@Resource
	private MemberDao memberDao;

	@Resource
	private SharedDao sharedDao;
	
	@Override
	public void save(Member member) {
//        if (StringUtils.isEmpty(member.getMemberAvatar())) {
//            member.setMemberAvatar("/upload/img/avatar/01.jpg");// 会员头像
//        }头像位置未确定
        //MD5加密
        if (StringUtils.isNotEmpty(member.getMemberPasswd())) {
            member.setMemberPasswd(Digests.md5AndSalt(member.getMemberPasswd()));
        }
		String time = DateUtils.getDate1();
        member.setMemberCreatetime(time);// 会员创建时间
		member.setMemberRole(MemberState.REGISTERMEMBER); //角色
		member.setAdministratorState(MemberState.ALLOW); //权限
		member.setMemberAvatar("/upload/img/avatar/1480392927120.png");//默认头像
        memberDao.save(member);
	}

	@Override
	public void update(Member member) {
		memberDao.update(member);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Member findById(Integer memberId) {
        List<Member> menbers = memberDao.findById(memberId);
        Member menber = null;
        if (menbers != null && menbers.size() != 0) {
            menber = menbers.get(0);
        }
        return menber;
	}

	@Override
	public Member findMemberByName(String memberName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryResponseVo findMemberNumByNameOrMobile(Member member) {
		QueryResponseVo rep = new QueryResponseVo();
        List<Member> qureyMembers = memberDao.findMemberByNameOrMobile(member);  //先判断用户名或者密码是否可用
        if (qureyMembers.size() == 0) {
            rep.setQueryNum(0);
        } else {
            List<Member> qureyMember = memberDao.findMemberByMobile(member.getMemberMobile());  //如果用户名或者密码不可用，找出原因返回
            if (qureyMember.size() == 0) {
                rep.setQueryNum(1);
                rep.setQueryMsg("该用户名已经注册了");
            } else {
                rep.setQueryNum(1);
                rep.setQueryMsg("该手机号码已经注册了");
            }
        }
        return rep;
	}

	@Override
	public Member findMemberByNameOrMobile(Member member) {
		List<Member> qureyMembers = memberDao.findMemberByNameOrMobile(member);  //先判断用户名或者密码是否可用
        if (qureyMembers.size() != 0) {
            return qureyMembers.get(0);
        }
        return null;
	}

	@Override
	public void updateMember(Member member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Member findMemberById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updatePass(String newPasswd, Integer memberId) {
		List<Member> menbers = memberDao.findMemberById(memberId);
        Member member = null;
        if (menbers != null && menbers.size() != 0) {
            member = menbers.get(0);
        }
        try {
            member.setMemberPasswd(Digests.md5AndSalt(newPasswd));
            memberDao.updateMember(member);
            return 1;
        } catch (Exception e) {
            //System.out.println("更新密码失败" + e.getMessage());
            return 0;
        }
	}

	@Override
	public int updateMember(String data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePass(String memberPasswd, String newPasswd,
			Integer memberId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateFace(String path, Integer memberId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Member findMemberByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member findMemberByMobile(String memberMobile) {
		List<Member> menbers = memberDao.findMemberByMobile(memberMobile);
        Member member = null;
        if (menbers != null && menbers.size() != 0) {
            member = menbers.get(0);
        }
        return member;
	}

	@Override
	public void updateweiMember(Integer memberId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int findMemberCount(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Member findMember(Member member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member findMemberByOpenId(Member member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member findByMemberId(Integer memberId) {
		return memberDao.findByMemberId(memberId);
	}

	@Override
	public void updateAuthorityMember(Member member) {
		//首先更改member权限状态
		memberDao.update(member);
		//批量修改该用户的分享状态
		Integer sharedState = SharedState.SHAREDDELETE;
		List<Shared> sharedList = sharedDao.listSharedByMemberId(member.getMemberId(), sharedState);
		if(member.getAdministratorState() == MemberState.ALLOW){
			for(Shared shared : sharedList){
				shared.setAdministratorState(SharedState.ALLOW);
				sharedDao.updateShared(shared);
			}
		}else{
			for(Shared shared : sharedList){
				shared.setAdministratorState(SharedState.PROHIBIT);
				sharedDao.updateShared(shared);
			}
		}
	}

	@Override
	public List<Member> listAllMembers() {
		Integer role = MemberState.REGISTERMEMBER;
		return memberDao.listAllMembers(role);
	}

}
