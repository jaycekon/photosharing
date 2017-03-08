package com.sherlochao.controller;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sherlochao.bean.BookApiBean;
import com.sherlochao.bean.MemberApiBean;
import com.sherlochao.common.C123SendSmsUtil;
import com.sherlochao.common.CommonConstants;
import com.sherlochao.common.Constants;
import com.sherlochao.common.ResponseMap;
import com.sherlochao.common.SMSValidCodeType;
import com.sherlochao.common.TheCode;
import com.sherlochao.model.Book;
import com.sherlochao.model.Favorites;
import com.sherlochao.model.Member;
import com.sherlochao.model.QueryResponseVo;
import com.sherlochao.model.SMSValidCode;
import com.sherlochao.service.BookService;
import com.sherlochao.service.MemberService;
import com.sherlochao.service.SMSValidCodeService;
import com.sherlochao.service.UserService;
import com.sherlochao.util.DateUtils;
import com.sherlochao.util.Digests;
import com.sherlochao.util.FileUtils;
import com.sherlochao.util.IpUtil;
import com.sherlochao.util.JsonUtils;
import com.sherlochao.util.MyBeanUtils;
import com.sherlochao.util.NumberUtils;
import com.sherlochao.util.ParamsUtils;
import com.sherlochao.util.PinYinUtil;

import static com.sherlochao.common.CacheUtils.getCacheUser;

/**
 * 会员中心接口
 */
@Slf4j
@Controller
@RequestMapping("/memberapi")
public class MemberApi {
	@Resource
	private MemberService memberService;

	@Resource
	SMSValidCodeService smsValidCodeService;

	@Resource
	BookService bookService;

	/**
	 * 会员注册
	 * 用户注册的时候，如果用户名或者手机存在，就提示已经注册过
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMap memberRegister(HttpServletRequest request) {
			//没有短信供应商 先忽略
//			ResponseMap responseMap = checkValidCode(name, validCode,
//					SMSValidCodeType.REGISTER);
//			if (null != responseMap) {
//				return responseMap;
//			}
		String name = ParamsUtils.getString(request
				.getParameter("name"));
		String password = ParamsUtils.getString(request
				.getParameter("password"));
			try {
				Member m = new Member();
				m.setMemberName(name);
				m.setMemberMobile(name); // 此处的name手机端是传手机号码过来
				// 修改为可以判断用户手机号码
				QueryResponseVo rep = memberService.findMemberNumByNameOrMobile(m);
				if (rep.getQueryNum() > 0) {
					return new ResponseMap(TheCode.FAIL.getName(),
							rep.getQueryMsg());
				} else {
					Member member = new Member();
					member.setMemberName(name);
					member.setMemberMobile(name);
					member.setMemberNickname(name);
					if (com.sherlochao.util.StringUtils.isMobile(name))
						member.setMemberMobile(name);// app端用户名限制是手机号

					member.setMemberPasswd(password);
					memberService.save(member);
					return new ResponseMap(TheCode.SUCCESS.getName(),
							TheCode.SUCCESS.getValue());
				}
			} catch (Exception e) {
				log.error("用户注册API出错", e);
				return new ResponseMap(TheCode.FAIL.getName(),
						TheCode.FAIL.getValue());

			}

	}

	/**
	 * 修改会员密码
	 *
	 * @param memberId
	 *            会员id
	 * @param password
	 *            旧密码
	 * @param newPasswd
	 *            新密码
	 * @return
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public ResponseMap updateMember(
			@RequestParam(value = "memberId")Integer memberId,
			@RequestParam(value = "password")String password,
			@RequestParam(value = "newPasswd")String newPasswd) {

		if (StringUtils.isBlank(newPasswd)) {
			return new ResponseMap(TheCode.NONEMPTY.getName(),
					TheCode.NONEMPTY.getValue());
		}
		try {
			Member m = memberService.findById(memberId);
			if (StringUtils.isNotEmpty(password)
					&& !Digests.validatePasswordWithMD5AndSalt(password,
							m.getMemberPasswd())) {
				return new ResponseMap(TheCode.FAIL.getName(), "原密码不正确");
			} else {
				memberService.updatePass(newPasswd, memberId);
				return new ResponseMap(TheCode.SUCCESS.getName(),
						TheCode.SUCCESS.getValue());
			}
		} catch (Exception e) {
			log.error("修改密码API出错", e);
			return new ResponseMap(TheCode.FAIL.getName(), "修改失败,请联系客服");

		}
	}

	/**
	 * 修改会员信息
	 *
	 * memberId 会员ID memberBirthday 会员生日 暂时不用 memberTruename 昵称 memberSex 性别
	 * memberAreainfo 会员地址 memberAvatar 会员头像
	 * 
	 * @return JSONObject
	 */
	@RequestMapping("/updateMember")
	@ResponseBody
	public JSONObject update(HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		try {
			request.setCharacterEncoding("UTF-8");
			Integer memberId = ParamsUtils.getInt(request
					.getParameter("memberId"));
			String nickname = ParamsUtils.getString(request
					.getParameter("nickname"));
			Integer memberSex = ParamsUtils.getInt(request.getParameter("sex"));
			String memberAreainfo = ParamsUtils.getString(request
					.getParameter("areaInfo"));
			String memberIntroduction = ParamsUtils.getString(request
					.getParameter("introduction"));
			Member m = memberService.findById(memberId);
			if (null == m) {
				jsonObj.put("result", 0);
				jsonObj.put("msg", "用户不存在");
			} else {
				m.setMemberId(memberId);
				m.setMemberSex(memberSex);
				m.setMemberAreainfo(memberAreainfo);
				m.setMemberNickname(nickname);
				m.setMemberIntroduction(memberIntroduction);
				if (ServletFileUpload.isMultipartContent(request)) {
					MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
					// 得到上传的图片数据
					MultipartFile portrait = multipartRequest.getFile("face");
					if (null != portrait) {
						Map<String, Object> map = FileUtils.fileUpload(
								portrait, CommonConstants.FILE_BASEPATH,
								Constants.MEMBER_UPLOAD_URL, request, "images",
								1);
						if ("true".equals(map.get("success") + "")) {
							m.setMemberAvatar(map.get("result") + "");
						}
					}
				}
				memberService.update(m);
				jsonObj.put("result", 1);
				jsonObj.put("msg", "修改成功");
			}
		} catch (Exception e) {
			log.error("修改用户信息API出错", e);
			jsonObj.put("result", 0);
			jsonObj.put("msg", "服务器异常");
		}

		return jsonObj;
	}

