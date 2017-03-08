package com.sherlochao.common;


/**
 * 各种返回码,返回给手机(email)
 * */
public enum TheCode {

	/**
	 * 成功
	 * */
	SUCCESS(1, "操作成功"),

	/**
	 * 失败,手机(email)端需要展示该消息
	 * */
	FAIL(0, "操作失败"),

	/**
	 * 2*** 返回码为逻辑操作,手机端需要对该返回值进行逻辑判断处理
	 * */
	EXCEPTION(2000, "系统发生了异常！"),

	/**
	 * 第三方登录用户没有绑定手机号码thirdLogin no mobile
	 * */
	NOTHIRDLOGINMOBILE(2001, "第三方用户未绑定邮箱"),

	/**
	 * 邮箱已经被注册
	 */
	MOBILEISUSED(2002,"邮箱已经被注册"),

	/**
	 * 邮箱格式错误
	 * */
	FORMATMOBILEERROR(2003, "邮箱格式错误"),

	/**
	 * 验证码错误
	 * */
	VALIDCODEERROR(2004, "验证码错误"),

	/**
	 * 验证码超时
	 * */
	VALIDCODTIMEOUT(2005, "验证码超时"),

	USERDOESNOTEXIST(2006,"用户不存在"),

	NONEMPTY(2007,"参数不能为空"),

	PASSWRONG(2008,"密码错误"),

	EXCEEDPUBLISH(2009,"发行限制"),

	EXCEEDRECEIVE(2010,"领取限制"),

	NOTSIGNED(2011,"用户未登录,或者登录超时了");

	
	private Integer name;
	private String value;

	private TheCode(Integer name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}
}