	/**
	 * 找回密码接口
	 *
	 * @param mobile
	 * @param validCode
	 * @param newPasswd
	 * @return
	 */
	@RequestMapping("/findPassWord")
	@ResponseBody
	public ResponseMap FindPassWord(
			@RequestParam(required = false, value = "mobile", defaultValue = "") String mobile,
			@RequestParam(required = false, value = "validCode", defaultValue = "") String validCode,
			@RequestParam(value = "newpassword") String newPasswd) {
		if (StringUtils.isBlank(newPasswd)) {
			return new ResponseMap(TheCode.NONEMPTY.getName(),
					TheCode.NONEMPTY.getValue());
		}

		ResponseMap responseMap = checkValidCode(mobile, validCode,
				SMSValidCodeType.FINDPASSWORD);
		if (null != responseMap) {
			return responseMap;
		}
		try {
			Member m = memberService.findMemberByMobile(mobile);
			if (null == m) {
				return new ResponseMap(TheCode.USERDOESNOTEXIST.getName(),
						TheCode.USERDOESNOTEXIST.getValue());
			}
			memberService.updatePass(newPasswd, m.getMemberId());
			return new ResponseMap(TheCode.SUCCESS.getName(),
					TheCode.SUCCESS.getValue());
		} catch (Exception e) {
			log.error("找回密码API出错", e);
			return new ResponseMap(TheCode.FAIL.getName(),
					TheCode.FAIL.getValue());

		}
	}

	/**
	 * 手机验证码
	 *
	 * @param mobile
	 * @param codeType
	 *            验证码类型 1:第三方登录绑定手机验证码 2:注册时短信验证码 3:找回密码
	 * @return
	 */
	@RequestMapping("/getValidCode")
	@ResponseBody
	public ResponseMap getValidCode(@RequestParam String mobile,
			@RequestParam Integer codeType, HttpServletRequest request) {
		try {
			if (com.sherlochao.util.StringUtils.isMobile(mobile)) {
				Integer valid = NumberUtils.getRandomNumber();

				SMSValidCode smsValidCode = new SMSValidCode();
				smsValidCode.setMobile(mobile);
				smsValidCode.setCodeType(codeType);
				smsValidCode.setCreatetime(System.currentTimeMillis());
				smsValidCode.setValidcode(valid.toString());
				smsValidCode.setChecktime(null);
				smsValidCode.setIp(IpUtil.getIpAddr(request));
				smsValidCode.setUserAgent(request.getHeader("user-agent"));

				int num = smsValidCodeService.save(smsValidCode);
				String result = null;
				if (1 == num) { // 更新条数
					result = C123SendSmsUtil.sendSms(mobile, valid.toString());
				}
				if ("1".equals(result)) {
					return new ResponseMap(TheCode.SUCCESS.getName(),
							TheCode.SUCCESS.getValue());
				} else {
					log.error("短信发送失败,短信返回码:" + result + ",手机:" + mobile);
					return new ResponseMap(TheCode.FAIL.getName(),
							"短信发送失败,请稍后再试");
				}
			} else {
				return new ResponseMap(TheCode.FAIL.getName(),
						"短信发送失败,手机号码格式错误");
			}
		} catch (Exception e) {
			log.error("getValidCode 接口出错了", e);
			return new ResponseMap(TheCode.FAIL.getName(),
					TheCode.FAIL.getValue());
		}
	}

	/**
	 * 校验手机的短信验证码是否正确
	 *
	 * @param mobile
	 * @param validCode
	 * @param codeType
	 * @return ResponseMap:错误返回码,如果短信可用,则返回 null
	 */
	private ResponseMap checkValidCode(String mobile, String validCode,
			int codeType) {
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(validCode)) {
			return new ResponseMap(TheCode.VALIDCODEERROR.getName(),
					TheCode.VALIDCODEERROR.getValue());
		}

		SMSValidCode smsValidCode = new SMSValidCode();
		smsValidCode.setMobile(mobile);
		smsValidCode.setValidcode(validCode);
		smsValidCode.setCodeType(codeType);

		SMSValidCode sms = smsValidCodeService.findSMS(smsValidCode);
		if (null == sms || null != sms.getChecktime()) { // 如果短信已经被校验了,或者找不到短信
			return new ResponseMap(TheCode.VALIDCODEERROR.getName(),
					TheCode.VALIDCODEERROR.getValue());
		}

		System.out.println(System.currentTimeMillis());

		if (System.currentTimeMillis() - sms.getCreatetime() > 40 * 60 * 1000) { // 40分钟过期
			return new ResponseMap(TheCode.VALIDCODTIMEOUT.getName(),
					TheCode.VALIDCODTIMEOUT.getValue());
		}

		sms.setChecktime(System.currentTimeMillis());
		smsValidCodeService.updateBySMSId(sms);
		return null;
	}

	/**
	 * 获取会员信息
	 *
	 * @param
	 * @return
	 */
	@RequestMapping("/memberDetail")
	@ResponseBody
	public JSONObject memberDetail(
			@RequestParam(value = "memberId") Integer oldmemberId) {
		JSONObject jsonObj = new JSONObject();
		Integer memberId;
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) { // 判断是否登录
			memberId = getCacheUser().getMember().getMemberId(); // 获取用户id
		} else {
			memberId = oldmemberId;// 获取用户id时,使用前端传入的用户id
		}

		try {
			Member member;
			member = memberService.findById(memberId);
			if (null == member) {
				jsonObj.put("result", 0);
				jsonObj.put("msg", "用户不存在");
				jsonObj.put("data", "[]");
			} else {

				MemberApiBean bean = new MemberApiBean();
				MyBeanUtils.copyBeanNotNull2Bean(member, bean);

				bean.setMemberNameCode(PinYinUtil.getPingYin(bean
						.getMemberName()));
				jsonObj.put("result", 1);
				jsonObj.put("msg", "获取成功");
				jsonObj.put("data", JSONArray.fromObject(bean, JsonUtils.getJsonConfig()));
			}

		} catch (Exception e) {
			log.error("会员信息API出错", e);
			jsonObj.put("result", 0);
			jsonObj.put("msg", "服务器异常");
			jsonObj.put("data", "[]");
			return jsonObj;
		}
		return jsonObj;
	}

}
